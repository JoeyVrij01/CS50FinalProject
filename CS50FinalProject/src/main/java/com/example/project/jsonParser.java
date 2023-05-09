package com.example.project;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class jsonParser {

    public static JSONObject getJson(URL url) throws IOException {
        String json = IOUtils.toString(url, StandardCharsets.UTF_8);
        return new JSONObject(json);
    }

    public static List<List> getStockData(List<String> symbolInput, String startDate) throws IOException {

        List<String> myBaseURL = new ArrayList<>();

         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=70b7383e19b734e9e2cb0c3bf3994b36&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=a7a5d48f121f56ab9bfed3eb05320c28&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=8c386be9e00cbbe93af74247894ad688&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=8803c65886b39849de44b6a9eca70f55&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=5677bb09f1cc1a7f541bed913e286693&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=c0ce90a68673125ac79df26e74d5b283&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=5677bb09f1cc1a7f541bed913e286693&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=c0ce90a68673125ac79df26e74d5b283&symbols=");
         myBaseURL.add("http://api.marketstack.com/v1/eod?access_key=1bb7f7d4dd3dcef304def3b176a4c432&symbols=");

        StringBuilder URLSymbolExtension = new StringBuilder();
        for (String symbol: symbolInput) {
            URLSymbolExtension.append(symbol);
            URLSymbolExtension.append(",");
        }
        URLSymbolExtension.deleteCharAt(URLSymbolExtension.length()-1);

        String URLSymbolExtensionFinal = String.valueOf(URLSymbolExtension);
        String URLDateFromExtensions = "&date_from=" + startDate;

        URL myURL = null;
        int x = 0;
        Boolean valid = false;
        while (!valid) {
            try {
                valid = true;
                myURL = new URL(myBaseURL.get(x) + URLSymbolExtensionFinal + URLDateFromExtensions);
                JSONObject json = getJson(myURL);
            } catch (IOException ioException) {
                if (ioException.toString().contains("422")) {
                    List<List> list = new ArrayList<>();
                    List<String> list2 = new ArrayList<>();
                    list2.add("ERROR:422");
                    list.add(list2);
                    return list;
                }
                System.out.println("API call limit is reached for this API key..");
                System.out.println("Skipping to another key..");
                valid = false;
            }
            x++;
        }

        LocalDate now = java.time.LocalDate.now();
        LocalDate dateUserLocalDate = LocalDate.parse(startDate);
        long differenceDays = DAYS.between(now, dateUserLocalDate) * -1;

        List<LocalDate> localDateList = new ArrayList<>();

        List<DayOfWeek> weekdays = new ArrayList<>();
        weekdays.add(DayOfWeek.valueOf("MONDAY"));
        weekdays.add(DayOfWeek.valueOf("TUESDAY"));
        weekdays.add(DayOfWeek.valueOf("WEDNESDAY"));
        weekdays.add(DayOfWeek.valueOf("THURSDAY"));
        weekdays.add(DayOfWeek.valueOf("FRIDAY"));

        List<String> holidays = new ArrayList<>();
        holidays.add("01-02");
        holidays.add("01-16");
        holidays.add("02-20");
        holidays.add("04-07");
        holidays.add("05-29");
        holidays.add("06-19");
        holidays.add("07-04");
        holidays.add("09-04");
        holidays.add("11-23");
        holidays.add("12-25");

        for (int i = 1; i < differenceDays; i++) {
            String temp = now.minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate tempLocalDate = LocalDate.parse(temp);
            if (weekdays.contains(tempLocalDate.getDayOfWeek()) && !holidays.contains(temp.substring(5))) {
                localDateList.add(tempLocalDate);
            }
        }

        JSONObject json = getJson(myURL);
        JSONArray data = json.getJSONArray("data");

        List<JSONObject> symbols = new ArrayList<>();

        Boolean valid2 = false;
        int x2 = 0;
        while (!valid2) {
            try {
                valid2 = true;
                List<JSONObject> symbolsTemp = new ArrayList<>();
                for (String s : symbolInput) {
                    for (int i = 0; i < (symbolInput.size() * localDateList.size()) - x2; i++) {
                        LocalDate temp = LocalDate.parse(data.getJSONObject(i).getString("date").substring(0, 10));
                        if (localDateList.contains(temp) && data.getJSONObject(i).getString("symbol").equals(s)) {
                            symbolsTemp.add(data.getJSONObject(i));
                        }
                    }
                }
                symbols = symbolsTemp;
            } catch (Exception e) {
                System.out.println("An issue with parsing the JSON data occurred");
                System.out.println("Trying again with x-1..");
                valid2 = false;
            }
            x2++;
        }

        List<List> listOfLists = new ArrayList<>();

        for (int i = 0; i < symbolInput.size(); i++) {
            int finalI = i;
            List<JSONObject> temp = symbols.stream()
                    .filter(object -> object.getString("symbol").equals(symbolInput.get(finalI)))
                    .collect(Collectors.toList());
            listOfLists.add(temp);
        }

        List<Summary> summaries = new ArrayList<>();

        for (int i = 0; i < symbolInput.size(); i++) {
            Summary s = new Summary();

            JSONObject jo = (JSONObject) listOfLists.get(i).get(0);
            JSONObject jo2 = (JSONObject) listOfLists.get(i).get(listOfLists.get(i).size()-1);

            Double d = ((jo.getDouble("close") / jo2.getDouble("close")) * 100) - 100;
            s.setClose(String.format("%.2f", Double. valueOf(String.valueOf(d).substring(0, 4))) + "%");
            summaries.add(s);
        }

        List<List> listOfListsOfLists = new ArrayList<>();
        listOfListsOfLists.add(listOfLists);
        listOfListsOfLists.add(summaries);

        return listOfListsOfLists;
    }

}

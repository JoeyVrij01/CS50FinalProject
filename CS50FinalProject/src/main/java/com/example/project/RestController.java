package com.example.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RestController {

    @RequestMapping(method = RequestMethod.GET, value = "/finance")
    public String queryForStockAndDateInput(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "input.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/finance")
    public String submitForm(@ModelAttribute("user") User user, Model model) throws IOException {

        List<String> URLSymbolParams = new ArrayList<>();

        if (user.getGoogle()) {
            URLSymbolParams.add("GOOGL");
        }

        if (user.getApple()) {
            URLSymbolParams.add("AAPL");
        }

        if (user.getMicrosoft()) {
            URLSymbolParams.add("MSFT");
        }

        if (user.getTesla()) {
            URLSymbolParams.add("TSLA");
        }

        if (user.getAmazon()) {
            URLSymbolParams.add("AMZN");
        }

        if (user.getFirstChoice() != "") {
            URLSymbolParams.add(user.getFirstChoice());
        }

        if (user.getSecondChoice() != "") {
            URLSymbolParams.add(user.getSecondChoice());
        }

        if (user.getThirdChoice() != "") {
            URLSymbolParams.add(user.getThirdChoice());
        }

        if (user.getFourthChoice() != "") {
            URLSymbolParams.add(user.getFourthChoice());
        }

        if (user.getFifthChoice() != "") {
            URLSymbolParams.add(user.getFifthChoice());
        }

        String dateFrom = user.getDateValue();

        List<List> listOfLists = jsonParser.getStockData(URLSymbolParams, dateFrom);

        if (listOfLists.get(0).get(0) == "ERROR:422") {
            return "error.html";
        }

        model.addAttribute("NumberOfSymbols", URLSymbolParams);
        model.addAttribute("ListOfLists", listOfLists.get(0));
        model.addAttribute("Summaries", listOfLists.get(1));

        return "stock.html";
    }
}

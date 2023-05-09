# Marketstack API
#### Video Demo: https://youtu.be/udGgiTO4jh8 
#### Description:

Brief explanation: my web app lets the user specify some stock symbols and a date and displays financial data about these stocks in the selected period by making an API call to www.marketstack.com, parsing the JSON response and displaying it back to the user.

Programming language:

As mentioned in my video, I have chosen to program this Marketstack API web application in Java Spring boot as I am a Java developer and I wanted to create a project that has some relevance to what I do professionally. I have done this by downloading a Spring Boot starter project from "start.spring.io" and opening it in IntelliJ IDEA and then creating the application.

RestController and User object:

The first thing I needed to create was a RestController to handle the GET and POST requests to '/finance'. This function returns 'input.html' that shows a website asking the user for stock data and a date. Then I created a User object with certain fields that match the required user input so I could store the user input into this object for later use. I created an MVC Model, and gave it as a parameter to my RestController handling the GET requests for '/finance'.

The second function in the RestController class handles POST request to '/finance', which happens when you click 'submit' on the first page of the application. This function has a User object and again a Model as arguments. The fields from the User object, certain stock names and a date value, are saved into a list and string respectively and then my 'JSONParser' function is called with the stock names and date as arguments.

JSONParser:

In the JSONParser class a URL is built with a base URL and the stock name(s) and the date, and then a request is made to marketstack.com using this URL. Exceptions regarding invalid data and invalid/overused API keys are being caught here. It then creates a list of all the dates that the stock market was open between today and the date input by the user, and then saves all the JSONObjects that were returned from the API call into a list. The biggest issue I had building this application was that if there were less JSONObjects than expected by the application (because the stock market would be closed one more day than expected or the API would return less days than entered by the user), the application would crash. I handled this by turning this creation of the list of JSONObjects into a while loop that when entering an error here would check for x-1 JSONObjects, eventually getting the right number and then processing could continue without crashing. Then I used a stream to filter the data and get it in the right order to display it on the website, created the object called Summary to store summary data about the stocks based on the closing price at the beginning and the end of the period as specified by the user so I could use this to display it at the frond end as well. Then some more formatting happens so I would later display all data to have two digits after the decimals, and a percent sign for data that ends up in the summary table. At the end the function returns a list of lists to the RestController with two parts: all the JSON data to be displayed, and the user objects (one for every stock).

Restcontroller (continued):

Back in the restcontroller three things are added to the MVC Model: the number of stocks requested, the JSON data to be displayed and the objects containing the summary data. This function then returns 'stock.html' that displays a table with the summary of the performance per stock, and a table per stock symbol with all the financial data about the stock over the selected period. Additionally, there is also one 'error.html' page that is shown when the user inputs invalid data in 'input.html', along with a button to return to 'input.html' to try it again.
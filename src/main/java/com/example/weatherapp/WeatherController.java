package com.example.weatherapp;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class WeatherController {

        @FXML
        private Text chanceOfRain;

        @FXML
        private ImageView dayWeatherIcon;

        @FXML
        private ImageView dayWeatherIcon12;

        @FXML
        private ImageView dayWeatherIcon15;

        @FXML
        private ImageView dayWeatherIcon18;

        @FXML
        private ImageView dayWeatherIcon21;

        @FXML
        private ImageView dayWeatherIcon3;

        @FXML
        private ImageView dayWeatherIcon6;

        @FXML
        private ImageView dayWeatherIcon9;

        @FXML
        private Text dayWeatherTime;

        @FXML
        private Text dayWeatherTime12;

        @FXML
        private Text dayWeatherTime15;

        @FXML
        private Text dayWeatherTime18;

        @FXML
        private Text dayWeatherTime21;

        @FXML
        private Text dayWeatherTime3;

        @FXML
        private Text dayWeatherTime6;

        @FXML
        private Text dayWeatherTime9;

        @FXML
        private Text dayWeatherWind;

        @FXML
        private Text dayWeatherWind12;

        @FXML
        private Text dayWeatherWind15;

        @FXML
        private Text dayWeatherWind18;

        @FXML
        private Text dayWeatherWind21;

        @FXML
        private Text dayWeatherWind3;

        @FXML
        private Text dayWeatherWind6;

        @FXML
        private Text dayWeatherWind9;

        @FXML
        private Text feelsLike;

        @FXML
        private Text forecastInFourDays;

        @FXML
        private Text forecastInThreeDays;

        @FXML
        private Text forecastInTwoDays;

        @FXML
        private Text forecastToday;

        @FXML
        private Text forecastTomorrow;

        @FXML
        private Text hourWeatherText;

        @FXML
        private Text hourWeatherText12;

        @FXML
        private Text hourWeatherText15;

        @FXML
        private Text hourWeatherText18;

        @FXML
        private Text hourWeatherText21;

        @FXML
        private Text hourWeatherText3;

        @FXML
        private Text hourWeatherText6;

        @FXML
        private Text hourWeatherText9;

        @FXML
        private Text humidity;

        @FXML
        private Text mainTemperature;

        @FXML
        private Text mainWeather;

        @FXML
        private Text pressure;

        @FXML
        private TextField selectCity;

        @FXML
        private Text sunriseTime;

        @FXML
        private Text sunsetTime;

        @FXML
        private Text temperatureInFourDays;

        @FXML
        private Text temperatureInThreeDays;

        @FXML
        private Text temperatureInTwoDays;

        @FXML
        private Text temperatureToday;

        @FXML
        private Text temperatureTomorrow;

        @FXML
        private Text visibility;

        @FXML
        private Text windForceDirection;

        @FXML
        private Text windForceText;

        @FXML
    private void initialize(){
            currentRequestHandler();
        selectCity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String getUserCity = selectCity.getText().trim();
                if (!getUserCity.equals("")) {
                    /* Write request to file */
                    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("WeatherData.txt"))){
                        String output = getUrlContent("http://api.openweathermap.org/data/2.5/forecast?q=" + getUserCity + "&appid=aecf0d6448508c14ff8301e4920e79d1&units=metric");
                        bufferedWriter.write(output);
                        currentRequestHandler();
                    } catch (IOException e){
                        System.out.println("Can't create file");
                        selectCity.setAlignment(Pos.CENTER_LEFT);
                        selectCity.setText("Try again! Error in writing data");
                    }

                    }
                }
        });
    }

    private JSONObject readFileReturnJSON(){
            String projectRoot = System.getProperty("user.dir");
            String filePath = projectRoot + File.separator + "WeatherData.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String weatherString = bufferedReader.readLine();
            if (!weatherString.isEmpty()) {    /* No mistakes and file was written correctly */
                return new JSONObject(weatherString.trim());
            }
        }catch (IOException e) {
            System.out.println("File for reading weather not found");
            selectCity.setAlignment(Pos.CENTER_LEFT);
            selectCity.setText("Try again! Error in writing data");
        } catch (NullPointerException e) {
            System.out.println("Error in reading file");
            selectCity.setAlignment(Pos.CENTER_LEFT);
            selectCity.setText("Try again! Error in reading file data");
        }
            return null;
    }

    /* Handler for weather in current time */
    private void currentRequestHandler() {
        JSONObject weatherRequest = readFileReturnJSON();


        /* Sun day parameters */
        int timeZone = weatherRequest.getJSONObject("city").getInt("timezone");
        long sunriseMilliseconds = weatherRequest.getJSONObject("city").getLong("sunrise");
        long sunsetMilliseconds = weatherRequest.getJSONObject("city").getLong("sunset");
        sunriseTime.setText(timeFormatter(sunriseMilliseconds,timeZone,"HH:mm"));
        sunsetTime.setText(timeFormatter(sunsetMilliseconds,timeZone,"HH:mm"));

        JSONArray weatherTimeArray = (JSONArray) weatherRequest.get("list");
        JSONObject currentWeatherObject = (JSONObject) weatherTimeArray.get(0);
        System.out.println(currentWeatherObject);

        /* Set data in top fields */
        /*mainTemperature.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("temp"))+"℃");
        mainWeather.setText(currentWeatherObject.getJSONObject("weather").getString("description")+
                " "+Math.round(currentWeatherObject.getJSONObject("main").getDouble("temp_max"))+"/"
                +Math.round(currentWeatherObject.getJSONObject("main").getDouble("temp_min")));*/


        /* Wind info */
        /*hourWeatherText.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("temp"))+"°");
        double windDirection = currentWeatherObject.getJSONObject("wind").getDouble("deg");*/

        /* Other info */

        /*humidity.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("humidity"))+"%");
        feelsLike.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("feels_like"))+"°");
        pressure.setText(currentWeatherObject.getJSONObject("main").getDouble("pressure")+" mBar");
        visibility.setText(currentWeatherObject.getJSONObject("main").getDouble("visibility")+" m");
        chanceOfRain.setText(Math.round(currentWeatherObject.getJSONObject("precipitation").getDouble("value"))+"%");*/

    }

    /* Time formatter for all time fields */
    private String timeFormatter(long epochTime, int timeZone, String pattern){
        /* Set dateTimeFormatter and checking correct TimeZone*/
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZone);
        ZoneId zoneId = ZoneId.ofOffset("UTC",zoneOffset);
        Instant instant = Instant.ofEpochMilli(epochTime);
        ZonedDateTime sunDayLocalTime = instant.atZone(zoneId);
        dateTimeFormatter.format(sunDayLocalTime);
        return dateTimeFormatter.format(sunDayLocalTime);
    }


        /* Handler for weather in next 24 hours */
    private void nextHoursRequestHandler(){
        /* Set dateTimeFormatter */
        String timePattern = "HH:mm";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);
    }

    /* Handler for weather in next 5 days */
    private void nextDaysRequestHandler(){

    }

    /* Creating URL-connection and write data to String */
    private static String getUrlContent(String urlAddress){
        StringBuffer content = new StringBuffer();

        try{
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String dataLine;

            while((dataLine = bufferedReader.readLine()) != null){
                content.append(dataLine+"\n");
            }
            bufferedReader.close();
        }catch (Exception e) {
            System.out.println("No such city was found!");
        }
        return content.toString();
    }

}

package com.example.weatherapp;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {

    @FXML
    private Text chanceOfRain;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView mainScreen;

    @FXML
    private AnchorPane day0;

    @FXML
    private AnchorPane day1;

    @FXML
    private AnchorPane day2;

    @FXML
    private AnchorPane day3;

    @FXML
    private AnchorPane day4;

    @FXML
    private ImageView dayWeatherIcon0;

    @FXML
    private ImageView dayWeatherIcon1;

    @FXML
    private ImageView dayWeatherIcon2;

    @FXML
    private ImageView dayWeatherIcon3;

    @FXML
    private ImageView dayWeatherIcon4;

    @FXML
    private ImageView dayWeatherIcon5;

    @FXML
    private ImageView dayWeatherIcon6;

    @FXML
    private ImageView dayWeatherIcon7;

    @FXML
    private ImageView dayWeatherIcon8;

    @FXML
    private Text dayWeatherTime0;

    @FXML
    private Text dayWeatherTime1;

    @FXML
    private Text dayWeatherTime2;

    @FXML
    private Text dayWeatherTime3;

    @FXML
    private Text dayWeatherTime4;

    @FXML
    private Text dayWeatherTime5;

    @FXML
    private Text dayWeatherTime6;

    @FXML
    private Text dayWeatherTime7;

    @FXML
    private Text dayWeatherTime8;

    @FXML
    private Text dayWeatherWind0;

    @FXML
    private Text dayWeatherWind1;

    @FXML
    private Text dayWeatherWind2;

    @FXML
    private Text dayWeatherWind3;

    @FXML
    private Text dayWeatherWind4;

    @FXML
    private Text dayWeatherWind5;

    @FXML
    private Text dayWeatherWind6;

    @FXML
    private Text dayWeatherWind7;

    @FXML
    private Text dayWeatherWind8;

    @FXML
    private Text feelsLike;

    @FXML
    private Text forecastDay0;

    @FXML
    private Text forecastDay1;

    @FXML
    private Text forecastDay2;

    @FXML
    private Text forecastDay3;

    @FXML
    private Text forecastDay4;

    @FXML
    private Text hourWeatherText0;

    @FXML
    private Text hourWeatherText1;

    @FXML
    private Text hourWeatherText2;

    @FXML
    private Text hourWeatherText3;

    @FXML
    private Text hourWeatherText4;

    @FXML
    private Text hourWeatherText5;

    @FXML
    private Text hourWeatherText6;

    @FXML
    private Text hourWeatherText7;

    @FXML
    private Text hourWeatherText8;

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
    private Text temperatureDay0;

    @FXML
    private Text temperatureDay1;

    @FXML
    private Text temperatureDay2;

    @FXML
    private Text temperatureDay3;

    @FXML
    private Text temperatureDay4;

    @FXML
    private Text visibility;

    @FXML
    private ImageView windDirectionImage;

    @FXML
    private Text windForceDirection;

    @FXML
    private Text windForceText;

        @FXML
    private void initialize(){
        currentRequestHandler();
        selectCity.setOnMouseClicked(mouseEvent -> {
            selectCity.setPromptText("");
            if(!selectCity.getText().equals("")){
                String cityName = selectCity.getText().trim();
                selectCity.setPromptText(cityName);
            }
            selectCity.setText("");
            selectCity.setAlignment(Pos.CENTER);
        });
        selectCity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String getUserCity = selectCity.getText().trim();
                if (!getUserCity.equals("")) {
                    /* Write request to files */
                    try(BufferedWriter bufferedWriterHourly = new BufferedWriter(new FileWriter("WeatherData.txt"))){
                        String outputHourly = getUrlContent("http://api.openweathermap.org/data/2.5/forecast?q=" + getUserCity + "&appid=943f02783f97c24c17648f5d4411afbd&units=metric");
                        bufferedWriterHourly.write(outputHourly);
                    } catch (IOException e){
                        System.out.println("Can't create file");
                        selectCity.setAlignment(Pos.CENTER_LEFT);
                        selectCity.setText("Try again! Error in writing data");
                    }
                    // Create a FadeTransition
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), mainPane);
                    fadeTransition.setFromValue(0.65); // Start with transparency
                    fadeTransition.setToValue(1.0);   // End with full visibility
                    fadeTransition.play();
                    currentRequestHandler();
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

        Path pathToImages = FileSystems.getDefault().getPath(System.getProperty("user.dir"),
                "src", "main", "resources","com","example","weatherapp","images");

        /* Sun day parameters */
        int timeZone = weatherRequest.getJSONObject("city").getInt("timezone");
        long sunriseSeconds = weatherRequest.getJSONObject("city").getLong("sunrise");
        long sunsetSeconds = weatherRequest.getJSONObject("city").getLong("sunset");
        sunriseTime.setText(timeFormatter(sunriseSeconds,timeZone,"HH:mm"));
        sunsetTime.setText(timeFormatter(sunsetSeconds,timeZone,"HH:mm"));

        JSONArray weatherTimeArray = (JSONArray) weatherRequest.get("list");
        JSONObject currentWeatherObject = (JSONObject) weatherTimeArray.get(0);

        /* Set data in top fields */
        mainTemperature.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("temp"))+"°C");
        String weatherDescription = "";
        JSONArray weatherArray = currentWeatherObject.getJSONArray("weather");
        if (weatherArray.length() > 0) {
            JSONObject JSONDescription = weatherArray.getJSONObject(0);
            weatherDescription = JSONDescription.getString("description");
        } else {
            weatherDescription = "No weather data available";
        }
        mainWeather.setText(weatherDescription);


        /* Handler for next days */
        setDataForNextDays(createListForNextDays(),timeZone);

        /* Handler for weather in next 24 hours */
        List<Text> hourWeatherText = new ArrayList<>(){{
            add(hourWeatherText0);add(hourWeatherText1);add(hourWeatherText2);
            add(hourWeatherText3);add(hourWeatherText4);add(hourWeatherText5);
            add(hourWeatherText6);add(hourWeatherText7);add(hourWeatherText8);
        }};
        List<Text> dayWeatherWind = new ArrayList<>(){{
            add(dayWeatherWind0);add(dayWeatherWind1);add(dayWeatherWind2);
            add(dayWeatherWind3);add(dayWeatherWind4);add(dayWeatherWind5);
            add(dayWeatherWind6);add(dayWeatherWind7);add(dayWeatherWind8);
        }};
        List<Text> dayWeatherTime = new ArrayList<>(){{
            add(dayWeatherTime0);add(dayWeatherTime1);add(dayWeatherTime2);
            add(dayWeatherTime3);add(dayWeatherTime4);add(dayWeatherTime5);
            add(dayWeatherTime6);add(dayWeatherTime7);add(dayWeatherTime8);
        }};
        List<ImageView> dayWeatherIcon = new ArrayList<>(){{
            add(dayWeatherIcon0);add(dayWeatherIcon1);add(dayWeatherIcon2);
            add(dayWeatherIcon3);add(dayWeatherIcon4);add(dayWeatherIcon5);
            add(dayWeatherIcon6);add(dayWeatherIcon7);add(dayWeatherIcon8);
        }};



        for(int i=0;i<9;i++){
            JSONObject nextHoursWeatherObject = (JSONObject) weatherTimeArray.get(i);
            hourWeatherText.get(i).setText(Math.round(nextHoursWeatherObject.getJSONObject("main").getDouble("temp"))+"°");
            dayWeatherWind.get(i).setText(nextHoursWeatherObject.getJSONObject("wind").getDouble("speed")+" km/h");
            /* Choosing icon for 24 hours forecast */
            JSONArray weatherCurrentTimeArray = (JSONArray) nextHoursWeatherObject.get("weather");
            JSONObject currentTimeWeatherObject = (JSONObject) weatherCurrentTimeArray.get(0);

            /* Set icon to current time weather*/
            String currentWeatherIconName = currentTimeWeatherObject.getString("icon");
            dayWeatherIcon.get(i).setImage(new Image(pathToImages.toString()+File.separator+"weatherIcons"+File.separator+currentWeatherIconName+".png"));

            if(i==0) {
                dayWeatherTime.get(i).setText("Now");
                String backgroundName = currentWeatherIconName.substring(0,2);
                mainScreen.setImage(new Image(pathToImages.toString()+File.separator+"backgrounds"+File.separator+backgroundName+".jpg"));
            } else {
                long currentTime = nextHoursWeatherObject.getLong("dt");
                dayWeatherTime.get(i).setText(timeFormatter(currentTime,timeZone,"HH:mm"));
            }
        }


        /* All bottom info */

        windForceText.setText(currentWeatherObject.getJSONObject("wind").getDouble("speed")+" m/sec");
        double windDirection = currentWeatherObject.getJSONObject("wind").getDouble("deg");
        chooseWindDirectionIcon(windDirection);
        /*  */

        humidity.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("humidity"))+"%");
        feelsLike.setText(Math.round(currentWeatherObject.getJSONObject("main").getDouble("feels_like"))+"°");
        pressure.setText(currentWeatherObject.getJSONObject("main").getDouble("pressure")+" hPa");
        visibility.setText((int)currentWeatherObject.getDouble("visibility")+" m");
        chanceOfRain.setText(Math.round(currentWeatherObject.getDouble("pop")*100)+"%");

    }

    /* Time formatter for all time fields */
    private String timeFormatter(long epochTime, int timeZone, String pattern){
        /* Set dateTimeFormatter and checking correct TimeZone*/
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZone);
        ZoneId zoneId = ZoneId.ofOffset("UTC",zoneOffset);
        Instant instant = Instant.ofEpochSecond(epochTime);
        ZonedDateTime sunDayLocalTime = instant.atZone(zoneId);
        DayOfWeek dayOfWeek = sunDayLocalTime.getDayOfWeek();
        String dayOfWeekFormatted = dayOfWeek.toString().charAt(0) + dayOfWeek.toString().substring(1).toLowerCase();
        dateTimeFormatter.format(sunDayLocalTime);
        if (pattern.equals("dd.MM")){
            return (dateTimeFormatter.format(sunDayLocalTime))+" "+dayOfWeekFormatted;
        } else return dateTimeFormatter.format(sunDayLocalTime);
    }


        /* Set icon and text for wind direction */
    private void chooseWindDirectionIcon(double windDirection){
        Path pathToImages = FileSystems.getDefault().getPath(System.getProperty("user.dir"),
                "src", "main", "resources","com","example","weatherapp","images","windIcons");
        if(windDirection>22.5 && windDirection<67.5) {
            windForceDirection.setText("N-E");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"NE.png"));
        } else if(windDirection>=67.5 && windDirection<=112.5) {
            windForceDirection.setText("East");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"E.png"));
        } else if(windDirection>112.5 && windDirection<157.5) {
            windForceDirection.setText("S-E");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"SE.png"));
        } else if(windDirection>=157.5 && windDirection<=202.5) {
            windForceDirection.setText("South");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"S.png"));
        } else if(windDirection>202.5 && windDirection<247.5) {
            windForceDirection.setText("S-W");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"SW.png"));
        } else if(windDirection>=247.5 && windDirection<=292.5) {
            windForceDirection.setText("West");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"W.png"));
        }else if(windDirection>292.5 && windDirection<337.5) {
            windForceDirection.setText("N-W");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"NW.png"));
        } else {
            windForceDirection.setText("North");
            windDirectionImage.setImage(new Image(pathToImages.toString()+File.separator+"N.png"));
        }
    }

    /* Handler for weather in next days */
    private List<DayWeatherObject> createListForNextDays(){

        /* Open file and create new array of weather objects every 3 hours*/
        JSONObject weatherRequest = readFileReturnJSON();
        int timeZone = weatherRequest.getJSONObject("city").getInt("timezone");
        JSONArray weatherArray = (JSONArray) weatherRequest.get("list");

        /* Taking data from first object, add necessary variables */
        int partsCount = 1;
        List<DayWeatherObject> resultDaysList = new ArrayList<>();

        JSONObject firstObject = weatherArray.getJSONObject(0);
        long firstTime = firstObject.getLong("dt");
        int tempMin = (int) Math.round(firstObject.getJSONObject("main").getDouble("temp_min"));
        int tempMax = (int) Math.round(firstObject.getJSONObject("main").getDouble("temp_max"));
        long secondTime = 0;
        int secondTempMin,secondTempMax;

        for(int i=1;i<weatherArray.length();i++){
            JSONObject secondObject = weatherArray.getJSONObject(i);
            secondTime = secondObject.getLong("dt");
            secondTempMin = (int) Math.round(secondObject.getJSONObject("main").getDouble("temp_min"));
            secondTempMax = (int) Math.round(secondObject.getJSONObject("main").getDouble("temp_max"));
            if(isDatesEqual(firstTime,secondTime,timeZone)){
                partsCount+=1;
                tempMin = Math.min(tempMin,secondTempMin);
                tempMax = Math.max(tempMax,secondTempMax);
            } else {
                if(partsCount==8) {
                    resultDaysList.add(new DayWeatherObject(firstTime,tempMin,tempMax));
                }
                partsCount = 1;
                firstTime = secondTime;
                tempMin = secondTempMin;
                tempMax = secondTempMax;
            }
            if(i==weatherArray.length()-1 && partsCount>=6){
                resultDaysList.add(new DayWeatherObject(secondTime,tempMin,tempMax));
            }
        }
        return resultDaysList;
    }

    /*set data for next days block*/
    private void setDataForNextDays(List<DayWeatherObject> weatherForNextDays,int timeZone){
        /*creating lists of fields to set data using a loop*/
        List<AnchorPane> daysAnchorList = new ArrayList<>(){{
            add(day0);add(day1);add(day2);add(day3);add(day4);
        }};
        for (AnchorPane anchor:daysAnchorList) {
            anchor.setVisible(false);
        }
        List<Text> daysDateList = new ArrayList<>(){{
            add(forecastDay0);add(forecastDay1);add(forecastDay2);add(forecastDay3);add(forecastDay4);
        }};
        List<Text> daysTemperatureList = new ArrayList<>(){{
            add(temperatureDay0);add(temperatureDay1);add(temperatureDay2);add(temperatureDay3);add(temperatureDay4);
        }};

        for (int i = 0; i < weatherForNextDays.size(); i++) {
            daysAnchorList.get(i).setVisible(true);
            daysDateList.get(i).setText(timeFormatter(weatherForNextDays.get(i).getDate(),timeZone,"dd.MM"));
            daysTemperatureList.get(i).setText(weatherForNextDays.get(i).getTempMax()+"°/"+weatherForNextDays.get(i).getTempMin()+"°");
        }

    }

    /* checking two epochSeconds if it's the same date */
    private boolean isDatesEqual(long epochSecondsOne,long epochSecondsTwo,int timeZone){
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timeZone);
        ZoneId zoneId = ZoneId.ofOffset("UTC",zoneOffset);
        LocalDate localDateOne = Instant.ofEpochSecond(epochSecondsOne).atZone(zoneId).toLocalDate();
        LocalDate localDateTwo = Instant.ofEpochSecond(epochSecondsTwo).atZone(zoneId).toLocalDate();
        if (localDateOne.isEqual(localDateTwo)) {
            return true;
        } else return false;
    }

    /*private void createGradientBackground(int tempMin,int tempMax) {
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null,
                new Stop(0, Color.BLUE),
                new Stop(1, Color.GREEN)
        );
        Background background = new Background(new BackgroundFill(gradient, null, null));
        root.setBackground(background);
    }*/


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

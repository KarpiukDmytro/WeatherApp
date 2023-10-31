package com.example.weatherapp;

import java.util.Objects;

public class DayWeatherObject {
    private long date;
    private int tempMin,tempMax;

    public DayWeatherObject(long date, int tempMin, int tempMax) {
        this.date = date;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayWeatherObject that = (DayWeatherObject) o;
        return date == that.date && tempMin == that.tempMin && tempMax == that.tempMax;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, tempMin, tempMax);
    }

    @Override
    public String toString() {
        return "DayWeatherObject{" +
                "date=" + date +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}

package com.example.android.quakereport;

import java.sql.Time;

public class Earthquake {

    // Magnitude of earthquake
    private double Magnitude;

    // Location of the earthquake
    private String Location;

    // Time of earthquake
    private long TimeInMilliseconds;

    // Url of webpage associated with earthquake
    private String Url;


    /* Create a new Earthquake object.
     * @param magnitude                    Magnitude of the earthquake
     * @param location                     Location of city experiencing the earthquake
     * @param timeInMilliseconds           Time of the earthquake
     * @ param url                         Url of webpage associated with earthquake
     */

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        Magnitude = magnitude;
        Location = location;
        TimeInMilliseconds = timeInMilliseconds;
        Url = url;
    }

    // Get the magnitude of the earthquake
    public double getMagnitude() {
        return Magnitude;
    }

    // Get the location of the earthquake
    public String getLocation() {
        return Location;
    }

    // Get the date of the earthquake
    public long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }

    // Get the url of the webpage associated with earthquake
    public String getUrl() {
        return Url;
    }
}






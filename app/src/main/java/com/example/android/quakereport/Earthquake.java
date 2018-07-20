package com.example.android.quakereport;

import java.sql.Time;

public class Earthquake {

    // Magnitude of earthquake
    private double Magnitude;

    // Location of the earthquake
    private String Location;

    // Time of earthquake
    private long TimeInMilliseconds;


    /* Create a new Earthquake object.
     * @param magnitude                    Magnitude of the earthquake
     * @param location                     Location of city experiencing the earthquake
     * @param timeInMilliseconds           Time of the earthquake
     */

    public Earthquake(double magnitude, String location, long timeInMilliseconds) {
        Magnitude = magnitude;
        Location = location;
        TimeInMilliseconds = timeInMilliseconds;
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
}




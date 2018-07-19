package com.example.android.quakereport;

public class Earthquake {

    // Magnitude of earthquake
    private String Magnitude;

    // Location of the earthquake
    private String Location;

    // Date of earthquake
    private String Date;


    /* Create a new Earthquake object.
     * @param magnitude                    Magnitude of the earthquake
     * @param location                     Location of city experiencing the earthquake
     * @param date                          Date of the earthquake
     */

    public Earthquake(String magnitude, String location, String date) {
        Magnitude = magnitude;
        Location = location;
        Date = date;
    }

    // Get the magnitude of the earthquake
    public String getMagnitude() {
        return Magnitude;
    }

    // Get the location of the earthquake
    public String getLocation() {
        return Location;
    }

    // Get the date of the earthquake
    public String getDate() {
        return Date;
    }
}




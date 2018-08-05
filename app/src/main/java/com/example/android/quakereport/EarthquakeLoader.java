package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

// Loads a list of earthquakes by using an AsyncTask to perform the network request to the given URL

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    // Tag for log messages
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    // Query URL
    private String uUrl;

    /**
     * Constructs a new EarthquakeLoader
     *
     * @param context of the activity
     * @param url     to load data from
     */

    public EarthquakeLoader(Context context, String url) {
        super(context);
        uUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    // This is on a background thread.
    @Override
    public List<Earthquake> loadInBackground() {
        if (uUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(uUrl);
        return earthquakes;

    }
}

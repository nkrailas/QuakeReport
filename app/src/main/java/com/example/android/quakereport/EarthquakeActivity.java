/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    // URL for earthquake data from the USGS dataset
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    // Adapter for the list of earthquakes
    private EarthquakeAdapter eAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the ListView in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new adapter that takes an EMPTY list of earthquakes as input
        eAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the ListView so the list can be populated in the UI
        earthquakeListView.setAdapter(eAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser to
        // open a website with more information about the selected earthquake
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the url from the current earthquake that was clicked on
                Earthquake currentEarthquake = eAdapter.getItem(position);

                // Convert the String url into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent that specifies an action to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Start the AsyncTask to fetch the earthquake data
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

    }

    /**
     * AsyncTask to perform network request on a background thread and then update UI with list of
     * earthquakes in the response. AsyncTask has three generic parameters: input type, progress type,
     * and output type. Our task will take a String URL as input type (return an Earthquake), Void
     * as progress type (no updates), and List<Earthquakes> as output type (list of earthquakes).
     * Override only two AsyncTask methods: doInBackground() and onPostExecute().
     */
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        /**
         * The doInBackground() method runs on a background thread (so it doesn't interfere with
         * the UI) and performs the network request. Do not update the UI from a background
         * so we return a list of Earthquake as the result.
         */

        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // If there are no URLs or the first URL is null, then don't perform the request.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        /**
         * The onPostExecute() method runs on the main UI thread after background work is done.
         * The onPostExecute() input is the result passed from the doInBackground() method, and
         * is the produced data to update the UI. First, the adapter needs to be cleared to get
         * rid of earthquake data from a previous query to USGS. The, the adapter is updated
         * with a new list of earthquakes, triggering the ListView to repopulate its list items.
         */
        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            eAdapter.clear();

            // If there is a valid list of Earthquakes, add them to the adapter's data set.
            // This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                eAdapter.addAll(data);

            }
        }
    }
}




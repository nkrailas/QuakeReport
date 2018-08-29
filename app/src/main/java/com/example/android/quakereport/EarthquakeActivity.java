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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    // URL for earthquake data from the USGS dataset
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";

    // Constant value for earthquake loader ID.
    private static final int EARTHQUAKE_LOADER_ID = 1;

    // Adapter for the list of earthquakes
    private EarthquakeAdapter eAdapter;

    // TextView that is displayed when the list is empty
    private TextView eEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the ListView in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Set empty state TextView onto the ListView
        eEmptyStateTextView = findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(eEmptyStateTextView);

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

        // Get a reference to the Connectivity Manager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default area network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
        }

        // Update empty state with no connection error message
        eEmptyStateTextView.setText(R.string.no_internet_connection);

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. Second parameter is the default.
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        // .parse breaks apart the URI string that's passed into its parameter.
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);

        // .buildUpon prepares the baseUri that we just parsed so we can add query parameters to it.
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, 'format=geojson'.
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");

        // Return the completed uri 'http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&
        //limit=10&minmag=minMagnitude&orderby=time'.
        return new EarthquakeLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        eEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        eAdapter.clear();

        // If there is a valid list of Earthquakes, then add to adapter's data set. This will
        // trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            eAdapter.addAll(earthquakes);
            }
        }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        //Loader reset, so we can clear out our existing data
            eAdapter.clear();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




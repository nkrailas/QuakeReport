package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Create a custom constructor.
     *
     * @param context     The current context used to inflate the layout file
     * @param earthquakes A list of earthquakes that is the data source for adapter
     */

    // Initialize ArrayAdapter's internal storage for the context and the list.
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for a ListView.
     *
     * @param position    The position in the list of data to be displayed in list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     *                    return               The View for the position in the ListView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check is exiting view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);

            // Get the Earthquake object located at this position in the list
            Earthquake currentEarthquake = getItem(position);

            // Find the TextView for the magnitude in earthquake_list_item.xml
            TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
            magnitudeView.setText(currentEarthquake.getMagnitude());

            // Find the TextView for the location in earthquake_list_item.xml
            TextView locationView = listItemView.findViewById(R.id.location);
            locationView.setText(currentEarthquake.getLocation());

            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

            // Find the TextView with view ID date
            TextView dateView = listItemView.findViewById(R.id.date);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(dateObject);
            // Display the date of the current earthquake in that TextView
            dateView.setText(formattedDate);

            // Find the TextView with view ID time
            TextView timeView = listItemView.findViewById(R.id.time);
            // Format the time string (i.e. "3:00PM")
            String formattedTime = formatTime(dateObject);
            // Display the time of the current earthquake in that TextView
            timeView.setText(formattedTime);

        }

        return listItemView;

    }

    // Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    // Return the formatted time string (i.e. "3:00PM") from a Date object.
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}




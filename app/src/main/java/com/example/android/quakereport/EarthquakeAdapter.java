package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

            // Find the TextView with view ID date
            TextView dateView = (TextView) listItemView.findViewById(R.id.date);
            dateView.setText(currentEarthquake.getDate());

        }

        return listItemView;

    }

}




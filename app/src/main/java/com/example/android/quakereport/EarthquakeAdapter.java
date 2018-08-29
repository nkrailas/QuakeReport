package com.example.android.quakereport;

import android.content.Context;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.graphics.drawable.GradientDrawable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    // Split the original location string where the text "of" occurs, if present
    // If no "of", then there is no location offset and use "near the"
    private static final String LOCATION_SEPARATOR = " of ";

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
     *
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check is existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);

            // Get the Earthquake object located at this position in the list
            Earthquake currentEarthquake = getItem(position);

            // Find the TextView with view ID magnitude in earthquake_list_item.xml
            TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
            //Format the magnitude to show one decimal place
            String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
            magnitudeView.setText(formattedMagnitude);

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            // Get the original location String from the Earthquake object and store in a variable.
            // Create two variables, one for location offset and one for primary location
            String originalLocation = currentEarthquake.getLocation();
            String locationOffset;
            String primaryLocation;

            // If original location contains "of," then split at parts before plus "of"
            // into locationOffset and parts after into primaryLocation
            if (originalLocation.contains(LOCATION_SEPARATOR)) {
                String[] parts = originalLocation.split(LOCATION_SEPARATOR);
                locationOffset = parts[0] + LOCATION_SEPARATOR;
                primaryLocation = parts[1];

                // If original location lacks "of," meaning the first part, then add "Near the"
                // into locationOffset and parts after into primaryLocation
            } else {
                locationOffset = getContext().getString(R.string.near_the);
                primaryLocation = originalLocation;

            }

            // Find the TextView with view ID location_offset in earthquake_list_item.xml
            TextView locationOffsetView = listItemView.findViewById(R.id.location_offset);
            // Display the location offset of the current earthquake in that TextView
            locationOffsetView.setText(locationOffset);

            // Find the TextView with view ID primary_location in earthquake_list_item.xml
            TextView primaryLocationView = listItemView.findViewById(R.id.primary_location);
            // Display the primary location of the current earthquake in that TextView
            primaryLocationView.setText(primaryLocation);

            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

            // Find the TextView with view ID date in earthquake_list_item.xml
            TextView dateView = listItemView.findViewById(R.id.date);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(dateObject);
            // Display the date of the current earthquake in that TextView
            dateView.setText(formattedDate);

            // Find the TextView with view ID time in earthquake_list_item.xml
            TextView timeView = listItemView.findViewById(R.id.time);
            // Format the time string (i.e. "3:00PM")
            String formattedTime = formatTime(dateObject);
            // Display the time of the current earthquake in that TextView
            timeView.setText(formattedTime);

        }

        return listItemView;

    }

    // Return the color for the magnitude circle based on the intensity
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    // Return the formatted magnitude string showing one decimal place.
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
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




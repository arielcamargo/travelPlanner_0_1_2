package com.example.travelplanner_0_1_1.directionhelpers;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    protected final String DIR_HELPER = "dir_helper";

    private final TaskLoadedCallback taskCallback;
    private final String directionMode;
    private final String key;
    private String distance, duration;


    public PointsParser(String key, TaskLoadedCallback taskCallback, String directionMode) {
        this.key = key;
        this.taskCallback = taskCallback;
        this.directionMode = directionMode;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            Log.d(DIR_HELPER, jsonData[0]);
            DataParser parser = new DataParser();
            Log.d(DIR_HELPER, parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d(DIR_HELPER, "Executing routes");
            Log.d(DIR_HELPER, routes.toString());

            distance = parser.getDistance(jObject);
            duration = parser.getDuration(jObject);

        } catch (Exception e) {
            Log.d(DIR_HELPER, e.toString());
            e.printStackTrace();
            distance = "-1";
            duration = "-1";
            taskCallback.onTaskDone(key, null, distance, duration);
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        if (result != null) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                if (directionMode.equalsIgnoreCase("walking")) {
                    lineOptions.width(15);
                    //blue-ish color
                    lineOptions.color(0xff0732ab);
                } else if(directionMode.equalsIgnoreCase("driving")){
                    lineOptions.width(10);
                    //dark red color
                    lineOptions.color(0xff800d01);
                } else if(directionMode.equalsIgnoreCase("bicycling")){
                    lineOptions.width(15);
                    //dark green color
                    lineOptions.color(0xff07a314);
                } else{
                    lineOptions.width(10);
                    //magenta color
                    lineOptions.color(0xff9d22f5);
                }
                Log.d(DIR_HELPER, "onPostExecute line options decoded");
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                taskCallback.onTaskDone(key, lineOptions, distance, duration);

            } else {
                Log.d(DIR_HELPER, "without Polylines drawn");
            }
        }
    }
}

package com.example.travelplanner_0_1_1.directionhelpers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchUrl extends AsyncTask<String, Void, String> {

    private final TaskLoadedCallback taskLoadedCallback;
    private String directionMode = "driving";
    private String key = "from home";
    private String dist, dur;

    public FetchUrl(TaskLoadedCallback taskLoadedCallback) {
        this.taskLoadedCallback = taskLoadedCallback;
    }

    @Override
    protected String doInBackground(String... strings) {
        // For storing data from web service
        String data = "";
        directionMode = strings[1];
        key = strings[2];
        try {
            // Fetching the data from web service
            data = downloadUrl(strings[0]);
            Log.d("mylog", "Background task data " + data);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        PointsParser parserTask = new PointsParser(this, taskLoadedCallback, directionMode);
        // Invokes the thread for parsing the JSON data
        parserTask.execute(s);

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data);
            br.close();
        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        if (!data.contains("ZERO_RESULTS")) {
            //save distance from download
            dist = data.substring(data.indexOf(" \"distance\""), data.indexOf(" mi\""));

            //save time from download
            dur = data.substring(data.indexOf(" \"duration\""), data.indexOf(" mins\""));

        }
        return data;
    }

    public double getDistance() {
        double distance;
        try {
            dist = dist.replaceAll("[^0-9.]", "");
            distance = Double.parseDouble(dist);
        } catch (Exception e) {
            distance = -1;
        }
        return distance;
    }

    public int getTime() {
        dur = dur.replaceAll("[^0-9.]", "");
        return Integer.parseInt(dur);
    }
}

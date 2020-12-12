package com.example.travelplanner_0_1_1.vehicles;

import android.content.Context;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.directionhelpers.FetchUrl;
import com.example.travelplanner_0_1_1.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

//this class contains all the common attributes of all the vehicle types
public abstract class Vehicle implements TaskLoadedCallback {

    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);

    //the type of transportation
    protected String type;

    //max number of subtypes
    protected int subTypes;

    protected int timeFromSac;
    protected int timeFromHome;

    private int cost = 0;

    protected double distFromSac = -1;
    protected double distFromHome = -1;

    protected double carbonEmissions;

    //Todo: add in the rest of the options
    final String[] DIRECTION_OPTIONS = new String[] {"driving", "walking"};
    protected int dirSelection = 0;

    private FetchUrl fetchUrlFromSac;
    private FetchUrl fetchUrlFromHome;

    private Context context;

    private PolylineOptions dirFromSac;
    private PolylineOptions dirFromHome;

    public Vehicle(Context context) {
        //needs the context in order to get api key from values
        this.context = context;

    }

    public void setDirections(LatLng home) {
        fetchUrlFromSac = new FetchUrl(this);
        fetchUrlFromSac.execute(getUrl(home, SAC_STATE_LOC, DIRECTION_OPTIONS[dirSelection]), DIRECTION_OPTIONS[dirSelection], "from home");

        fetchUrlFromHome = new FetchUrl(this);
        fetchUrlFromHome.execute(getUrl(SAC_STATE_LOC, home, DIRECTION_OPTIONS[dirSelection]), DIRECTION_OPTIONS[dirSelection], "from Sac");
    }

    //used to create the url to be used to get the directions
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?"
                + parameters + "&key=" + context.getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(FetchUrl fetchUrl, Object... values) {
        if (fetchUrl == fetchUrlFromSac) {
            dirFromSac = (PolylineOptions) values[0];
            distFromSac = fetchUrl.getDistance();
            timeFromSac = fetchUrl.getTime();
        } else {
            dirFromHome = (PolylineOptions) values[0];
            distFromHome = fetchUrl.getDistance();
            timeFromHome  = fetchUrl.getTime();
        }
    }
}

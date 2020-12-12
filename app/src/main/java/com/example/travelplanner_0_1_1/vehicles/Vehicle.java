package com.example.travelplanner_0_1_1.vehicles;

import android.content.Context;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.directionhelpers.FetchUrl;
import com.example.travelplanner_0_1_1.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

//this class contains all the common attributes of all the vehicle types
//protected attributes should not be accessed from outside of vehicles package
public abstract class Vehicle implements TaskLoadedCallback {

    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);

    protected int dirSelection = 0;
    protected static final String[] DIRECTION_OPTIONS = new String[]{"driving", "walking", "bicycling", "transit"};

    //gas price per gallon
    public static final double GAS_AVG = 2.42;

    //according to https://catalog.csus.edu/academic-calendar/#spring2021text
    protected static final int DAYS_IN_SEMESTER = 147;

    //the type of transportation
    protected String type;

    protected int subType = 1;
    //max number of subtypes possible values [1, 4]
    protected int subTypes;
    protected String[] subTypeId;

    protected int timeFromSac = -1;
    protected int timeFromHome = -1;

    protected double avgMpg = -1;
    protected double gasCost = -1;

    //size of costs array
    protected int costTypes = 0;
    // array for the different costs
    protected double[] costs;
    //how many different costs included ex: insurance = cost[0], maintenance = cost[1], etc...
    protected String[] costId;
    protected double netCost = 0;

    protected double distFromSac = -1;
    protected double distFromHome = -1;

    protected double avgEmissions;
    protected double nextEmissions;

    protected String[] pros;
    protected String[] cons;

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
    //TODO: https://developers.google.com/maps/documentation/directions/overview#TravelModes
    //research above for the other modes of transportation and options for things like traffic
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

    //when the setDirections are completed
    @Override
    public void onTaskDone(FetchUrl fetchUrl, Object... values) {
        if (fetchUrl == fetchUrlFromSac) {
            dirFromSac = (PolylineOptions) values[0];
            distFromSac = fetchUrl.getDistance();
            timeFromSac = fetchUrl.getTime();
        } else {
            dirFromHome = (PolylineOptions) values[0];
            distFromHome = fetchUrl.getDistance();
            timeFromHome = fetchUrl.getTime();
        }
    }

    public void setSubType(int subType) {
        //error checking to prevent input greater than max
        this.subType = Math.max(subType, subTypes);
        updateSubType();
    }

    //how the subtypes will be calculated
    public abstract void updateSubType();

    //since different for every type
    public abstract void calculateEmissions();

    public abstract void calculateGas();

    //we can have this made here since its adding up all the fields
    public void setNetCost() {
        netCost = 0;
        for (int i = 0; i < costTypes; i++) {
            netCost += costs[i];
        }
    }

    public String getType() {
        return type;
    }

    public int getSubType() {
        return subType;
    }

    public int getSubTypes() {
        return subTypes;
    }

    public String[] getSubTypeId() {
        return subTypeId;
    }

    public int getTimeFromSac() {
        return timeFromSac;
    }

    public int getTimeFromHome() {
        return timeFromHome;
    }

    public int getCostTypes() {
        return costTypes;
    }

    public double getAvgMpg() {
        return avgMpg;
    }

    public double getGasCost() {
        return gasCost;
    }

    public double[] getCosts() {
        return costs;
    }

    public String[] getCostId() {
        return costId;
    }

    public double getNetCost() {
        return netCost;
    }

    public double getDistFromSac() {
        return distFromSac;
    }

    public double getDistFromHome() {
        return distFromHome;
    }

    public double getNextEmissions() {
        return nextEmissions;
    }

    public String[] getPros() {
        return pros;
    }

    public String[] getCons() {
        return cons;
    }
}

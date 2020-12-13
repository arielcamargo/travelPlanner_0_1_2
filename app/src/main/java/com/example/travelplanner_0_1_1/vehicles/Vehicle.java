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

    //TODO: This is a temporary solution to accessing the vehicle class about each and every activity
    // however this is not the best coding practice. The supposed solution is to pass it through bundles
    // as a parcelable object which means the vehicle class needs to implement parcelable but that requires
    // more learning and testing. https://developer.android.com/reference/android/os/Parcelable
    public static final Vehicle[] vehicles = new Vehicle[6];

    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);

    //dir selection is which type of direction options want to be used when fetchURL is executed
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

    protected double avgMpg = 0;
    protected double gasCost = 0;

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
    protected double netEmissions;

    protected String[] pros;
    protected String[] cons;

    private FetchUrl fetchUrlFromSac;
    private FetchUrl fetchUrlFromHome;

    private PolylineOptions dirFromSac;
    private PolylineOptions dirFromHome;

    public Vehicle() {
    }


    public void setDirections(LatLng home, TaskLoadedCallback taskLoadedCallback, Context context) {
        fetchUrlFromHome = new FetchUrl(taskLoadedCallback);
        fetchUrlFromHome.execute(getUrl(SAC_STATE_LOC, home, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from Sac");

        fetchUrlFromSac = new FetchUrl(this);
        fetchUrlFromSac.execute(getUrl(home, SAC_STATE_LOC, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from home");
    }

    public void setDirections(LatLng home, Context context) {
        fetchUrlFromHome = new FetchUrl(this);
        fetchUrlFromHome.execute(getUrl(SAC_STATE_LOC, home, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from Sac");

        fetchUrlFromSac = new FetchUrl(this);
        fetchUrlFromSac.execute(getUrl(home, SAC_STATE_LOC, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from home");
    }

    //used to create the url to be used to get the directions
    //TODO: https://developers.google.com/maps/documentation/directions/overview#TravelModes
    //research above for the other modes of transportation and options for things like traffic
    private String getUrl(LatLng origin, LatLng dest, String directionMode, Context context) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?"
                + parameters + "&key=" + context.getString(R.string.google_maps_key);
    }

    //when the setDirections are completed
    @Override
    public void onTaskDone(FetchUrl fetchUrl, Object... values) {
        if (fetchUrl == fetchUrlFromSac) {
            dirFromSac = (PolylineOptions) values[0];
            distFromSac = parseDistance((String) values[1]);
            timeFromSac = parseDuration((String) values[2]);
        } else {
            updateHomeDir(fetchUrl, values);
        }

        calculateEmissions();
        calculateGas();
        calculateCosts();
        calculateNetCost();
    }

    public void updateHomeDir(FetchUrl fetchUrl, Object... values) {
        dirFromHome = (PolylineOptions) values[0];
        distFromHome = parseDistance((String) values[1]);
        timeFromHome = parseDuration((String) values[2]);
    }

    public double parseDistance(String value) {
        double distance = -1;
        value = value.replaceAll("[^0-9.]", "");
        distance = Double.parseDouble(value);
        return distance;
    }

    public int parseDuration(String value) {
        int duration = -1, hrDur = 0;

        //in the case that it takes 1+ hrs to get to campus
        if (value.contains("hr")) {
            String hrs = value.substring(0, value.indexOf("hr"));
            hrs.replaceAll("[^0-9.]", "");
            hrDur = 60 * Integer.parseInt(hrs);
            //trim first half
            value = value.substring(value.indexOf("hr"));
        }

        value = value.replaceAll("[^0-9.]", "");
        duration = Integer.parseInt(value);
        return duration + hrDur;
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
    public void calculateNetCost() {
        netCost = 0;
        for (int i = 0; i < costTypes; i++) {
            netCost += costs[i];
        }
    }

    public String costBreakdown() {
        String info = "Cost breakdown:\n";
        if (costTypes > 0) {
            for (int i = 0; i < costTypes; i++) {
                if (i + 1 == costTypes)
                    info += String.format("%s: \t$%.2f per year", costId[i], costs[i]);
                else
                    info += String.format("%s: \t$%.2f per year\n", costId[i], costs[i]);
            }
        } else {
            info += "no costs!";
        }
        return info;
    }

    public void calculateCosts() {

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

    public double getCosts(int i) {
        return costs[i];
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

    public double getNetEmissions() {
        return netEmissions;
    }

    public String[] getPros() {
        return pros;
    }

    public String[] getCons() {
        return cons;
    }

    public PolylineOptions getDirFromSac() {
        return dirFromSac;
    }

    public PolylineOptions getDirFromHome() {
        return dirFromHome;
    }
}

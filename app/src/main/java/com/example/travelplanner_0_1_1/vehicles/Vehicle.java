package com.example.travelplanner_0_1_1.vehicles;


import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.directionhelpers.FetchUrl;
import com.example.travelplanner_0_1_1.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import java.text.DecimalFormat;
//this class contains all the common attributes of all the vehicle types
//protected attributes should not be accessed from outside of vehicles package
public abstract class Vehicle implements TaskLoadedCallback {

    //TODO: This is a temporary solution to accessing the vehicle class about each and every activity
    // however this is not the best coding practice. The supposed solution is to pass it through bundles
    // as a parcelable object which means the vehicle class needs to implement parcelable but that requires
    // more learning and testing. https://developer.android.com/reference/android/os/Parcelable
    public static final Vehicle[] vehicles = new Vehicle[6];

    public static final LatLng SAC_STATE_LOC = new LatLng(38.5645615, -121.4261736);

    //dir selection is which type of direction options want to be used when fetchURL is executed
    protected int dirSelection = 0;
    protected static final String[] DIRECTION_OPTIONS = new String[]{"driving", "walking", "bicycling", "transit"};

    //gas price per gallon
    public static final double GAS_AVG = 2.42;

    //according to https://catalog.csus.edu/academic-calendar/#spring2021text
    protected static final int DAYS_IN_SEMESTER = 147 + 10;

    //the type of transportation
    protected String type;

    protected int subType = 1;
    //max number of subtypes possible values [1, 4]
    protected int numOfSubtypes;
    protected String[] subTypeId;

    protected double timeFromSac = -1;
    protected double timeFromHome = -1;

    //string values
    protected String strTimeFromSac;
    protected String strTimeFromHome;

    protected double avgMpg = 0;
    protected double gasCost = 0;

    //size of costs array
    protected int numOfCosts = 0;
    // array for the different costs
    protected double[] costs;
    //how many different costs included ex: insurance = cost[0], maintenance = cost[1], etc...
    protected String[] costId;
    protected double netCost = 0;

    protected double distFromSac = -1;
    protected double distFromHome = -1;

    //string values
    protected String strDistFromSac;
    protected String strDistFromHome;

    protected double avgEmissions;
    protected double netEmissions;

    private FetchUrl fetchUrlFromSac;
    private FetchUrl fetchUrlFromHome;

    private PolylineOptions dirFromSac;
    private PolylineOptions dirFromHome;

    protected int backgroundId = R.drawable.car_title_image;
    protected int infoDescriptionId = R.string.app_string_info;
    protected int quickDescriptionId = R.string.app_name;

    public void setDirections(LatLng home, TaskLoadedCallback taskLoadedCallback, Context context) {
        fetchUrlFromHome = new FetchUrl(taskLoadedCallback, "from home");
        fetchUrlFromHome.execute(getUrl(SAC_STATE_LOC, home, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from Sac");

        fetchUrlFromSac = new FetchUrl(this, "from sac");
        fetchUrlFromSac.execute(getUrl(home, SAC_STATE_LOC, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from home");

    }

    public void setDirections(LatLng home, Context context) {
        if (home != null) {
            fetchUrlFromHome = new FetchUrl(this, "from home");
            fetchUrlFromHome.execute(getUrl(SAC_STATE_LOC, home, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from Sac");

            fetchUrlFromSac = new FetchUrl(this, "from home");
            fetchUrlFromSac.execute(getUrl(home, SAC_STATE_LOC, DIRECTION_OPTIONS[dirSelection], context), DIRECTION_OPTIONS[dirSelection], "from home");
        } else{
            clearDirections();
        }
    }

    //when the setDirections are completed
    @Override
    public void onTaskDone(String key, Object... values) {
        if (key.equals("from home")) {
            if (values[0] != null) {
                dirFromSac = (PolylineOptions) values[0];
                strDistFromSac = (String) values[1];
                strTimeFromSac = (String) values[2];
                distFromSac = parseDistance(strDistFromSac);
                timeFromSac = parseDuration(strTimeFromSac);
            } else {
                dirFromSac = null;
                distFromSac = -1;
                timeFromSac = -1;
            }

        } else {
            if (values[0] != null) {
                updateHomeDir(values);
            } else {
                dirFromHome = null;
                distFromHome = -1;
                timeFromHome = -1;
            }
        }
        calculateCosts();
    }

    public void updateHomeDir(Object... values) {
        dirFromHome = (PolylineOptions) values[0];
        strDistFromHome = (String) values[1];
        strTimeFromHome = (String) values[2];
        distFromHome = parseDistance(strDistFromHome);
        timeFromHome = parseDuration(strTimeFromHome);
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

    public void clearDirections(){
        timeFromSac = -1;
        timeFromHome = -1;
        distFromSac = -1;
        distFromHome = -1;
        dirFromSac = null;
        dirFromHome = null;
        calculateCosts();
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

    public static int getIndex(String type) {
        int i;
        for (i = 0; i < 5; i++) {
            if (vehicles[i].getTitle().equalsIgnoreCase(type))
                break;
        }
        return i;
    }
    public void setSubType(int subType) {
        //error checking to prevent input greater than max
        this.subType = Math.min(subType, numOfSubtypes);
        updateSubType();
    }

    //Abstract methods to implement in sub classes

    //not abstract but may be overridden by child class to calculate additional costs
    public abstract void calculateCosts();

    //how the subtypes will be calculated
    public abstract void updateSubType();

    //since different for every type
    public abstract void calculateEmissions();

    public abstract void calculateGas();

    //we can have this made here since its adding up all the fields
    public void calculateNetCost() {
        netCost = 0;
        for (int i = 0; i < numOfCosts; i++) {
            netCost += costs[i];
        }
    }

    //Display methods for displaying the data

    public String printNetCost() {
        DecimalFormat tf = new DecimalFormat("##,###.00");
        if (netCost == 0)
            return "Annual net Cost: $0!";
        return String.format("Annual net cost: $%.2f", netCost);

    }

    public String printCostBreakdown() {
        String info = "";

        if (numOfCosts > 0) {
            info = "Cost breakdown:\n";
            DecimalFormat tf = new DecimalFormat("##,###.00");
            for (int i = 0; i < numOfCosts; i++) {
                if (i + 1 == numOfCosts)
                    info += String.format("%s: \u0009$%.2f per year", costId[i], costs[i]);
                else
                    info += String.format("%s: \u0009$%.2f per year\n", costId[i], costs[i]);
            }
        } else {
            info += "no costs!";
        }
        return info;
    }

    public String printEmissions() {
        String info = "";
        if (netEmissions == 0)
            return "Carbon emissions: NONE!";
        if (netEmissions > 1000)

            info += String.format("Carbon emissions: %.2fk grams", netEmissions / 1000);
        else
            info += String.format("Carbon emissions: %.2f grams", netEmissions);
        return info;
    }

    public String printDistance() {
        String info = "";
        if (distFromSac == -1) {
            return "no possible routes found";
        }
        info = String.format("Distance going to Sac State: %s\n", strDistFromHome);
        info += String.format("Distance going home: %s", strDistFromSac);

        return info;
    }

    public String printDuration(){
        String info;
        if (timeFromHome == -1) {
            return "unknown duration";
        }
        info = String.format("ETA going to Sac State: %s\n", strTimeFromHome);
        info += String.format("ETA going Home: %s", strTimeFromHome);

        return info;
    }

    //getter and setter methods

    public String getTitle() {
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    public String getType() {
        return type;
    }

    public int getSubType() {
        return subType;
    }

    public int getNumOfSubtypes() {
        return numOfSubtypes;
    }

    public String getSubTypeId(int i) {
        return subTypeId[i];
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

    public PolylineOptions getDirFromSac() {
        return dirFromSac;
    }

    public PolylineOptions getDirFromHome() {
        return dirFromHome;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public int getInfoDescriptionId() {
        return infoDescriptionId;
    }

    public int getQuickDescriptionId() {
        return quickDescriptionId;
    }
}
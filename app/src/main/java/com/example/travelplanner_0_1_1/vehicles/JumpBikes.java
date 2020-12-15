package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class JumpBikes extends Vehicle
{
    /*int time;
    int maintenance = 0;
    int Pass = 30;
    int MPH = 12;
    double gasPrice = 0.00;
    int avgMpg = 32;
    double money;
    double distance;
    double avgCO2 = 0.00;
    String vehicle = "JumpBikes";
    int insurance = 0;

    public JumpBikes(double jumpBikeDistance) {
        distance = jumpBikeDistance;
        setMoney();
    }

    public double getTotalC02() {
        return avgCO2 * distance * 10 * 34;
    }

    public double getMoney() {
        return money;
    }

    public int getParkingPass() {
        return Pass;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public double getDistance() {
        return distance;
    }

    public int getInsurance() {
        return insurance;
    }

    public double getGas() {
        return gasPrice / avgMpg * 10 * 34 * distance;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("###.##");
        return ("\nSac State is " + df.format(distance) + " miles away by " + vehicle
                + ".\n You should expect to spend $" + df.format(money) + " per year to drive a " + vehicle
                + ".\n");
    }

    public void setMoney() {
        money = distance / MPH * 0.32 * 10 * 54 + 1 * 540;
    }*/
    double rent;
    double avgSpeed = 14;

    public JumpBikes(){
        super();

        backgroundId = R.drawable.jump_bike;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        type = "Jump bike";

        dirSelection = 2;

        numOfSubtypes = 2;
        subTypeId = new String[]{"bike", "scooter"};

        numOfCosts = 1;
        costs = new double[]{rent};
        costId = new String[]{"rental"};
    }

    @Override
    public void calculateCosts(){
        rent = (1 + (timeFromHome + timeFromSac) * 0.32 / avgSpeed) * 340;
        costs[0] = rent;
    }

    @Override
    public void updateSubType() {
        switch (subType){
            case 1:
                // normal
                // update variables and calculations for each mode
                break;
            case 2:
                //rideshare
                break;
        }
    }

    @Override
    public void calculateEmissions() {
    }

    @Override
    public void calculateGas() {

    }
}

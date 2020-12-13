package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class Walk extends Vehicle
{
    /*int time;
    int insurance = 0;
    int maintenance = 0;
    int parkingPass = 0;
    //int fastMPH = 60;
    int MPH = 3;
    double gasPrice = 0.00;
    int avgMpg = 3;
    double money;
    double distance;
    double avgCO2 = 0.00;
    String vehicle = "Car";

    public Walk(double walkDistance)
    {
        distance = walkDistance;
        setMoney();
    }

    public double getTotalC02(){return avgCO2 * distance * 10 * 34;}
    public double getMoney()
    {
        return money;
    }

    public double getDistance()
    {
        return distance;
    }

    public int getInsurance(){return insurance;}

    public double getGas(){return gasPrice / avgMpg * 10 * 34 * distance;}

    public String toString()
    {
        DecimalFormat df = new DecimalFormat("###.##");
        return ("\nSac State is " + df.format(distance) + " miles away by " + vehicle
                + ".\n You should expect to spend $" + df.format(money) + " per year to drive a " + vehicle
                + ".\n");
    }
    public int getParkingPass() {
        return parkingPass;
    }

    public int getMaintenance()
    {
        return maintenance;
    }
    public void setMoney()
    {
        money = insurance + maintenance + parkingPass + (gasPrice / avgMpg * 10 * 34 * distance);
    }*/

    public Walk(){
        super();
        dirSelection = 1;

        backgroundId = R.drawable.walking_image;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        type = "walk";

        numOfCosts = 0;

        avgEmissions = 0;
    }

    @Override
    public void updateSubType() {
        //do nothing here
    }

    @Override
    public void calculateEmissions() {
        //do nothing here

    }

    @Override
    public void calculateGas() {
        //do nothing here
    }
}

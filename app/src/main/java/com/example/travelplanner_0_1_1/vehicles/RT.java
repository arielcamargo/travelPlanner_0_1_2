package com.example.travelplanner_0_1_1.vehicles;

import java.text.DecimalFormat;
public class RT
{
    double timeBest;
    double timeWorst;
    int monthlyPass = 100;
    int fastMPH = 50;
    int slowMPH = 13;
    //	double gasfuelprice = 2.42;
//	int avgmpg = 32;
    int avgCO2 = 64; // grams/mile
    double totalCO2;
    double money;
    double distance;
    int insurance = 0;
    int parkingPass = 0;
    int maintenance = 0;
    String vehicle = "RT";
    public RT(double RTDistance)
    {
        distance = RTDistance;
        setMoney();
    }
    public double getTotalC02(){return avgCO2 * distance  * 10 * 34;}
    public double getMoney()
    {
        return money;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getGas(){return 0.00;}
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("###.##");
        return ("\nSac State is " + distance + " miles away by " + vehicle
                + ".\n You should expect to spend $" + df.format(money) + " per year using " + vehicle
                + ".\n"
                + "The total amount of CO2 emission for this distance would be about " + totalCO2 + " grams. \n"
                + "The fastest time it would take would be " + String.format("%.2f", timeBest) + " minutes.\n"
                + "The slowest time it would take would be " + String.format("%.2f", timeWorst) + " minutes.\n"
                + "\n (Note: If the distance is more than 5 miles, expect to take 2 buses. This means there could also be an extra 15 minute wait.) \n");
    }

    public void setMoney()
    {
        money = 0.00;// + (gasfuelprice / avgmpg * 10 * 34 * distance);
    }

    public int getParkingPass() {
        return parkingPass;
    }

    public int getMaintenance()
    {
        return maintenance;
    }
    public int getInsurance()
    {
        return insurance;
    }
    public double getTotalCO2()
    {
        totalCO2 = distance * avgCO2  * 10 * 34;

        return totalCO2;
    }

    public double getTimeBest()
    {
        timeBest = distance / (fastMPH * 1.0);

        // convert hours to minutes
        timeBest = timeBest * 60;

        if (distance >= 5)
        {
            timeBest = timeBest + 15; //If 5 or more miles, take two buses, but there could be a 15 minute wait.
        }

        return timeBest;
    }

    public double getTimeWorst()
    {
        timeWorst = distance / (slowMPH * 1.0);

        // convert hours to minutes
        timeWorst = timeWorst * 60;

        if (distance >= 5)
        {
            timeWorst = timeWorst + 15; //If 5 or more miles, take two buses, but there could be a 15 minute wait.
        }

        return timeWorst;
    }
}

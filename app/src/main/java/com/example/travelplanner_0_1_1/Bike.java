package com.example.travelplanner_0_1_1;

import java.text.DecimalFormat;

public class Bike
{
    double time;
    int insurance = 275;
    int maintenance = 175;
    int parkingPass = 0;
    int fastMPH = 14;
    int slowMPH = 10;
    double gasfuelprice = 0;
    int avgmpg = 0;
    double avgCO2 = 0;
    int totalCO2 = 0;
    double money;
    double distance;
    String vehicle = "Bike";

    public Bike(double bikeDistance)
    {
        distance = bikeDistance;
        setMoney();
    }

    public double getMoney()
    {
        return money;
    }


    public double getDistance()
    {
        return distance;
    }


    public double getGas(){return 0.00;}
    public double getTotalC02(){return avgCO2 * distance  * 10 * 34;}

    public String toString()
    {
        DecimalFormat df = new DecimalFormat("###.##");
        return ("\nSac State is " + distance + " miles away by " + vehicle
                + ".\n You should expect to spend $" + df.format(money) + " per year riding a " + vehicle
                + " due to possible insurance and maintenance costs.\n"
                + "The total amount of CO2 emission for this distance would be about " + totalCO2 + " grams. \n"
                + "The average time it would take would be " + String.format("%.2f", time) + " minutes.\n");
    }


    public void setMoney()
    {
        money = insurance + maintenance;// + (gasfuelprice / avgmpg * 10 * 34 * distance);
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


    public double getTime()
    {
        time = distance / ((fastMPH + slowMPH) / 2.0) * 1.0;

        // convert hours to minutes
        time = time * 60;

        return time;
    }
}


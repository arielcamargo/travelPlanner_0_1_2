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
    int avgCO2 = 0;
    int totalCO2 = 0;
    double money;
    int distance;
    String vehicle = "Bike";

    public Bike(int bikeDistance)
    {
        distance = bikeDistance;
    }

    public double getMoney()
    {
        return money;
    }

    public int getDistance()
    {
        return distance;
    }

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

    public int getInsurance()
    {
        return insurance;
    }
    public int gettotalCO2()
    {
        totalCO2 = distance * avgCO2;

        return totalCO2;
    }

    public double getTime()
    {
        time = distance / ((fastMPH + slowMPH) / 2.0) * 1.0;

        // convert hours to minutes
        time = time * 60;

        return time;
    }
}


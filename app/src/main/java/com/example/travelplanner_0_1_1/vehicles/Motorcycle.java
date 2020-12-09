package com.example.travelplanner_0_1_1.vehicles;

public class Motorcycle extends Car
{
    public Motorcycle(double carDistance)
    {
        super(carDistance);
        vehicle = "Motorcycle";
        avgMpg = 60;
        parkingPass = 44;
        insurance = 900;
        avgCO2 = 13.58; //data grabbed from https://www.bts.gov/content/estimated-national-average-vehicle-emissions-rates-vehicle-vehicle-type-using-gasoline-and   2018
        setMoney();
    }



}

package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ComparisonActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: Get distance into ComparisonActivity.java,
    // the current commented out code below and the inits don't work atm.

    //private double distance = getIntent().getDoubleExtra("miles", 0.0);

    //Car variables
    private double carTotalPrice= -1;
    private double carTotalCO2= -1;
    private double carDistance= -1;

    //Motorcycle variables
    private double motorcycleTotalPrice= -1;
    private double motorcycleTotalCO2= -1;
    private double motorCycleDistance= -1;

    //Bike variables
    private double bikeTotalPrice= -1;
    private double bikeTotalCO2= -1;
    private double bikeDistance= -1;

    //RT Variables
    private double rtTotalPrice= -1;
    private double rtTotalCO2= -1;
    private double rtDistance= -1;

    //Walk variables
    private double walkTotalPrice= -1;
    private double walkTotalCO2= -1;
    private double walkDistance= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        /*initCar();
        //Log.e("MyTag", Double.toString(carTotalPrice));
        initMotorcycle();
        initBike();
        initRT();
        initWalk();*/
    }

    @Override
    public void onClick(View v) {

    }

    /*private void initCar()
    {
        Car car = new Car(distance);
        carTotalPrice = car.getMoney();
        carTotalCO2 = car.getTotalC02();
        carDistance = car.getDistance();
    }

    private void initMotorcycle()
    {
        Motorcycle motorcycle = new Motorcycle(distance);
        motorcycleTotalPrice = motorcycle.getMoney();
        motorcycleTotalCO2 = motorcycle.getTotalC02();
        motorCycleDistance = motorcycle.getDistance();
    }

    private void initBike()
    {
        Bike bike = new Bike(distance);
        bikeTotalPrice = bike.getMoney();
        bikeTotalCO2 = bike.getTotalC02();
        bikeDistance = bike.getDistance();
    }

    private void initRT()
    {
        RT rt = new RT(distance);
        rtTotalPrice = rt.getMoney();
        rtTotalCO2 = rt.getTotalCO2();
        rtDistance = rt.getDistance();
    }

    private void initWalk()
    {
        Walk walk = new Walk(distance);
        walkTotalPrice = walk.getMoney();
        walkTotalCO2 = walk.getTotalC02();
        walkDistance = walk.getDistance();
    }*/
}
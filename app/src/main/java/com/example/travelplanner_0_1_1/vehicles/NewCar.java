package com.example.travelplanner_0_1_1.vehicles;

import android.os.Parcel;

//for the car, motorcycle, and Rt
public class NewCar extends Vehicle {

    public NewCar() {
        super();

        dirSelection = 0;

        type = "car";

        subTypes = 4;
        subTypeId = new String[]{"normal", "ride share", "hybrid", "electric"};

        avgMpg = 32;
        costTypes = 4;
        costs = new double[]{gasCost, 1462, 368, 178};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        avgEmissions = 286.68;

    }

    public NewCar(Parcel in){

    }

    // no need to call this here, will be called when setSubType is called
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
            case 3:
                // hybrid
                break;
            case 4:
                // electric
                break;

        }
    }

    @Override
    public void calculateEmissions() {
        nextEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
    }

    @Override
    public void calculateGas() {
        gasCost = GAS_AVG / avgMpg * 34 * 10 * (distFromHome + distFromSac);
    }
}

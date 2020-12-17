package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

//for the car, motorcycle, and Rt
public class Car extends Vehicle {

    public Car() {
        super();

        backgroundId = R.drawable.car_title_image;
        infoDescriptionId = R.string.car_info;
        quickDescriptionId = R.string.car_description;

        dirSelection = 0;

        type = "car";

        numOfSubtypes = 4;
        subTypeId = new String[]{"normal", "ride share", "hybrid", "electric"};

        avgMpg = 32;

        numOfCosts = 4;
        costs = new double[]{gasCost, 1462, 368, 178};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        avgEmissions = 286.68;

    }

    @Override
    public void calculateCosts() {
        calculateEmissions();
        calculateGas();
        calculateNetCost();
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
                //rides hare, uber
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
        netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
    }

    @Override
    public void calculateGas() {
        gasCost = GAS_AVG / avgMpg * 34 * 10 * (distFromHome + distFromSac);
        costs[0] = gasCost;
    }
}
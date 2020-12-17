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
        switch (subType) {
            case 1:
                avgMpg = 32;

                numOfCosts = 4;
                costs = new double[]{gasCost, 1462, 368, 178};
                costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

                avgEmissions = 286.68;
                calculateCosts();
                break;
            case 2:
                numOfCosts = 1;
                double fairPrice =  ((0.21 * (timeFromHome + timeFromSac)) + (0.78 * (distFromHome + distFromSac)) + 4.15) * 340;
                costs = new double[]{fairPrice};
                costId = new String[]{"Expected fair price"};
                avgMpg = 0;
                avgEmissions = 286.68;
                calculateCosts();
                break;
            case 3:
                avgMpg = 50;

                numOfCosts = 4;
                costs = new double[]{gasCost, 1462, 500, 178};
                costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

                avgEmissions = 286.68;
                calculateCosts();
                break;
            case 4:
                avgMpg = 0;

                numOfCosts = 3;
                costs = new double[]{1462, 900, 178};
                costId = new String[]{"insurance", "maintenance", "parking pass"};

                avgEmissions = 0;
                calculateCosts();
                break;
        }
    }

    @Override
    public void calculateEmissions() {
        netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
    }

    @Override
    public void calculateGas() {
        if(avgMpg != 0) {
            gasCost = GAS_AVG / avgMpg * 34 * 10 * (distFromHome + distFromSac);
            costs[0] = gasCost;
        } else{
            gasCost = 0;
        }
    }
}
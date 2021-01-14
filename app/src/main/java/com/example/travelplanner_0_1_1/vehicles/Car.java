/**
 * Here we adopt all aspects of Vehicle, but with some modifications.
 * We have some constants like avgMpg and avgEmissions that can be
 * changed as we get new data. Subtypes help simplify other closely related
 * cars that would not warrant their own page, but only differ by small changes in
 * the data that is involved.
 */
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
        costs = new double[]{gasCost, 1462, 368, 178*2};
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
                costs = new double[]{gasCost, 1462, 368, 178*2};
                costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

                avgEmissions = 286.68;
                calculateCosts();
                break;
            case 2:
                numOfCosts = 1;
                double fairPrice;
                if (distFromHome == -1)
                    fairPrice = ((0.21 * 45) + (0.78 * 40) + 4.15) * DAYS_IN_SEMESTER;
                else
                    fairPrice = ((0.21 * (timeFromHome + timeFromSac)) + (0.78 * (distFromHome + distFromSac)) + 4.15) * DAYS_IN_SEMESTER;
                costs = new double[]{fairPrice};
                costId = new String[]{"Expected fair price"};
                avgMpg = 0;
                avgEmissions = 286.68;
                calculateCosts();
                break;
            case 3:
                avgMpg = 50;

                numOfCosts = 4;
                costs = new double[]{gasCost, 1462, 500, 178*2};
                costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

                avgEmissions = 197.50;
                calculateCosts();
                break;
            case 4:
                avgMpg = 0;

                numOfCosts = 3;
                costs = new double[]{1462, 900, 178*2};
                costId = new String[]{"insurance", "maintenance", "parking pass"};

                avgEmissions = 0;
                calculateCosts();
                break;
        }
    }

    @Override
    public void calculateEmissions() {
        if (distFromHome == -1)
            netEmissions = (40) * 10 * 34 * avgEmissions;
        else
            netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
    }

    @Override
    public void calculateGas() {
        if (avgMpg != 0) {
            if (distFromHome != -1)
                gasCost = GAS_AVG / avgMpg * 34 * 10 * (distFromHome + distFromSac);
            else
                gasCost = GAS_AVG / avgMpg * 34 * 10 * 40;
            costs[0] = gasCost;
        } else {
            gasCost = 0;
        }
    }
}
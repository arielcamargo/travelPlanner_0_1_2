package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class Motorcycle extends Vehicle {
    public Motorcycle() {
        super();

        backgroundId = R.drawable.motorcycle_title_image;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        dirSelection = 0;

        type = "Motorcycle";

        numOfSubtypes = 2;
        subTypeId = new String[]{"normal", "electric"};

        avgMpg = 60;

        numOfCosts = 4;
        costs = new double[]{gasCost, 900, 1000, 44};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        //data grabbed from https://www.bts.gov/content/estimated-national-average-vehicle-emissions-rates-vehicle-vehicle-type-using-gasoline-and
        avgEmissions = 13.58;
    }

    @Override
    public void calculateCosts() {

    }

    @Override
    public void updateSubType() {
        switch (subType) {
            case 1:
                // normal
                // update variables and calculations for each mode
                break;
            case 2:
                //rideshare
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

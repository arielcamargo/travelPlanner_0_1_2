package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class RT extends Vehicle {
    public RT() {
        super();

        backgroundId = R.drawable.transit_title_image;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        dirSelection = 3;

        type = "transit";

        numOfSubtypes = 2;
        subTypeId = new String[]{"Bus", "light rail"};

        numOfCosts = 1;
        costs = new double[]{100};
        costId = new String[]{"monthly pass"};

        avgEmissions = 64;
    }

    @Override
    public void calculateCosts() {
        calculateEmissions();
        calculateGas();
        calculateNetCost();
    }

    @Override
    public void updateSubType() {
        switch (subType) {
            case 1:
                // normal
                // update variables and calculations for each mode
                break;
            case 2:
                break;
        }
    }

    @Override
    public void calculateEmissions() {
        netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;


    }

    @Override
    public void calculateGas() {

    }
}

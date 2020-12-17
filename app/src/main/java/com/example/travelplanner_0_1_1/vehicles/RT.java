package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class RT extends Vehicle {
    public RT() {
        super();

        backgroundId = R.drawable.transit_title_image;
        infoDescriptionId = R.string.transit_info;
        quickDescriptionId = R.string.transit_description;

        dirSelection = 3;

        type = "transit";

        //numOfSubtypes = 2;
        //subTypeId = new String[]{"Bus", "light rail"};

        numOfCosts = 0;
        //costs = new double[]{100};
        //costId = new String[]{"monthly pass"};

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
        if (timeFromHome != -1)
            netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
        else
            netEmissions = 0;

    }

    @Override
    public void calculateGas() {

    }
}

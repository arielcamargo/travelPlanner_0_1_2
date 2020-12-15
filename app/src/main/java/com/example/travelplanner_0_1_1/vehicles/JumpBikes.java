package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class JumpBikes extends Vehicle {
    double rent;
    double avgSpeed = 14;

    public JumpBikes() {
        super();

        backgroundId = R.drawable.jump_bike;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        type = "Jump bike";

        dirSelection = 2;

        numOfSubtypes = 2;
        subTypeId = new String[]{"bike", "scooter"};

        numOfCosts = 1;
        costs = new double[]{rent};
        costId = new String[]{"rental"};
    }

    @Override
    public void calculateCosts() {
        rent = (1 + (timeFromHome + timeFromSac) * 0.32 / avgSpeed) * 340;
        costs[0] = rent;
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
    }

    @Override
    public void calculateGas() {

    }
}

package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class JumpBikes extends Vehicle {
    double rent;
    double avgSpeed = 14;

    public JumpBikes() {
        super();

        backgroundId = R.drawable.jump_bike_title_image;
        infoDescriptionId = R.string.jump_bike_info;
        quickDescriptionId = R.string.jump_bike_description;

        type = "Jump bike";

        dirSelection = 2;

        numOfSubtypes = 0;
        //subTypeId = new String[]{"bike", "scooter"};

        numOfCosts = 1;
        costs = new double[]{rent};
        costId = new String[]{"rental"};
    }

    @Override
    public void calculateCosts() {
        if (distFromSac == -1)
            rent = (2 + (40) * 0.38) * Vehicle.DAYS_IN_SEMESTER;
        else
            rent = (2 + (timeFromHome + timeFromSac) * 0.38) * Vehicle.DAYS_IN_SEMESTER;
        costs[0] = rent;
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

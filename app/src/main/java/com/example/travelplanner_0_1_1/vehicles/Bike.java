package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class Bike extends Vehicle {

    public Bike() {
        super();

        dirSelection = 2;

        backgroundId = R.drawable.bike_title_image;
        descriptionId = R.string.about_this_app;
        quickDescriptionId = R.string.app_string_info;

        type = "bike";

        numOfSubtypes = 2;
        subTypeId = new String[]{"normal", "electric"};

        numOfCosts = 2;
        costs = new double[]{275, 175};
        costId = new String[]{"maintenance", "insurance"};
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

                break;
        }
    }

    @Override
    public void calculateEmissions() {
        //do nothing here
    }

    @Override
    public void calculateGas() {
        //do nothing here

    }
}


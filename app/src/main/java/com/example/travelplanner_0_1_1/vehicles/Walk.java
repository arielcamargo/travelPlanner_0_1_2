package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class Walk extends Vehicle {
    public Walk() {
        super();
        dirSelection = 1;

        backgroundId = R.drawable.walking_title_image;
        infoDescriptionId = R.string.walk_info;
        quickDescriptionId = R.string.walk_description;

        type = "walk";

        numOfCosts = 0;

        avgEmissions = 0;
    }

    @Override
    public void calculateCosts() {

    }

    @Override
    public void updateSubType() {
        //do nothing here
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

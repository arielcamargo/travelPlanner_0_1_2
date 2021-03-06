package com.example.travelplanner_0_1_1.vehicles;

import com.example.travelplanner_0_1_1.R;

public class Motorcycle extends Vehicle {

    public Motorcycle() {
        super();

        backgroundId = R.drawable.motorcycle_title_image;
        infoDescriptionId = R.string.motorcycle_info;
        quickDescriptionId = R.string.motorcycle_description;

        dirSelection = 0;

        type = "Motorcycle";

        numOfSubtypes = 2;
        subTypeId = new String[]{"normal", "electric"};

        avgMpg = 60;

        numOfCosts = 4;
        costs = new double[]{gasCost, 900, 1000, 44*2};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        //data grabbed from https://www.bts.gov/content/estimated-national-average-vehicle-emissions-rates-vehicle-vehicle-type-using-gasoline-and
        avgEmissions = 13.58;

    }

    @Override
    public void calculateCosts() {
        calculateEmissions();
        if (subType != 2)
            calculateGas();
        calculateNetCost();
    }

    @Override
    public void updateSubType() {
        switch (subType) {
            case 1:
                // normal
                avgEmissions = 13.58;
                avgMpg = 60;
                costs = new double[]{gasCost, 900, 1000, 44*2};
                costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};
                break;
            case 2:
                //0.1 = 0.1 kilowatt-hour per mile, 0.12 dollars per kwh
                double electricity;
                if (distFromHome == -1)
                    electricity = (340.0 * 40.0 * 12.0 / 1000.0);
                else
                    electricity = (340.0 * (distFromHome + distFromSac) * 12 / 1000);

                avgEmissions = 0;
                avgMpg = 0;
                costs = new double[]{electricity, 900, 1200, 44*2};
                costId = new String[]{"electricity", "insurance", "maintenance", "parking pass"};
                break;
        }
        calculateCosts();
    }

    @Override
    public void calculateEmissions() {
        if (avgEmissions != 0) {

            if (distFromHome == -1)
                netEmissions = (40) * 10 * 34 * avgEmissions;
            else
                netEmissions = (distFromHome + distFromSac) * 10 * 34 * avgEmissions;
        } else {
            netEmissions = 0;
        }
    }

    @Override
    public void calculateGas() {
        if (distFromHome == -1) {
            gasCost = GAS_AVG / avgMpg * 34 * 10 * 40;
        } else {
            gasCost = GAS_AVG / avgMpg * 34 * 10 * (distFromHome + distFromSac);
        }
        costs[0] = gasCost;
    }
}

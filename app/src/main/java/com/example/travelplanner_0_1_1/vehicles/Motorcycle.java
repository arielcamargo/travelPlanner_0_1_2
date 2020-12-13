package com.example.travelplanner_0_1_1.vehicles;

public class Motorcycle extends Vehicle
{
    public Motorcycle(){
        super();

        dirSelection = 0;

        type = "Motorcycle";

        numOfTypes = 2;
        subTypeId = new String[]{"normal", "electric"};

        avgMpg = 60;

        numOfCosts = 4;
        costs = new double[]{gasCost, 900, 1000, 44};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        //data grabbed from https://www.bts.gov/content/estimated-national-average-vehicle-emissions-rates-vehicle-vehicle-type-using-gasoline-and
        avgEmissions = 13.58;
    }

    @Override
    public void updateSubType() {
        switch (subType){
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

    /*
    public Motorcycle(double carDistance)

    {
        super(carDistance);
        vehicle = "Motorcycle";
        avgMpg = 60;
        parkingPass = 44;
        insurance = 900;
        avgCO2 = 13.58; //data grabbed from https://www.bts.gov/content/estimated-national-average-vehicle-emissions-rates-vehicle-vehicle-type-using-gasoline-and   2018
        setMoney();
    }
    */


}

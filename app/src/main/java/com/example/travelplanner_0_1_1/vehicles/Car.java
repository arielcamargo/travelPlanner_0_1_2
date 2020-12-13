package com.example.travelplanner_0_1_1.vehicles;

//for the car, motorcycle, and Rt
public class Car extends Vehicle {

    public Car() {
        super();

        dirSelection = 0;

        type = "car";

        numOfSubtypes = 4;
        subTypeId = new String[]{"normal", "ride share", "hybrid", "electric"};

        avgMpg = 32;

        numOfCosts = 4;
        costs = new double[]{gasCost, 1462, 368, 178};
        costId = new String[]{"gas", "insurance", "maintenance", "parking pass"};

        avgEmissions = 286.68;

    }

    // no need to call this here, will be called when setSubType is called
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
            case 3:
                // hybrid
                break;
            case 4:
                // electric
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

/*
public class Car {
    int time;
    int insurance = 1462;
    int maintenance = 368;
    int parkingPass = 178;
    int fastMPH = 60;
    int slowMPH = 20;
    double gasPrice = 2.42;
    int avgMpg = 32;
    double money;
    double distance;
    double avgCO2 = 286.68;
    String vehicle = "Car";

    public Car(double carDistance) {
        distance = carDistance;
        setMoney();
    }

    public double getTotalC02() {
        return avgCO2 * distance * 10 * 34;
    }

    public double getMoney() {
        return money;
    }

    public int getParkingPass() {
        return parkingPass;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public double getDistance() {
        return distance;
    }

    public int getInsurance() {
        return insurance;
    }

    public double getGas() {
        return gasPrice / avgMpg * 10 * 34 * distance;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("###.##");
        return ("\nSac State is " + df.format(distance) + " miles away by " + vehicle
                + ".\n You should expect to spend $" + df.format(money) + " per year to drive a " + vehicle
                + ".\n");
    }

    public void setMoney() {
        money = insurance + maintenance + parkingPass + (gasPrice / avgMpg * 10 * 34 * distance);
    }

}
 */
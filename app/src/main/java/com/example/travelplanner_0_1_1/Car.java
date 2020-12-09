package com.example.travelplanner_0_1_1;
import java.text.DecimalFormat;

public class Car

    {
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

        public Car(double carDistance)
        {
            distance = carDistance;
            setMoney();
        }

        public double getTotalC02(){return avgCO2 * distance;}
        public double getMoney()
        {
            return money;
        }

        public double getDistance()
        {
            return distance;
        }

        public int getInsurance(){return insurance;}

        public double getGas(){return gasPrice / avgMpg * 10 * 34 * distance;}

        public String toString()
        {
            DecimalFormat df = new DecimalFormat("###.##");
            return ("\nSac State is " + df.format(distance) + " miles away by " + vehicle
                    + ".\n You should expect to spend $" + df.format(money) + " per year to drive a " + vehicle
                    + ".\n");
        }

        public void setMoney()
        {
            money = insurance + maintenance + parkingPass + (gasPrice / avgMpg * 10 * 34 * distance);
        }

}

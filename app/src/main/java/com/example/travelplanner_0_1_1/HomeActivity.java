package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private String userLocation;
    private int userBudget;
    private double userDistance;

    private Button menuToCompare;
    private Button menuToPlanner;

    private VehicleButtonFragment display0;
    private VehicleButtonFragment display1;
    private VehicleButtonFragment display2;
    private VehicleButtonFragment display3;
    private VehicleButtonFragment display4;

    private String[] vehicleDisplayOrder = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDistance = getIntent().getDoubleExtra("miles", -1);

        menuToCompare = findViewById(R.id.menuToCompare);
        menuToCompare.setOnClickListener(this);
        menuToPlanner = findViewById(R.id.menuToPlanner);
        menuToPlanner.setOnClickListener(this);

        display0 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment0);
        display1 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        display2 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        display3 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        display4 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment4);

        VehicleButtonFragment[] fragmentOrder = new VehicleButtonFragment[]{display2, display3, display1, display4, display0};
        createButtons(fragmentOrder);
    }

    private void createButtons(VehicleButtonFragment[] fragmentOrder) {
        fragmentOrder[0].setBackgroundImg(R.drawable.car_image);
        fragmentOrder[0].setTitle("Car");
        fragmentOrder[0].setInfo("click this to get info on car");
        fragmentOrder[0].setOnClickListener(this);
        vehicleDisplayOrder[0] = "car";

        fragmentOrder[1].setBackgroundImg(R.drawable.motorcycle_image);
        fragmentOrder[1].setTitle("Motorcycle");
        fragmentOrder[1].setInfo("click this to get info on motorcycle");
        fragmentOrder[1].setOnClickListener(this);
        vehicleDisplayOrder[1] = "motorcycle";

        fragmentOrder[2].setBackgroundImg(R.drawable.transit_image);
        fragmentOrder[2].setTitle("Transit");
        fragmentOrder[2].setInfo("click this to get info on Transit");
        fragmentOrder[2].setOnClickListener(this);
        vehicleDisplayOrder[2] = "transit";

        fragmentOrder[3].setBackgroundImg(R.drawable.bike_image);
        fragmentOrder[3].setTitle("Bike");
        fragmentOrder[3].setInfo("click this to get info on Bike");
        fragmentOrder[3].setOnClickListener(this);
        vehicleDisplayOrder[3] = "bike";

        fragmentOrder[4].setBackgroundImg(R.drawable.walking_image);
        fragmentOrder[4].setTitle("Walk");
        fragmentOrder[4].setInfo("click this to get info on walk");
        fragmentOrder[4].setOnClickListener(this);
        vehicleDisplayOrder[4] = "walk";
    }

    /* intent = new Intent(this, ComparisonActivity.class);
    //    startActivity(intent);

    public void createPlan(View view){
        Intent intent = new Intent(this, PlannerActivity.class);
        startActivity(intent);
    }

    public void viewData(View view){
        Intent intent = new Intent(this, DisplayDataActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.menuToCompare:
                intent = new Intent(this, ComparisonActivity.class);
                break;
            case R.id.menuToPlanner:
                intent = new Intent(this, PlannerActivity.class);
                break;
            case R.id.vehicleButtonSelect:
            default:
                intent = new Intent(this, DisplayDataActivity.class);

                if (view == display0.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[0]);
                else if (view == display1.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[1]);
                else if (view == display2.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[2]);
                else if (view == display3.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[3]);
                else
                    intent.putExtra("type", vehicleDisplayOrder[4]);

                intent.putExtra("miles", userDistance);
                break;
        }
        startActivity(intent);
    }
}
package com.example.travelplanner_0_1_1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.fragments.VehicleButtonFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private double userDistance;

    private VehicleButtonFragment display0;
    private VehicleButtonFragment display1;
    private VehicleButtonFragment display2;
    private VehicleButtonFragment display3;
    private VehicleButtonFragment display4;
    private VehicleButtonFragment display5;

    private final String[] vehicleDisplayOrder = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userDistance = getIntent().getDoubleExtra("miles", -1);

        Button menuToCompare = findViewById(R.id.menuToCompare);
        menuToCompare.setOnClickListener(this);
        Button menuToPlanner = findViewById(R.id.menuToPlanner);
        menuToPlanner.setOnClickListener(this);

        display0 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment0);
        display1 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
        display2 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        display3 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        display4 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment4);
        display5 = (VehicleButtonFragment) getSupportFragmentManager().findFragmentById(R.id.fragment5);

        VehicleButtonFragment[] fragmentOrder = new VehicleButtonFragment[]{display5, display2, display3, display1, display4, display0};
        createButtons(fragmentOrder);

        vehicleDisplayOrder[0] = display0.getTitleText();
        vehicleDisplayOrder[1] = display1.getTitleText();
        vehicleDisplayOrder[2] = display2.getTitleText();
        vehicleDisplayOrder[3] = display3.getTitleText();
        vehicleDisplayOrder[4] = display4.getTitleText();
        vehicleDisplayOrder[5] = display5.getTitleText();
    }

    private void createButtons(VehicleButtonFragment[] fragmentOrder) {
        fragmentOrder[0].setBackgroundImg(R.drawable.car_image);
        fragmentOrder[0].setTitle("Car");
        fragmentOrder[0].setInfo("click this to get info on car");
        fragmentOrder[0].setOnClickListener(this);

        fragmentOrder[1].setBackgroundImg(R.drawable.motorcycle_image);
        fragmentOrder[1].setTitle("Motorcycle");
        fragmentOrder[1].setInfo("click this to get info on motorcycle");
        fragmentOrder[1].setOnClickListener(this);

        fragmentOrder[2].setBackgroundImg(R.drawable.transit_image);
        fragmentOrder[2].setTitle("Transit");
        fragmentOrder[2].setInfo("click this to get info on Transit");
        fragmentOrder[2].setOnClickListener(this);

        fragmentOrder[3].setBackgroundImg(R.drawable.bike_image);
        fragmentOrder[3].setTitle("Bike");
        fragmentOrder[3].setInfo("click this to get info on Bike");
        fragmentOrder[3].setOnClickListener(this);

        fragmentOrder[4].setBackgroundImg(R.drawable.walking_image);
        fragmentOrder[4].setTitle("Walk");
        fragmentOrder[4].setInfo("click this to get info on walk");
        fragmentOrder[4].setOnClickListener(this);

        fragmentOrder[5].setBackgroundImg(R.drawable.jump_bike);
        fragmentOrder[5].setTitle("Jump Bike");
        fragmentOrder[5].setInfo("click this to get info on Jump Bikes");
        fragmentOrder[5].setOnClickListener(this);
    }

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
                display0.getVehicleSelect().setEnabled(false);
                display1.getVehicleSelect().setEnabled(false);
                display2.getVehicleSelect().setEnabled(false);
                display3.getVehicleSelect().setEnabled(false);
                display4.getVehicleSelect().setEnabled(false);
                display5.getVehicleSelect().setEnabled(false);


                intent = new Intent(this, DisplayDataActivity.class);

                if (view == display0.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[0]);
                else if (view == display1.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[1]);
                else if (view == display2.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[2]);
                else if (view == display3.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[3]);
                else if(view == display4.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[4]);
                else
                    intent.putExtra("type", vehicleDisplayOrder[5]);

                intent.putExtra("vehicleDisplayOrder", vehicleDisplayOrder);
                intent.putExtra("miles", userDistance);

                display0.getVehicleSelect().setEnabled(true);
                display1.getVehicleSelect().setEnabled(true);
                display2.getVehicleSelect().setEnabled(true);
                display3.getVehicleSelect().setEnabled(true);
                display4.getVehicleSelect().setEnabled(true);
                display5.getVehicleSelect().setEnabled(true);
                break;
        }
        startActivity(intent);
    }
}
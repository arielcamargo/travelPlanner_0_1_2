package com.example.travelplanner_0_1_1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.fragments.VehicleButtonFragment;
import com.example.travelplanner_0_1_1.vehicles.Vehicle;
import com.google.android.gms.maps.model.LatLng;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private VehicleButtonFragment display0;
    private VehicleButtonFragment display1;
    private VehicleButtonFragment display2;
    private VehicleButtonFragment display3;
    private VehicleButtonFragment display4;
    private VehicleButtonFragment display5;
    private VehicleButtonFragment[] fragmentOrder;

    private Vehicle[] vehicles;
    private final String[] vehicleDisplayOrder = new String[6];
    private String[] order;

    private LatLng homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeAddress = getIntent().getParcelableExtra("LatLng");
        order = getIntent().getStringArrayExtra("order");

        vehicles = Vehicle.vehicles;
        getIntent().removeExtra("order");


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

        fragmentOrder = new VehicleButtonFragment[]{display0, display1, display2, display3, display4, display5};
    }

    private void setOrder(String[] order) {
        for (int i = 0; i < 6; i++) {
            swap(vehicles, Vehicle.getIndex(order[i]), i);
        }
    }

    private void swap(Vehicle[] vehicles, int a, int b) {
        Vehicle temp = vehicles[a];
        vehicles[a] = vehicles[b];
        vehicles[b] = temp;
    }

    private void createButtons(VehicleButtonFragment[] fragmentOrder) {
        for (int i = 0; i < 6; i++) {

            fragmentOrder[i].setBackgroundImg(vehicles[i].getBackgroundId());
            fragmentOrder[i].setTitle(vehicles[i].getTitle());
            fragmentOrder[i].setInfo(getString(vehicles[i].getQuickDescriptionId()));
            fragmentOrder[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.menuToCompare:
                intent = new Intent(this, ComparisonActivity.class);
                break;
            case R.id.menuToPlanner:
                intent = new Intent(this, SurveyActivity.class);
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
                else if (view == display4.getVehicleSelect())
                    intent.putExtra("type", vehicleDisplayOrder[4]);
                else
                    intent.putExtra("type", vehicleDisplayOrder[5]);

                intent.putExtra("vehicleDisplayOrder", vehicleDisplayOrder);
                intent.putExtra("LatLng", homeAddress);

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

    @Override
    public void onResume() {
        super.onResume();
        setOrder(order);
        createButtons(fragmentOrder);

        for (int i = 0; i < 6; i++)
            vehicleDisplayOrder[i] = vehicles[i].getTitle();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
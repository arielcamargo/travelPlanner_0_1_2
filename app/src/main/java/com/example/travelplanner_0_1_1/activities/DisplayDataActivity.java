package com.example.travelplanner_0_1_1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.vehicles.Bike;
import com.example.travelplanner_0_1_1.vehicles.Car;
import com.example.travelplanner_0_1_1.vehicles.JumpBikes;
import com.example.travelplanner_0_1_1.vehicles.Motorcycle;
import com.example.travelplanner_0_1_1.vehicles.RT;
import com.example.travelplanner_0_1_1.vehicles.Vehicle;
import com.example.travelplanner_0_1_1.vehicles.Walk;

import java.text.DecimalFormat;

public class DisplayDataActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private LinearLayout displayLayout;

    //background image
    private ImageView displayBackgroundImage, displayDivider2;

    //for allowing the user to quickly change vehicle transportation modes
    private Spinner displayVehicleTitle;
    private ScrollView displayScrollView;

    //radio buttons for certain types, some can be set invisible for when there's less than 4 kinds
    private RadioGroup subTypeGroup;
    private RadioButton vehicleType1, vehicleType2, vehicleType3, vehicleType4;

    //for whichever subType the radio buttons has selected, default value is 1, values are [1,2,3,4]
    //We could cut the radio buttons for the sake of time but I would not. The sub type will only
    //affect the calculations on the screen and its text
    private int subType = 1;

    //text views for displaying all the information
    private TextView vehicleInfo, vehicleCostInfo, vehicleCostInfoBreakdown, vehicleEmissionsInfo, vehicleDistanceInfo, getVehicleEmissionsInfo;

    //map fragment for showing the distance
    private Fragment mapFragment;
    //button for toggling between distance from home to Sac State
    private Button distanceFromHome, distanceFromSacState;

    // fragment that will hold specific view that is designed specifically for each mode
    private Fragment vehicleFragmentHolder;

    //navigation buttons, show nextDisplay will show next vehicle type to display
    private Button displayToComparison, displayToPlanner, showNextDisplay;

    private String[] vehicleDisplayOrder;
    private String type;

    private Vehicle[] vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        type = getIntent().getStringExtra("type");
        vehicleDisplayOrder = getIntent().getStringArrayExtra("vehicleDisplayOrder");

        vehicles = Vehicle.vehicles;

        Car car = new Car(getIntent().getDoubleExtra("miles", 0.0));
        car.setMoney();

        displayLayout = findViewById(R.id.displayLayout);

        displayBackgroundImage = findViewById(R.id.displayBackgroundImage);
        //to be made visible/invisible when no toggle radio group
        displayDivider2 = findViewById(R.id.displayDivider2);

        displayVehicleTitle = findViewById(R.id.displayTitleVehicle);
        displayScrollView = findViewById(R.id.displayScrollView);

        subTypeGroup = findViewById(R.id.subTypeGroup);

        vehicleType1 = findViewById(R.id.vehicleType1);
        vehicleType1.setOnClickListener(this);
        vehicleType2 = findViewById(R.id.vehicleType2);
        vehicleType2.setOnClickListener(this);
        vehicleType3 = findViewById(R.id.vehicleType3);
        vehicleType3.setOnClickListener(this);
        vehicleType4 = findViewById(R.id.vehicleType4);
        vehicleType4.setOnClickListener(this);

        vehicleInfo = findViewById(R.id.vehicleInfo);
        vehicleCostInfo = findViewById(R.id.vehicleCostInfo);
        vehicleCostInfoBreakdown = findViewById(R.id.vehicleCostInfoBreakdown);
        vehicleEmissionsInfo = findViewById(R.id.vehicleEmissionsInfo);
        vehicleDistanceInfo = findViewById(R.id.vehicleDistanceInfo);
        //temporary line below
        vehicleDistanceInfo.setText(type + ", " + car.toString());

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        distanceFromHome = findViewById(R.id.distanceFromHome);
        distanceFromHome.setOnClickListener(this);
        distanceFromSacState = findViewById(R.id.distanceFromSac);
        distanceFromSacState.setOnClickListener(this);

        vehicleFragmentHolder = getSupportFragmentManager().findFragmentById(R.id.vehicleFragmentHolder);

        displayToComparison = findViewById(R.id.displayToComparison);
        displayToComparison.setOnClickListener(this);
        displayToPlanner = findViewById(R.id.displayToPlanner);
        displayToPlanner.setOnClickListener(this);
        showNextDisplay = findViewById(R.id.showNextDisplay);
        showNextDisplay.setOnClickListener(this);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, vehicleDisplayOrder);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        displayVehicleTitle.setAdapter(adapter);
        displayVehicleTitle.setOnItemSelectedListener(this);
        displayVehicleTitle.setSelection(indexOf(type));

        //edits all the components based on what vehicle type the user clicked on
        displayData(type);

    }

    /*
    *final FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.details, new NewFragmentToReplace(), "NewFragmentTag");
    ft.commit();
    */

    private int indexOf(String type) {
        int i;
        for (i = 0; i < 5; i++) {
            if (vehicleDisplayOrder[i].equalsIgnoreCase(type))
                break;
        }
        return i;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.vehicleType1:
                subType = 1;
                displayData(type);
                break;
            case R.id.vehicleType2:
                subType = 2;
                displayData(type);
                break;
            case R.id.vehicleType3:
                subType = 3;
                displayData(type);
                break;
            case R.id.vehicleType4:
                subType = 4;
                displayData(type);
                break;
            case R.id.distanceFromHome:
                //update map
                distanceFromHome.setEnabled(false);
                distanceFromSacState.setEnabled(true);
                break;
            case R.id.distanceFromSac:
                //update map but show directions from Sac State
                distanceFromHome.setEnabled(true);
                distanceFromSacState.setEnabled(false);
                break;
            case R.id.displayToComparison:
                intent = new Intent(this, ComparisonActivity.class);
                startActivity(intent);
                break;
            case R.id.displayToPlanner:
                intent = new Intent(this, PlannerActivity.class);
                startActivity(intent);
                break;
            case R.id.showNextDisplay:
                showNextDisplay.setEnabled(false);
                displayScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        int nextPos = (displayVehicleTitle.getSelectedItemPosition() + 1) % 6;

                        displayData(vehicleDisplayOrder[nextPos]);
                        displayVehicleTitle.setSelection(nextPos);
                        displayScrollView.scrollTo(0, 0);
                    }
                });
                showNextDisplay.setEnabled(true);
                break;
        }
    }

    //method for swapping to whichever mode of transportation necessary
    public void displayData(String vehicleType) {
        type = vehicleType;
        switch (vehicleType) {
            case "car":
                switch(subType) {
                    case(1):
                        initCar();
                        break;
                    case(2):
                        initMotorcycle();
                        break;
                    case(3):
                        initBike();
                        break;
                    case(4):
                        initJumpBike();
                        break;
                }
                break;
            case "motorcycle":
                initMotorcycle();
                break;
            case "transit":
                initTransit();
                break;
            case "bike":
                initBike();
                break;
            case "walk":
                initWalk();
                break;
            case "jump bike":
                initJumpBike();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String title = displayVehicleTitle.getSelectedItem().toString().toLowerCase();
        displayData(title);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initCar() {
        DecimalFormat df = new DecimalFormat("###.##");
        Car car = new Car(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.car_image);
        vehicleCostInfoBreakdown.setText("gas: $" + df.format(car.getGas()) + " per year\ninsurance: $" + df.format(car.getInsurance()) +
                " per year\nMaintenance: $" + car.getMaintenance() + "\nParking Pass: $" + car.getParkingPass() + " per year\nTotal Cost: $" + df.format(car.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + vehicles[0].getDistFromHome() + " miles");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(car.getTotalC02()) + " grams of CO2");

        updateRadioGroup();
    }

    private void initMotorcycle() {
        DecimalFormat df = new DecimalFormat("###.##");
        Motorcycle motorcycle = new Motorcycle(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.motorcycle_image);
        vehicleCostInfoBreakdown.setText("gas: $" + df.format(motorcycle.getGas()) + " per year\ninsurance: $" + df.format(motorcycle.getInsurance()) +
                " per year\nMaintenance: $" + motorcycle.getMaintenance() + "\nParking Pass: $" + motorcycle.getParkingPass() + " per year\nTotal Cost: $" + df.format(motorcycle.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + df.format(motorcycle.getDistance()) + " miles");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(motorcycle.getTotalC02()) + " grams of CO2");

        updateRadioGroup();
    }

    private void initTransit() {
        DecimalFormat df = new DecimalFormat("###.##");
        RT rt = new RT(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.transit_image);

        vehicleCostInfoBreakdown.setText("gas: $" + df.format(rt.getGas()) + " per year\ninsurance: $" + df.format(rt.getInsurance()) +
                " per year\nMaintenance: $" + rt.getMaintenance() + "\nParking Pass: $" + rt.getParkingPass() + " per year\nTotal Cost: $" + df.format(rt.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + getIntent().getDoubleExtra("miles", 0.0) + " miles one way");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(rt.getTotalC02()) + " grams of CO2 per year");
        updateRadioGroup();
    }

    private void initBike() {
        DecimalFormat df = new DecimalFormat("###.##");
        Bike bike = new Bike(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.bike_image);

        vehicleCostInfoBreakdown.setText("gas: $" + df.format(bike.getGas()) + " per year\ninsurance: $" + df.format(bike.getInsurance()) +
                " per year\nMaintenance: $" + bike.getMaintenance() + "\nParking Pass: $" + bike.getParkingPass() + " per year\nTotal Cost: $" + df.format(bike.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + getIntent().getDoubleExtra("miles", 0.0) + " miles one way");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(bike.getTotalC02()) + " grams of CO2 per year");
        updateRadioGroup();

    }

    private void initWalk() {
        DecimalFormat df = new DecimalFormat("###.##");
        Walk walk = new Walk(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.walking_image);

        vehicleCostInfoBreakdown.setText("gas: $" + df.format(walk.getGas()) + " per year\ninsurance: $" + df.format(walk.getInsurance()) +
                " per year\nMaintenance: $" + walk.getMaintenance() + "\nParking Pass: $" + walk.getParkingPass() + " per year\nTotal Cost: $" + df.format(walk.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + getIntent().getDoubleExtra("miles", 0.0) + " miles one way");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(walk.getTotalC02()) + " grams of CO2 per year");
        updateRadioGroup();
    }

    private void initJumpBike() {
        DecimalFormat df = new DecimalFormat("###.##");
        JumpBikes jumpBikes = new JumpBikes(getIntent().getDoubleExtra("miles", 0.0));
        displayBackgroundImage.setImageResource(R.drawable.jump_bike);

        vehicleCostInfoBreakdown.setText("gas: $" + df.format(jumpBikes.getGas()) + " per year\ninsurance: $" + df.format(jumpBikes.getInsurance()) +
                " per year\nMaintenance: $" + jumpBikes.getMaintenance() + "\nParking Pass: $" + jumpBikes.getParkingPass() + " per year\nTotal Cost: $" + df.format(jumpBikes.getMoney()) + " per year");
        vehicleDistanceInfo.setText("distance: " + getIntent().getDoubleExtra("miles", 0.0) + " miles one way");
        vehicleEmissionsInfo.setText("carbon emissions: " + df.format(jumpBikes.getTotalC02()) + " grams of CO2 per year");
        updateRadioGroup();
    }

    private void updateRadioGroup() {
        if (type.equals("walk")) {
            //can add other types to condition up above if no group added
            displayLayout.removeView(subTypeGroup);
            displayLayout.removeView(displayDivider2);

        } else {
            if (subTypeGroup.getParent() == null) {
                //if radio group missing
                displayLayout.addView(subTypeGroup, 2);
                displayLayout.addView(displayDivider2, 3);
            }

            if (type.equals("car")) {

                if (vehicleType4.getParent() == null) {
                    subTypeGroup.addView(vehicleType3);
                    subTypeGroup.addView(vehicleType4);
                }
                vehicleType1.setText("normal");
                vehicleType2.setText("ride share");
                vehicleType3.setText("hybrid");
                vehicleType4.setText("electric");
            } else {
                if (vehicleType4.getParent() != null) {
                    subTypeGroup.removeView(vehicleType3);
                    subTypeGroup.removeView(vehicleType4);
                }
                switch (type) {
                    case "motorcycle":
                    case "bike":
                        vehicleType1.setText("normal");
                        vehicleType2.setText("electric");
                        break;
                    case "transit":
                        vehicleType1.setText("bus");
                        vehicleType2.setText("light rail");
                        break;
                    case "jump bike":
                        vehicleType1.setText("bike");
                        vehicleType2.setText("scooter");
                        break;
                }
            }
        }
    }
}
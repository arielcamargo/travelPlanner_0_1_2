package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;

public class DisplayDataActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    //background image
    private ImageView displayBackgroundImage;

    //for allowing the user to quickly change vehicle transportation modes
    private Spinner displayVehicleTitle;
    private ScrollView displayScrollView;

    //radio buttons for certain types, some can be set invisible for when there's less than 4 kinds
    private RadioButton vehicleType1, vehicleType2, vehicleType3, vehicleType4;

    //for whichever subType the radio buttons has selected, default value is 1, values are [1,2,3,4]
    //We could cut the radio buttons for the sake of time but I would not. The sub type will only
    //affect the calculations on the screen and its text
    private int subType = 1;

    //text views for displaying all the information
    private TextView vehicleInfo, vehicleCostInfo, vehicleCostInfoBreakdown, vehicleEmissionsInfo, vehicleDistanceInfo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        type = getIntent().getStringExtra("type");
        vehicleDisplayOrder = getIntent().getStringArrayExtra("vehicleDisplayOrder");

        Car car = new Car(getIntent().getDoubleExtra("miles", 0.0));
        car.setMoney();

        displayBackgroundImage = findViewById(R.id.displayBackgroundImage);

        displayVehicleTitle = findViewById(R.id.displayTitleVehicle);
        displayScrollView = findViewById(R.id.displayScrollView);

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
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, vehicleDisplayOrder);
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
                break;
            case R.id.vehicleType2:
                subType = 2;
                break;
            case R.id.vehicleType3:
                subType = 3;
                break;
            case R.id.vehicleType4:
                subType = 4;
                break;
            case R.id.distanceFromHome:
                //update map
                break;
            case R.id.distanceFromSac:
                //update map too
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
                displayScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        String title = displayVehicleTitle.getSelectedItem().toString().toLowerCase();
                        int nextPos = (displayVehicleTitle.getSelectedItemPosition() + 1) % 5;

                        displayData(vehicleDisplayOrder[nextPos]);
                        displayVehicleTitle.setSelection(nextPos);
                        displayScrollView.scrollTo(0, 0);
                    }
                });
                break;
        }
    }

    //method for swapping to whichever mode of transportation necessary
    public void displayData(String vehicleType) {
        switch (vehicleType) {
            case "car":
                initCar();
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
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String title = displayVehicleTitle.getSelectedItem().toString().toLowerCase();
        displayData(title);
    }

    //todo fill in the rest of the methods below
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void initCar() {
        displayBackgroundImage.setImageResource(R.drawable.car_image);

    }

    private void initMotorcycle() {
        displayBackgroundImage.setImageResource(R.drawable.motorcycle_image);

    }

    private void initTransit() {
        displayBackgroundImage.setImageResource(R.drawable.transit_image);

    }

    private void initBike() {
        displayBackgroundImage.setImageResource(R.drawable.bike_image);

    }

    private void initWalk() {
        displayBackgroundImage.setImageResource(R.drawable.walking_image);
    }
}
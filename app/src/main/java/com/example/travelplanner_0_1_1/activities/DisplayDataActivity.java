package com.example.travelplanner_0_1_1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Bundle;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.vehicles.Vehicle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

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
    private TextView vehicleInfo, vehicleCostInfo, vehicleCostInfoBreakdown, vehicleEmissionsInfo, vehicleDistanceInfo, vehicleTravelTimeInfo;

    //map fragment for showing the distance
    private Fragment mapFragment;

    private TableRow mapRow;

    //button for toggling between distance from home to Sac State
    private Button distanceFromHome, distanceFromSacState;

    // fragment that will hold specific view that is designed specifically for each mode
    private Fragment vehicleFragmentHolder;

    //navigation buttons, show nextDisplay will show next vehicle type to display
    private Button displayToComparison, displayToPlanner, showNextDisplay;

    private String[] vehicleDisplayOrder;
    private String type;

    private Vehicle[] vehicles;

    private LatLng homeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        type = getIntent().getStringExtra("type");
        homeAddress = getIntent().getParcelableExtra("LatLng");
        vehicleDisplayOrder = getIntent().getStringArrayExtra("vehicleDisplayOrder");

        vehicles = Vehicle.vehicles;

        //Car car = new Car(getIntent().getDoubleExtra("miles", 0.0));
        //car.setMoney();

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
        vehicleTravelTimeInfo = findViewById(R.id.vehicleTravelTimeInfo);

        mapFragment = getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapRow = findViewById(R.id.mapRow);

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
        displayVehicleTitle.setSelection(getIndex(type));

        //edits all the components based on what vehicle type the user clicked on
        displayData(type);


    }

    /*
    *final FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.details, new NewFragmentToReplace(), "NewFragmentTag");
    ft.commit();
    */

    private int getIndex(String type) {
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
        int curr = getIndex(displayVehicleTitle.getSelectedItem().toString().toLowerCase());
        switch (view.getId()) {
            case R.id.vehicleType1:
                subType = 1;
                updateText(curr, subType);
                break;
            case R.id.vehicleType2:
                subType = 2;
                updateText(curr, subType);
                break;
            case R.id.vehicleType3:
                subType = 3;
                updateText(curr, subType);
                break;
            case R.id.vehicleType4:
                subType = 4;
                updateText(curr, subType);
                break;
            case R.id.distanceFromHome:
                //update map
                distanceFromHome.setEnabled(false);
                distanceFromSacState.setEnabled(true);
                updateMap(vehicles[curr].getDirFromHome(), vehicles[curr].getDistFromHome());

                break;
            case R.id.distanceFromSac:
                //update map but show directions from Sac State
                distanceFromHome.setEnabled(true);
                distanceFromSacState.setEnabled(false);
                updateMap(vehicles[curr].getDirFromSac(), vehicles[curr].getDistFromSac());

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
                        type = vehicleDisplayOrder[nextPos];
                    }
                });
                showNextDisplay.setEnabled(true);
                break;
        }
    }

    //method for swapping to whichever mode of transportation necessary
    public void displayData(String vehicleType) {
        setDisplay(getIndex(vehicleType));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = displayVehicleTitle.getSelectedItem().toString().toLowerCase();
        displayData(type);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateMap(PolylineOptions polylineOptions, Double distance) {
        if (homeAddress != null && distance != -1) {
            Bundle result = new Bundle();
            result.putDouble("lat", homeAddress.latitude);
            result.putDouble("lng", homeAddress.longitude);
            result.putParcelable("directions", polylineOptions);
            getSupportFragmentManager().setFragmentResult("update_directions", result);

            if (mapRow.getParent() == null) {
                ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                params.height = getResources().getDimensionPixelSize(R.dimen.map_display_height);
                mapFragment.getView().setLayoutParams(params);
                //sets it to appropriate index in layout
                displayLayout.addView(mapRow, (subTypeGroup.getParent() == null ? 9 : 11));
            }

        } else {
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = 0;
            mapFragment.getView().setLayoutParams(params);

            displayLayout.removeView(mapRow);
        }
    }

    private void setDisplay(int type) {
        displayBackgroundImage.setImageResource(vehicles[type].getBackgroundId());

        updateText(type, 1);

        //for updating the map
        if (!distanceFromHome.isEnabled())
            updateMap(vehicles[type].getDirFromHome(), vehicles[type].getDistFromHome());
        else
            updateMap(vehicles[type].getDirFromSac(), vehicles[type].getDistFromSac());
        updateRadioGroup(type);
    }

    private void updateText(int type, int subType) {
        vehicles[type].setSubType(subType);

        vehicleInfo.setText(getString(vehicles[type].getDescriptionId()));
        vehicleCostInfo.setText(vehicles[type].printNetCost());

        vehicleCostInfoBreakdown.setText(vehicles[type].printCostBreakdown());

        vehicleEmissionsInfo.setText(vehicles[type].printEmissions());

        vehicleDistanceInfo.setText(vehicles[type].printDistance());

        vehicleTravelTimeInfo.setText(vehicles[type].printDuration());
    }

    private void updateRadioGroup(int type) {
        int numOfSubtypes = vehicles[type].getNumOfSubtypes();
        switch (numOfSubtypes) {
            case 0:
            default:
                if (subTypeGroup.getParent() != null) {
                    displayLayout.removeView(subTypeGroup);
                    displayLayout.removeView(displayDivider2);
                }
                break;
            case 2:
                if (subTypeGroup.getParent() == null) {
                    displayLayout.addView(subTypeGroup, 2);
                    displayLayout.addView(displayDivider2, 3);
                }
                if (vehicleType4.getParent() != null) {
                    subTypeGroup.removeView(vehicleType3);
                    subTypeGroup.removeView(vehicleType4);
                }
                vehicleType1.setText(vehicles[type].getSubTypeId(0));
                vehicleType2.setText(vehicles[type].getSubTypeId(1));

                break;
            case 4:
                if (subTypeGroup.getParent() == null) {
                    displayLayout.addView(subTypeGroup, 2);
                    displayLayout.addView(displayDivider2, 3);
                }
                if (vehicleType4.getParent() == null) {
                    subTypeGroup.addView(vehicleType3);
                    subTypeGroup.addView(vehicleType4);
                }
                vehicleType1.setText(vehicles[type].getSubTypeId(0));
                vehicleType2.setText(vehicles[type].getSubTypeId(1));
                vehicleType3.setText(vehicles[type].getSubTypeId(2));
                vehicleType4.setText(vehicles[type].getSubTypeId(3));

                break;
        }
    }
}
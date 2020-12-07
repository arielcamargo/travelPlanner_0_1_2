package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;

public class DisplayDataActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private ImageView displayBackgroundImage;
    private Spinner displayVehicleTitle;
    private Button displayToComparison;
    private Button displayToPlanner;
    private Button showNextDisplay;
    private ScrollView displayScrollView;

    private TextView vehicleDistanceInfo;

    //TODO: add in the rest of the fields of all the UI elements in Fragment

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

        displayToComparison = findViewById(R.id.displayToComparison);
        displayToComparison.setOnClickListener(this);
        displayToPlanner = findViewById(R.id.displayToPlanner);
        displayToPlanner.setOnClickListener(this);
        showNextDisplay = findViewById(R.id.showNextDisplay);
        showNextDisplay.setOnClickListener(this);

        //initialize the rest of the components to be edited by them
        displayBackgroundImage = findViewById(R.id.displayBackgroundImage);

        displayVehicleTitle = findViewById(R.id.displayTitleVehicle);

        vehicleDistanceInfo = findViewById(R.id.vehicleDistanceInfo);
        //temporary line below
        vehicleDistanceInfo.setText(type + ", " + car.toString());

        displayScrollView = findViewById(R.id.displayScrollView);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, vehicleDisplayOrder);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        displayVehicleTitle.setAdapter(adapter);
        displayVehicleTitle.setOnItemSelectedListener(this);
        displayVehicleTitle.setSelection(indexOf(type));

        //edits all the components based on what vehicle type the user clicked on
        displayData(type);

    }

    private int indexOf(String type){
        int i;
        for( i = 0; i < 5; i++){
            if(vehicleDisplayOrder[i].equalsIgnoreCase(type))
                break;
        }
        return i;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
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

    public void displayData(String vehicleType) {
        switch (vehicleType) {
            case "car":
                displayBackgroundImage.setImageResource(R.drawable.car_image);
                break;
            case "motorcycle":
                displayBackgroundImage.setImageResource(R.drawable.motorcycle_image);
                break;
            case "transit":
                displayBackgroundImage.setImageResource(R.drawable.transit_image);
                break;
            case "bike":
                displayBackgroundImage.setImageResource(R.drawable.bike_image);
                break;
            case "walk":
                displayBackgroundImage.setImageResource(R.drawable.walking_image);
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
}
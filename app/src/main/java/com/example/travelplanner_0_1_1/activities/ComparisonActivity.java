package com.example.travelplanner_0_1_1.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.vehicles.Vehicle;
import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ComparisonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //TODO: Get distance into ComparisonActivity.java


    //TODO: Need to figure out how to import data


    private RadioButton compareByCost, compareByDistance, compareByEmissions;
    private Spinner comparisonOption, comparisonOption2;

    private TextView comparisonTitle;

    private int spacingLength = 2;
    private int graphState = 1;

    private LatLng homeAddress;
    private String type;

    private GraphView graphview;
    private Vehicle[] vehicles;
    private String[] vehicleDisplayOrder;

    private String vehicle1, vehicle2;
    private double vehicle1Cost, vehicle2Cost, vehicle1Distance, vehicle2Distance, vehicle1Emissions, vehicle2Emissions;

    private Button comparisonToSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        compareByCost = findViewById(R.id.compareByCost);
        compareByCost.setOnClickListener(this);
        compareByDistance = findViewById(R.id.compareByDistance);
        compareByDistance.setOnClickListener(this);
        compareByEmissions = findViewById(R.id.compareByEmissions);
        compareByEmissions.setOnClickListener(this);

        type = getIntent().getStringExtra("type");
        homeAddress = getIntent().getParcelableExtra("LatLng");
        vehicleDisplayOrder = getIntent().getStringArrayExtra("vehicleDisplayOrder");
        vehicles = Vehicle.vehicles;

        comparisonOption = (Spinner) findViewById(R.id.comparisonOption);
        comparisonOption.setOnItemSelectedListener(this);

        comparisonOption2 = (Spinner) findViewById(R.id.comparisonOption2);
        comparisonOption2.setOnItemSelectedListener(this);

        comparisonTitle = findViewById(R.id.comparisonTitle);

        comparisonToSurvey = findViewById(R.id.comparisonToSurvey);
        comparisonToSurvey.setOnClickListener(this);

        //comparisonTitle.setText(Double.toString(vehicles[i].getNetCost()));

        //First Data Initialization, default option is car/car for the Spinners
        //so this sets that situation up.

        int noDir = 0;
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i].getDistFromHome() == -1)
                noDir++;
        }
        compareByDistance.setEnabled(noDir < 1);


        vehicle1 = "car";
        vehicle2 = vehicle1;
        int i = Vehicle.getIndex(vehicle1);

        vehicle1Cost = vehicles[i].getNetCost();
        vehicle2Cost = vehicle1Cost;
        vehicle1Distance = vehicles[i].getDistFromSac();
        vehicle2Distance = vehicle1Distance;
        vehicle1Emissions = vehicles[i].getNetEmissions();
        vehicle2Emissions = vehicle1Emissions;

        graphview = (GraphView) findViewById(R.id.comparisonGraph);
        updateBarGraph(vehicle1, vehicle2, vehicle1Cost, vehicle2Cost);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compareByCost:
                updateBarGraph(vehicle1, vehicle2, vehicle1Cost, vehicle2Cost);
                graphState = 1;
                break;
            case R.id.compareByDistance:
                updateBarGraph(vehicle1, vehicle2, vehicle1Distance, vehicle2Distance);
                graphState = 2;
                break;
            case R.id.compareByEmissions:
                updateBarGraph(vehicle1, vehicle2, vehicle1Emissions, vehicle2Emissions);
                graphState = 3;
                break;
            case R.id.comparisonToSurvey:
                Intent intent = new Intent(this, SurveyActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void updateBarGraph(String leftString, String rightString, double value1, double value2) {
        graphview = (GraphView) findViewById(R.id.comparisonGraph);
        graphview.removeAllSeries();
        /*BarGraphSeries<DataPoint> baseGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(0,0),
                }
        );
        graphview.addSeries(baseGraph);*/

        BarGraphSeries<DataPoint> firstGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(1, value1)
                }
        );
        firstGraph.setColor(Color.RED);
        firstGraph.setSpacing(spacingLength);
        firstGraph.setAnimated(true);
        firstGraph.setDrawValuesOnTop(true);
        firstGraph.setValuesOnTopColor(Color.BLUE);
        graphview.addSeries(firstGraph);

        BarGraphSeries<DataPoint> secondGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(2, value2)
                }
        );
        secondGraph.setColor(Color.GREEN);
        secondGraph.setSpacing(spacingLength);
        secondGraph.setAnimated(true);
        secondGraph.setDrawValuesOnTop(true);
        secondGraph.setValuesOnTopColor(Color.BLUE);
        graphview.addSeries(secondGraph);

        graphview.getViewport().setXAxisBoundsManual(true);
        graphview.getViewport().setYAxisBoundsManual(true);
        graphview.getViewport().setMinY(0.0);
        graphview.getViewport().setMaxY(maxYValue(value1, value2));
        graphview.getViewport().setMinX(0.5);
        graphview.getViewport().setMaxX(2.5);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphview);
        staticLabelsFormatter.setHorizontalLabels(new String[]{vehicle1, vehicle2, ""});
        graphview.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        //comparisonTitle.setText(item);
        int vehiclePos = Vehicle.getIndex(item);


        switch (parent.getId()) {
            case R.id.comparisonOption:
                //comparisonTitle.setText(item);
                vehicle1 = item;
                vehicle1Cost = vehicles[vehiclePos].getNetCost();
                vehicle1Distance = vehicles[vehiclePos].getDistFromSac();
                vehicle1Emissions = vehicles[vehiclePos].getNetEmissions();
                double vehicle1Change = applyChangedVehicle(vehicle1Cost, vehicle1Distance, vehicle1Emissions);
                double vehicle2Number = applyChangedVehicle(vehicle2Cost, vehicle2Distance, vehicle2Emissions);
                updateBarGraph(vehicle1, vehicle2, vehicle1Change, vehicle2Number);
                break;

            case R.id.comparisonOption2:
                //comparisonTitle.setText("test2");
                vehicle2 = item;
                vehicle2Cost = vehicles[vehiclePos].getNetCost();
                vehicle2Distance = vehicles[vehiclePos].getDistFromSac();
                vehicle2Emissions = vehicles[vehiclePos].getNetEmissions();
                double vehicle1Number = applyChangedVehicle(vehicle1Cost, vehicle1Distance, vehicle1Emissions);
                double vehicle2Change = applyChangedVehicle(vehicle2Cost, vehicle2Distance, vehicle2Emissions);
                updateBarGraph(vehicle1, vehicle2, vehicle1Number, vehicle2Change);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private double maxYValue(double number1, double number2)
    {
        double maxNumber = Math.max(number1 + 1, number2);
        return maxNumber * 1.1;
    }


    private double applyChangedVehicle(double cost, double distance, double emissions) {
        if (graphState == 1) {
            return cost;
        } else if (graphState == 2) {
            return distance;
        } else {
            return emissions;
        }
    }

    private String stringType() {
        if (graphState == 1) {
            return "In Dollars";
        } else if (graphState == 2) {
            return "In Miles";
        } else {
            return "CO2 in grams";
        }
    }
}
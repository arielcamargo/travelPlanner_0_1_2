package com.example.travelplanner_0_1_1.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplanner_0_1_1.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ComparisonActivity extends AppCompatActivity implements View.OnClickListener {
    //TODO: Get distance into ComparisonActivity.java


    //TODO: Need to figure out how to import data

    //private double distance = getIntent().getDoubleExtra("miles", 0.0);

    private RadioButton compareByCost, compareByDistance, compareByEmissions;

    private int spacingLength = 2;

    GraphView graphview;

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

        graphview = (GraphView) findViewById(R.id.comparisonGraph);
        updateBarGraph("JumpBike", "Bike", 6, 6);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.compareByCost:
                updateBarGraph("JumpBike", "Bike", 6, 6);
                break;
            case R.id.compareByDistance:
                updateBarGraph("Car", "Bike", 1, 2);
                break;
            case R.id.compareByEmissions:
                updateBarGraph("RT", "Bike", 10, 5);
                break;
        }
    }

    public void updateBarGraph(String leftString, String rightString, double value1, double value2)
    {
        graphview = (GraphView) findViewById(R.id.comparisonGraph);
        graphview.removeAllSeries();
        BarGraphSeries<DataPoint> baseGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(0,0),
                }
        );
        graphview.addSeries(baseGraph);

        BarGraphSeries<DataPoint> firstGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(1,value1)
                }
        );
        firstGraph.setColor(Color.RED);
        firstGraph.setSpacing(spacingLength);
        firstGraph.setAnimated(true);
        firstGraph.setTitle(leftString);
        graphview.addSeries(firstGraph);

        BarGraphSeries<DataPoint> secondGraph = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(2,value2)
                }
        );
        secondGraph.setColor(Color.GREEN);
        secondGraph.setSpacing(spacingLength);
        secondGraph.setAnimated(true);
        secondGraph.setTitle(rightString);
        graphview.addSeries(secondGraph);

        BarGraphSeries<DataPoint> baseGraph2 = new BarGraphSeries<DataPoint>(new DataPoint[]
                {
                        new DataPoint(4,0),
                }
        );
        graphview.addSeries(baseGraph2);
    }


}
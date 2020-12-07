package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void compareModes(View view){
        Intent intent = new Intent(this, ComparisonActivity.class);
        startActivity(intent);
    }

    public void createPlan(View view){
        Intent intent = new Intent(this, PlannerActivity.class);
        startActivity(intent);
    }

    public void viewData(View view){
        Intent intent = new Intent(this, DisplayDataActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}
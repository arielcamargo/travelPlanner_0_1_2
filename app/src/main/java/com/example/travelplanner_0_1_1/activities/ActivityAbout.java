package com.example.travelplanner_0_1_1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelplanner_0_1_1.R;

public class ActivityAbout extends AppCompatActivity implements View.OnClickListener {

    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button goToInput = findViewById(R.id.goToInput);
        goToInput.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
    }
}
package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

public class DisplayDataActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");

        setContentView(R.layout.activity_display_data);
        Car car = new Car(getIntent().getDoubleExtra("miles", 0.0));
        car.setMoney();
        textview = findViewById(R.id.textView);
        textview.setText(type + ", " + car.toString());
    }

    @Override
    public void onClick(View v) {

    }
}
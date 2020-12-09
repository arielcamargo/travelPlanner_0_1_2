package com.example.travelplanner_0_1_1.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.travelplanner_0_1_1.R;

public class PlannerActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
    }

    public void browser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSe9FMmJBj64aQFojoDPZBMfLj1JMwvZLAVWqD_PRT049u1HFQ/viewform"));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {

    }
}
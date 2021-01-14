/**
 * MainActivity.java is the first screen the user sees.
 * There's a blurb about terms and conditions that the user
 * must accept before they can continue to use the program.
 *
 * activity_main.xml corresponds to the design of the page.
 *
 * Next Page: ActivityAbout.java
 * Previous Page: None
 */
package com.example.travelplanner_0_1_1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.travelplanner_0_1_1.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startProgram = findViewById(R.id.startProgram);
        startProgram.setOnClickListener(this);

        CheckBox userAgreement = findViewById(R.id.userAgreement);
        userAgreement.setChecked(false);
        userAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.userAgreement) {
            startProgram.setEnabled(((CheckBox) view).isChecked());

        } else {
            Intent intent = new Intent(this, ActivityAbout.class);
            startActivity(intent);
        }

    }
}

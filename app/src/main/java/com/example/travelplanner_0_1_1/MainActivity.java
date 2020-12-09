package com.example.travelplanner_0_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

//I made a commment
//hello

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startProgram;
    private CheckBox userAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startProgram = findViewById(R.id.startProgram);
        startProgram.setOnClickListener(this);

        userAgreement = findViewById(R.id.userAgreement);
        userAgreement.setChecked(false);
        userAgreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userAgreement:
                startProgram.setEnabled(((CheckBox) view).isChecked());
                break;
            case R.id.startProgram:
                Intent intent = new Intent(this, ActivityAbout.class);
                startActivity(intent);
                break;
        }
    }
}

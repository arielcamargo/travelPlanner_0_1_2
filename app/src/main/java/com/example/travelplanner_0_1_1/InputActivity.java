package com.example.travelplanner_0_1_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    //Initialize variables
    EditText etSource, etDestination;
    Button goToMenu;
    TextView textView;
    String sType;
    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
    boolean[] inputFlag = new boolean[]{false, false}; // originally flag
    double sendDouble = 0.0;

    private List<Place.Field> placeFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //Assign variable
        etSource = findViewById(R.id.getLocation);
        etDestination = findViewById(R.id.getDestination);
        goToMenu = findViewById(R.id.goToMenu);
        textView = findViewById(R.id.text_view10);

        //Initialize places
        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);

        //Set edit text non focusable
        etSource.setFocusable(false);
        etSource.setOnClickListener(this);

        //Set edit text non focusable
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(this);

        goToMenu.setOnClickListener(this);

        placeFields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);

        //Set text on text
        textView.setText("0.0 miles");
    }

    //listener for whenever a button is clicked
    @Override
    public void onClick(View view) {
        List<Place.Field> fields;
        Intent intent;
        switch (view.getId()) {
            case R.id.getLocation:
                sType = "source";

                //Create intent
                intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, placeFields
                ).build(InputActivity.this);
                //Start activity result
                startActivityForResult(intent, 100);
                break;
            case R.id.getDestination:
                //Define type
                sType = "destination";
                //Create intent
                intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, placeFields
                ).build(InputActivity.this);
                //Start activity result
                startActivityForResult(intent, 100);
                break;
            case R.id.goToMenu:
                intent = new Intent(this, DisplayDataActivity.class);
                intent.putExtra("miles", sendDouble);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //When success initialize place
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            LatLng latLng = place.getLatLng();

            //Check condition
            if (sType.equals("source")) {
                //When type is source, Increase flag value
                inputFlag[0] = true;
                //Set address on edit text
                etSource.setText(place.getAddress());
                //Get latitude and longitude
                lat1 = latLng.latitude;
                long1 = latLng.longitude;
            } else {
                //When type is destination, Increase flag value
                inputFlag[1] = true;
                //Set address on edit text
                etDestination.setText(place.getAddress());
                //Get latitude and longitude
                lat2 = latLng.latitude;
                long2 = latLng.longitude;
            }

            //Check condition
            if (inputFlag[0] && inputFlag[1]) {
                //When flag is greater than and equal to 2
                //Calculate distance
                calculateDistance(lat1, long1, lat2, long2);
            }
        } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
            //When error
            //Initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            //Display toast
            Toast.makeText(getApplicationContext(), status.getStatusMessage()
                    , Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateDistance(double lat1, double long1, double lat2, double long2) {
        //Calculate longitude difference
        double longDiff = long1 - long2;
        //Calculate distance
        double distance = Math.sin(Math.toRadians(lat1))
                * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.cos(Math.toRadians(longDiff));
        distance = Math.acos(distance);
        //Convert distance radian to degree
        distance = Math.toDegrees(distance);
        //Distance in miles
        distance = distance * 60 * 1.1515;
        sendDouble = distance;
        //Set distance on text view
        textView.setText(String.format(Locale.US, "%2f miles", distance));
    }
}
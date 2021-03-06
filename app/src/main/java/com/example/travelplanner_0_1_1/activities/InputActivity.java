/**
 * InputActivity.java requests data from the user. If they
 * choose to skip entering info a default value of 20 miles
 * away from Sac State is assumed instead. Otherwise
 * the address is sent to the AddressFragment that shows the
 * location entered, on a map. Also, multiple vehicles are
 * created from the abstract vehicle object.
 *
 * directly related files
 * Vehicle.java, AddressFragment.java
 *
 * activity_input.xml corresponds to the design of the page.
 *
 * Previous Page: ActivityAbout.java
 * Next Page: HomeActivity.java
 */
package com.example.travelplanner_0_1_1.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.directionhelpers.FetchUrl;
import com.example.travelplanner_0_1_1.directionhelpers.TaskLoadedCallback;
import com.example.travelplanner_0_1_1.fragments.AddressFragment;
import com.example.travelplanner_0_1_1.vehicles.Bike;
import com.example.travelplanner_0_1_1.vehicles.Car;
import com.example.travelplanner_0_1_1.vehicles.JumpBikes;
import com.example.travelplanner_0_1_1.vehicles.Motorcycle;
import com.example.travelplanner_0_1_1.vehicles.RT;
import com.example.travelplanner_0_1_1.vehicles.Vehicle;
import com.example.travelplanner_0_1_1.vehicles.Walk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import static com.example.travelplanner_0_1_1.vehicles.Vehicle.SAC_STATE_LOC;

public class InputActivity extends AppCompatActivity implements View.OnClickListener, PlaceSelectionListener {

    private Button goToNext;

    private AutocompleteSupportFragment getHomeAddress;
    private String address;
    private LatLng addressLatLng;

    //used for getting its calculations
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        goToNext = findViewById(R.id.goToNext);
        goToNext.setOnClickListener(this);

        String api_key = getString(R.string.google_maps_key);
        if (!Places.isInitialized())
            Places.initialize(getApplicationContext(), api_key);

        //Sets up the AutocompleteSupportFragment
        getHomeAddress = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.get_home_address);

        getHomeAddress.getView().setBackgroundColor(Color.WHITE);

        //bounds it to specific area
        assert getHomeAddress != null;
        getHomeAddress.setCountries("US");

        double BIAS_RANGE = 0.125;
        getHomeAddress.setLocationBias(
                RectangularBounds.newInstance(
                        new LatLng(SAC_STATE_LOC.latitude - BIAS_RANGE, SAC_STATE_LOC.longitude - BIAS_RANGE),
                        new LatLng(SAC_STATE_LOC.latitude + BIAS_RANGE, SAC_STATE_LOC.longitude + BIAS_RANGE)
                )
        );

        //set what data types about each place we want to return
        getHomeAddress.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        //get the address that the user selects
        getHomeAddress.setOnPlaceSelectedListener(this);
        getHomeAddress.requireView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(this);

        car = new Car();
        Vehicle.vehicles[0] = car;
        Vehicle.vehicles[1] = new Bike();
        Vehicle.vehicles[2] = new JumpBikes();
        Vehicle.vehicles[3] = new RT();
        Vehicle.vehicles[4] = new Walk();
        Vehicle.vehicles[5] = new Motorcycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goToNext:
                Intent intent = new Intent(this, HomeActivity.class);

                String[] order;
                for (int i = 1; i < 6; i++) {
                    //update directions for other vehicle modes
                    Vehicle.vehicles[i].setDirections(addressLatLng, this);
                }

                if (car.getDistFromHome() == -1) {
                    order = new String[]{"bike", "jump bike", "transit", "motorcycle", "walk", "car"};
                } else if (car.getDistFromHome() < 6) {
                    order = new String[]{"walk", "bike", "jump bike", "transit", "motorcycle", "car"};
                } else if (car.getDistFromHome() < 12) {
                    order = new String[]{"bike", "jump bike", "walk", "transit", "motorcycle", "car"};
                } else if (car.getDistFromHome() > 25) {
                    order = new String[]{"transit", "motorcycle", "car", "bike", "jump bike", "walk"};
                } else {
                    order = new String[]{"transit", "bike", "jump bike", "motorcycle", "car", "walk"};
                }

                intent.putExtra("order", order);
                intent.putExtra("LatLng", addressLatLng);
                startActivity(intent);
                break;
            case R.id.places_autocomplete_clear_button:
                //when the user clicks the X button, clear the data
                getHomeAddress.setText(null);

                goToNext.setText(R.string.skip);
                goToNext.setTextColor(getColor(R.color.colorWhite));
                goToNext.setBackgroundTintList(getResources().getColorStateList(R.color.redMaple));
                address = "";
                addressLatLng = null;
                getSupportFragmentManager().setFragmentResult("clearMap", new Bundle());
        }
    }

    @Override
    public void onPlaceSelected(@NonNull final Place place) {
        //store address
        address = place.getAddress();
        addressLatLng = place.getLatLng();

        car.setDirections(addressLatLng, new TaskLoadedCallback() {
            @Override
            public void onTaskDone(String key, Object... values) {
                car.updateHomeDir(values);

                Bundle result = new Bundle();
                result.putString("addressName", place.getName());
                result.putString("addressLoc", address);
                result.putDouble("lat", addressLatLng.latitude);
                result.putDouble("lng", addressLatLng.longitude);
                result.putParcelable("directions", car.getDirFromHome());

                getSupportFragmentManager().setFragmentResult("homeAddress", result);
            }
        }, this);

        goToNext.setText(R.string.go);
        goToNext.setTextColor(getColor(R.color.colorBlack));
        goToNext.setBackgroundTintList(getResources().getColorStateList(R.color.spruceGreen));

    }

    @Override
    public void onError(@NonNull Status status) {

    }
}
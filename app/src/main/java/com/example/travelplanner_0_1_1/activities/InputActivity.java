package com.example.travelplanner_0_1_1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.fragments.AddressFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class InputActivity extends AppCompatActivity implements View.OnClickListener, PlaceSelectionListener {

    private double sendDouble = -1; //default if none is given

    private Button goToNext;

    private AutocompleteSupportFragment getHomeAddress;
    private String address;
    private LatLng addressLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        goToNext = findViewById(R.id.goToNext);
        goToNext.setOnClickListener(this);

        TextView userBudget = findViewById(R.id.inputBudget);
        userBudget.setOnClickListener(this);

        Button budgetHelper = findViewById(R.id.budgetHelper);
        budgetHelper.setOnClickListener(this);

        String api_key = getString(R.string.google_maps_key);
        if (!Places.isInitialized())
            Places.initialize(getApplicationContext(), api_key);

        //Sets up the AutocompleteSupportFragment
        getHomeAddress = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.get_home_address);

        //bounds it to specific area
        assert getHomeAddress != null;
        getHomeAddress.setCountries("US");
        double BIAS_RANGE = 0.125;
        getHomeAddress.setLocationBias(
                RectangularBounds.newInstance(
                        new LatLng(AddressFragment.SAC_STATE_LOC.latitude - BIAS_RANGE, AddressFragment.SAC_STATE_LOC.longitude - BIAS_RANGE),
                        new LatLng(AddressFragment.SAC_STATE_LOC.latitude + BIAS_RANGE, AddressFragment.SAC_STATE_LOC.longitude + BIAS_RANGE)
                )
        );

        //set what data types about each place we want to return
        getHomeAddress.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));

        //get the address that the user selects
        getHomeAddress.setOnPlaceSelectedListener(this);
        getHomeAddress.requireView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(this);

        getSupportFragmentManager().setFragmentResultListener("sendDistance", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                sendDouble = bundle.getDouble("distance");
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goToNext:
                Intent intent = new Intent(this, HomeActivity.class);

                intent.putExtra("miles", sendDouble);
                startActivity(intent);
                break;
            case R.id.places_autocomplete_clear_button:
                //when the user clicks the X button, clear the data
                getHomeAddress.setText(null);
                goToNext.setText(R.string.skip);
                address = "";
                addressLatLng = null;
                sendDouble = -1;
                getSupportFragmentManager().setFragmentResult("clearMap", new Bundle());
            case R.id.budgetHelper:
                //todo: create popup that will show the user some guidelines on how to estimate their
                // budget

                break;
        }
    }

    @Override
    public void onPlaceSelected(@NonNull Place place) {
        //store address
        address = place.getAddress();
        addressLatLng = place.getLatLng();

        //passes address data to addressFragment through fragment manager
        Bundle result = new Bundle();
        result.putString("addressName", place.getName());
        result.putString("addressLoc", address);
        result.putDouble("lat", addressLatLng.latitude);
        result.putDouble("lng", addressLatLng.longitude);
        //sends address to map to update the map
        getSupportFragmentManager().setFragmentResult("homeAddress", result);

        goToNext.setText(R.string.go);
    }

    @Override
    public void onError(@NonNull Status status) {

    }
}
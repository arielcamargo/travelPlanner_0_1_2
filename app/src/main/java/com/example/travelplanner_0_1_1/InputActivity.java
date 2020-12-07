package com.example.travelplanner_0_1_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.content.Intent;
import android.location.Location;
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
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InputActivity extends AppCompatActivity implements View.OnClickListener, PlaceSelectionListener {

    private double sendDouble = -1; //default if none is given

    private Button goToNext;
    private TextView userBudget;

    private AutocompleteSupportFragment getHomeAddress;
    private Fragment addressFragment;
    private String address;
    private LatLng addressLatLng;

    private final double BIAS_RANGE = 0.075;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        goToNext = findViewById(R.id.goToNext);
        goToNext.setOnClickListener(this);

        userBudget = findViewById(R.id.inputBudget);
        userBudget.setOnClickListener(this);

        addressFragment = getSupportFragmentManager().findFragmentById(R.id.display_address);

        String api_key = getString(R.string.google_maps_key);
        if (!Places.isInitialized())
            Places.initialize(getApplicationContext(), api_key);

        //Sets up the AutocompleteSupportFragment
        getHomeAddress = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.get_home_address);

        //bounds it to specific area
        getHomeAddress.setCountries("US");
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
        getHomeAddress.getView().findViewById(R.id.places_autocomplete_clear_button).setOnClickListener(this);

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
                Intent intent = new Intent(this, DisplayDataActivity.class);

                intent.putExtra("miles", sendDouble);
                startActivity(intent);
                break;
            case R.id.places_autocomplete_clear_button:
                getHomeAddress.setText(null);
                goToNext.setText(R.string.skip);
                address = "";
                addressLatLng = null;
                getSupportFragmentManager().setFragmentResult("clearMap", null);
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
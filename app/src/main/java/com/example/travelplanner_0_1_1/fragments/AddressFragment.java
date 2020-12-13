package com.example.travelplanner_0_1_1.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.travelplanner_0_1_1.R;
import com.example.travelplanner_0_1_1.animation.LatLngInterpolator;
import com.example.travelplanner_0_1_1.animation.MarkerAnimation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class AddressFragment extends Fragment implements OnMapReadyCallback, FragmentResultListener {

    /*Google maps fragment that is used for displaying the initial route from the users home to Sac State within the InputFragment.java*/
    private MapView addressMap;
    public static final LatLng SAC_STATE_LOC = new LatLng(38.5575016, -121.4276552);
    private Marker homeMarker;
    private Polyline directions;

    //initialize map
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container,
                false);

        addressMap = (MapView) view.findViewById(R.id.mapView);
        addressMap.onCreate(savedInstanceState);
        addressMap.onResume(); // needed to get the map to display immediately

        MapsInitializer.initialize(getActivity().getApplicationContext());

        addressMap.getMapAsync(this);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up the listeners to listen for when actions want to be sent from Input fragment to this fragment
        getParentFragmentManager().setFragmentResultListener("homeAddress", this, this);
        getParentFragmentManager().setFragmentResultListener("clearMap", this, this);
        getParentFragmentManager().setFragmentResultListener("update_directions", this, this);

    }

    //method that creates the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // create marker at location
        googleMap.addMarker(new MarkerOptions()
                .position(SAC_STATE_LOC)
                .title("Sacramento State")
        );

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(SAC_STATE_LOC)
                .zoom(10)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled(false);
    }

    //Listener for when the Parent fragment (Input fragment) sends data to this fragment
    //this does not use the navigation component because we aren't moving to and from the fragment
    @Override
    public void onFragmentResult(@NonNull final String requestKey, @NonNull final Bundle result) {
        //create new callback that will update the map
        addressMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                //if the request is to create/update marker this method will create the marker and
                // draw the path needed
                if (requestKey.equals("homeAddress")) {
                    final LatLng homePos = new LatLng(result.getDouble("lat"), result.getDouble("lng"));
                    updateCamera(googleMap, homePos);
                    final PolylineOptions polylineOptions = result.getParcelable("directions");

                    if (homeMarker == null) {
                        //if no marker
                        homeMarker = googleMap.addMarker(new MarkerOptions().position(homePos).title(result.getString("addressLoc")));
                        directions = googleMap.addPolyline(polylineOptions);
                    } else {
                        //if there is marker
                        if (directions != null) {
                            directions.remove(); //clear previous directions
                        }
                        //animates the marker to move from its previous spot to new spot
                        homeMarker.setTitle(result.getString("addressLoc"));
                        MarkerAnimation.animateMarkerToGB(homeMarker, homePos, new LatLngInterpolator.Linear());

                        new Handler(Looper.getMainLooper()).postDelayed(
                                new Runnable() {
                                    public void run() {
                                        directions = googleMap.addPolyline(polylineOptions);
                                    }
                                }, MarkerAnimation.ANIMATION_DURATION);
                    }
                } else if (requestKey.equals("clearMap")) {
                    //if the request is to clear the search field
                    if (homeMarker != null) {
                        homeMarker.remove();
                        homeMarker = null;
                    }
                    if (directions != null)
                        directions.remove();
                } else {
                    //similar to homeAddress except no animations since camera doesn't move
                    final LatLng homePos = new LatLng(result.getDouble("lat"), result.getDouble("lng"));
                    final PolylineOptions polylineOptions = result.getParcelable("directions");

                    if (homeMarker == null) {
                        homeMarker = googleMap.addMarker(new MarkerOptions().position(homePos).title(result.getString("addressLoc")));
                        updateCamera(googleMap, homePos);
                    } else {
                        //if there is marker
                        if (directions != null) {
                            directions.remove(); //clear previous directions
                        }
                    }
                    directions = googleMap.addPolyline(polylineOptions);
                }
            }
        });
    }

    //readjusts the camera so that both markers are visible
    private void updateCamera(GoogleMap googleMap, LatLng homeLoc) {
        LatLngBounds.Builder viewBuilder = new LatLngBounds.Builder();
        viewBuilder.include(SAC_STATE_LOC);
        viewBuilder.include(homeLoc);
        LatLngBounds bounds = viewBuilder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onResume() {
        super.onResume();
        addressMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        addressMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addressMap.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        addressMap.onLowMemory();
    }
}
package com.animalrescueapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = "ReportActivity";
    int LOCATION_REQUEST_CODE = 10001; //Unique code for location request

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //TODO: If user selects location persmission allowed, then use current location else allow input

        //initilize SDK
        Places.initialize(getApplicationContext(), "AIzaSyBbwKEN2xO7HmUzNz3hGBMMXmkZvNXPrPY");

        //Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        //Intialize the autocomplete fragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragmentLocation);

        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.8,151),
                new LatLng(-33.8,151)
        ));

        autocompleteFragment.setCountries("IN");

        //Specify the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        //Handles event when user taps one of the predictions
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                //TODO: Handle the error
                Log.i(TAG, "An error occured:" + status);
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
               //TODO: Get info about the selected place
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }
        });
    }


}
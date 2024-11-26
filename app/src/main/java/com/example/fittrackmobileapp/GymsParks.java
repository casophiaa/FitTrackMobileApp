package com.example.fittrackmobileapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class GymsParks extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyms_parks);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

       // checkLocationPermission();
       // getCurrentLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mMap != null) {
            checkLocationPermission();
            mMap.setMyLocationEnabled(true); // Enable location if permissions are granted
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMap != null) {
            checkLocationPermission();
            mMap.setMyLocationEnabled(false); // Disable location updates to save resources
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release any map resources if necessary
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true); // Enable blue dot on the map for the current location

            // Request the current location
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();

                // Move the camera to the last known location
                LatLng currentLocation = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                // Fetch nearby gyms and parks
                fetchPlacesUsingSDK(latitude, longitude);
            } else {
                // If no location is available, set a default location
                LatLng dlsuLocation = new LatLng(14.564622, 120.993999);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dlsuLocation, 15));
                fetchPlacesUsingSDK(dlsuLocation.latitude, dlsuLocation.longitude);
            }
        } else {
            // If location permission is not granted, request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /*private void fetchNearbyPlaces(double latitude, double longitude) {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + latitude + "," + longitude +
                "&radius=2500" +
                "&type=gym|park" +
                "&key=AIzaSyAz5VUjgKK3GC2BbZhdsYWSNTsemJsNQrI";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject place = results.getJSONObject(i);
                                String name = place.getString("name");
                                JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                                double lat = location.getDouble("lat");
                                double lng = location.getDouble("lng");

                                // Add marker for each place
                                LatLng placeLatLng = new LatLng(lat, lng);
                                mMap.addMarker(new MarkerOptions().position(placeLatLng).title(name));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }*/

    /*private void fetchPlacesUsingSDK(double latitude, double longitude) {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAz5VUjgKK3GC2BbZhdsYWSNTsemJsNQrI");
        }

        PlacesClient placesClient = Places.createClient(this);

        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES);

        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        checkLocationPermission();

        placesClient.findCurrentPlace(request).addOnSuccessListener(response -> {
            for (PlaceLikelihood likelihood : response.getPlaceLikelihoods()) {
                Place place = likelihood.getPlace();

                // Filter for gyms and parks
                if (place.getTypes().contains(Place.Type.GYM) ){ //|| place.getTypes().contains(Place.Type.PARK)) {
                    LatLng placeLatLng = place.getLatLng();
                    if (placeLatLng != null) {
                        mMap.addMarker(new MarkerOptions().position(placeLatLng).title(place.getName()));
                    }
                }
            }
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
            // Toast.makeText(this, "Failed to fetch places: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }*/

    private void fetchPlacesUsingSDK(double latitude, double longitude) {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAz5VUjgKK3GC2BbZhdsYWSNTsemJsNQrI");
        }
        PlacesClient placesClient = Places.createClient(this);

        List<Place.Field> placeFields = Arrays.asList(Place.Field.DISPLAY_NAME, Place.Field.LOCATION, Place.Field.TYPES);
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        checkLocationPermission();

        placesClient.findCurrentPlace(request).addOnSuccessListener(response -> {
            for (PlaceLikelihood likelihood : response.getPlaceLikelihoods()) {
                Place place = likelihood.getPlace();

                // Log the types to debug
                Log.d("PlaceTypes", "Place name: " + place.getDisplayName() + " Types: " + place.getPlaceTypes());

                // Filter for gyms & parks
                if (place.getPlaceTypes().contains("gym") || place.getPlaceTypes().contains("park")) {
                    LatLng placeLatLng = place.getLocation();
                    if (placeLatLng != null) {
                     //   Log.d("PlaceTypes", "Place name: " + place.getDisplayName() + " Types: " + place.getPlaceTypes());
                        mMap.addMarker(new MarkerOptions().position(placeLatLng).title(place.getDisplayName()));
                    }
                }
            }
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
            // Toast.makeText(this, "Failed to fetch places: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    private void getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Move the camera to the current location
        LatLng currentLocation = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

        // Fetch nearby places using the current location
        fetchPlacesUsingSDK(latitude, longitude);
    }

}

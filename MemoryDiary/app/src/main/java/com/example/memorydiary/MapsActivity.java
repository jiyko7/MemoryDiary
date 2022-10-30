package com.example.memorydiary;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.memorydiary.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    String str = "서울역";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        start();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    void start()
    {
        Intent intent = getIntent();
        if(intent.getStringExtra("title")!=null)
        {
            str = intent.getStringExtra("title");
        }
        else
        {

        }

    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this.getBaseContext(), Locale.getDefault());
        double latitude=0;
        double longitude=0;
        int gogo=0;
        try{
            List<Address> geoResults = geocoder.getFromLocationName(str, 1);
            while (geoResults.size()==0 && gogo!=5) {
                geoResults = geocoder.getFromLocationName(str, 1);
                gogo++;
            }
            if (geoResults.size()>0) {
                Address addr = geoResults.get(0);
                latitude = addr.getLatitude();
                longitude = addr.getLongitude();
            }
        }
        catch(Exception e)
        {
           //error!
        }
        LatLng youLoc = new LatLng(latitude,longitude);

        mMap.addMarker(new MarkerOptions().position(youLoc).title(str));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(youLoc,15));

    }
}
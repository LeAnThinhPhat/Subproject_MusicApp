package com.example.myappmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(19.0f);
        mMap.setMaxZoomPreference(18.0f);
        // Add a marker in Sydney and move the camera
        LatLng sg = new LatLng( 10.822538866549818, 106.6874615265981);
        mMap.addMarker(new MarkerOptions().position(sg).title("IUH"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sg,15f));
    }
}
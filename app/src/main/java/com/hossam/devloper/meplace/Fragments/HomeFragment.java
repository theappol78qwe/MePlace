package com.hossam.devloper.meplace.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hossam.devloper.meplace.DataEncapsulation.EncapList;
import com.hossam.devloper.meplace.DataEncapsulation.EncapSearch;
import com.hossam.devloper.meplace.DataEncapsulation.EncapServices;
import com.hossam.devloper.meplace.DataEncapsulation.EncapUser;
import com.hossam.devloper.meplace.Database.DataBase;
import com.hossam.devloper.meplace.Database.SQLCommends;
import com.hossam.devloper.meplace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by devloper on 9/27/16.
 */
public class HomeFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    GoogleMap mMap;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.isIndoorEnabled();
        mMap.getUiSettings().setMapToolbarEnabled(false);


    }



}




package com.hossam.devloper.meplace.Activity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hossam.devloper.meplace.DataEncapsulation.EncapSearch;
import com.hossam.devloper.meplace.DataEncapsulation.EncapServices;
import com.hossam.devloper.meplace.Database.DataBase;
import com.hossam.devloper.meplace.Database.SQLCommends;
import com.hossam.devloper.meplace.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    DatabaseReference reference;
    DatabaseReference firebase;
    FirebaseAuth mAuth;
    EncapServices services;
    EncapServices user;
    SQLiteDatabase sqLiteDatabase;
    DataBase database;
    List<EncapSearch> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    public void friendsLocations(String id) {

        mAuth = FirebaseAuth.getInstance();

        firebase = FirebaseDatabase.getInstance().getReference("Database");

        reference = firebase.child("Users");

        final String name= reference.child(id).child("information").child("userName").getKey();

        user =new EncapServices();

        reference.child(id).child("location").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,Double > map = (Map)dataSnapshot.getValue();

                drawMarker(name,map.get("latitude"),map.get("longitude"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void arraylistData(List<EncapSearch> data ){

        for(int i=0 ;i<data.size();i++ ){

            friendsLocations(data.get(i).getFriendId());
            Log.v("Database : ",data.get(i).getFriendId());
            Log.v("Database : ",data.get(i).getUniqueID());
        }

    }

    public List<EncapSearch> getData() {

        data = new ArrayList<>();
        database = new DataBase(getApplicationContext());
        database.onOpen(sqLiteDatabase);
        sqLiteDatabase = database.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SQLCommends.SELECT_ALL_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                EncapSearch encap = new EncapSearch();
                encap.setUniqueID(cursor.getString(0));
                encap.setFriendId(cursor.getString(1));
                data.add(encap);

            } while (cursor.moveToNext());

        }
        cursor.close();
        database.close();

        return data;
    }

    private void drawMarker(String name , double lat , double lang){

        LatLng loc = new LatLng(lat, lang);
        mMap.addMarker(new MarkerOptions().position(loc).title(name).snippet("Me"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(loc), 5000, null);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference("Database");
        reference = firebase.child("Users");

        reference.child(mAuth.getCurrentUser().getUid()).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();
                services = dataSnapshot.getValue(EncapServices.class);
              //  drawMarker("Me",services.getLatitude(), services.getLongitude());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       // arraylistData(getData());
    }
}

package com.hossam.devloper.meplace.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hossam.devloper.meplace.DataEncapsulation.EncapSearch;
import com.hossam.devloper.meplace.DataEncapsulation.EncapUser;
import com.hossam.devloper.meplace.Database.DataBase;
import com.hossam.devloper.meplace.Database.SQLCommends;
import com.hossam.devloper.meplace.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchFragment extends android.support.v4.app.Fragment  {


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_layout, container, false);


        return view;
    }

}

package com.hossam.devloper.meplace.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.hossam.devloper.meplace.R;
import com.hossam.devloper.meplace.Services;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Splash();
        gpsPremision();
    }

    private void gpsPremision() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            return;
        }
    }

    private void Splash() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                mAuth = FirebaseAuth.getInstance();

                if (mAuth.getInstance().getCurrentUser() == null) {

                    startActivity(new Intent(SplashActivity.this, Login.class));
                    finish();
                } else {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 1000);

        if(mAuth.getInstance().getCurrentUser()!=null){

            startService(new Intent(getApplicationContext(), Services.class));

        }
    }
}

package com.hossam.devloper.meplace.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.hossam.devloper.meplace.ActivityNavigation.ChatActivity;
import com.hossam.devloper.meplace.Contracts.Normal;
import com.hossam.devloper.meplace.R;
import com.hossam.devloper.meplace.Services;
import com.hossam.devloper.meplace.Taps.SlidingTabLayout;
import com.hossam.devloper.meplace.Taps.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    FirebaseAuth mAuth;
    CharSequence Titles[] = {"Home", "Live", "Noti", "Search"};
    int Numboftabs = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        IntializeTaps();
        navigationDrawer();
    }


    public void IntializeTaps() {

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);

    }

    public void navigationDrawer() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_Chat) {

            startActivity(new Intent(this, ChatActivity.class).putExtra(Normal.UserString,FirebaseAuth.getInstance().getCurrentUser().getUid()));

        } else if (id == R.id.nav_group_family) {

        } else if (id == R.id.nav_group_friends) {

        } else if (id == R.id.nav_group_vip) {

        } else if (id == R.id.nav_histroy) {

        } else if (id == R.id.nav_signout) {

            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

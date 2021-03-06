package com.hossam.devloper.meplace.Taps;

/**
 * Created by devloper on 9/26/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hossam.devloper.meplace.Fragments.HomeFragment;
import com.hossam.devloper.meplace.Fragments.LiveFragment;
import com.hossam.devloper.meplace.Fragments.NotificationFragment;
import com.hossam.devloper.meplace.Fragments.SearchFragment;


/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        Fragment fragment=null;
        if(position==0){
            fragment = new HomeFragment();
        }
        else if(position==1){
            fragment=new LiveFragment();
        }
        else if(position==2){
            fragment=new NotificationFragment();
        }
        else if(position==3){
            fragment=new SearchFragment();
        }

        return fragment;



    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
package com.example.michael.getcontact;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int NoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.NoOfTabs = NumberOfTabs;

    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {

            case 0:
                return new Home();
            case 1:
                return new History();
            default:
                return null;
        }
    }

        @Override
        public int getCount () {
            return NoOfTabs;
        }
    }


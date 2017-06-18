package com.chopik_andrew.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Andrew on 18.06.2017.
 */

public class MyDescriptionPagerAdapter extends FragmentPagerAdapter {

    static final int PAGE_COUNT = 5;

    public MyDescriptionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DescriptionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}

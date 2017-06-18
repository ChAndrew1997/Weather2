package com.chopik_andrew.weather;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    private ArrayList<Integer> date;
    private double[] temp;
    private ArrayList<String> desc;
    private ArrayList<Integer> clouds;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        date = intent.getIntegerArrayListExtra("date");
        temp = intent.getDoubleArrayExtra("temp");
        desc = intent.getStringArrayListExtra("desc");
        clouds = intent.getIntegerArrayListExtra("clouds");
        count = intent.getIntExtra("count", 1);

        viewPager = (ViewPager) findViewById(R.id.pager_description);
        pagerAdapter = new MyDescriptionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

    public ArrayList<Integer> getDate() {
        return date;
    }

    public double[] getTemp() {
        return temp;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public ArrayList<Integer> getClouds() {
        return clouds;
    }

    public int getCount() {
        return count;
    }
}

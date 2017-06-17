package com.chopik_andrew.weather;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;
    ViewPager pager;
    private LocationManager locationManager;
    PagerAdapter pagerAdapter;
    SwipeRefreshLayout refreshLayout;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        findMyLocation();


        dbHelper = new DBHelper(this);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < 10; i++){
            cv.put("city", "Minsk");
            cv.put("date", 1221 + i);
            cv.put("temp", 25);
            cv.put("desc", "frost");
            cv.put("clouds", 54);
            db.insert("mytable", null, cv);
            cv.clear();
        }


        Cursor cursor = db.query("mytable", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Log.d("table", "ID = " + cursor.getInt(cursor.getColumnIndex("id")) +
                        ", city = " + cursor.getString(cursor.getColumnIndex("city")) +
                        ", date = " + cursor.getInt(cursor.getColumnIndex("date")) +
                        ", temp = " + cursor.getDouble(cursor.getColumnIndex("temp")) +
                        ", desc = " + cursor.getString(cursor.getColumnIndex("desc")) +
                        ", clouds = " + cursor.getDouble(cursor.getColumnIndex("clouds"))
                );
            } while (cursor.moveToNext());
        }

        Log.d("table", "deleted rows count = " + db.delete("mytable", null, null));

        cursor.close();
        dbHelper.close();


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLocation(location);
            locationManager.removeUpdates(locationListener);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    private void setLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }
    private void findMyLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
    }


}



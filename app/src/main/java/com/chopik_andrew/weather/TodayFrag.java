package com.chopik_andrew.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFrag extends Fragment {

    SwipeRefreshLayout refreshLayout;
    MainActivity activity;

    ListView listView;
    String[] list = {"Сегодня", "Завтра"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_frag, null);
        listView = (ListView) view.findViewById(R.id.today_list);
        MyTodayListAdapter listAdapter = new MyTodayListAdapter(getContext(), list);
        listView.setAdapter(listAdapter);
        activity = (MainActivity) getActivity();

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                activity.findMyLocation();
                refreshLayout.setRefreshing(false);
/*
                Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
*/
            }
        });

        WeatherApiManager.getInstance().downloadWeather(getContext(), 0.0, 0.0, new WeatherApiManager.LodWeatherListener() {
            @Override
            public void start() {

            }

            @Override
            public void success() {

            }

            @Override
            public void failure() {

            }
        });

        return view;
    }

}

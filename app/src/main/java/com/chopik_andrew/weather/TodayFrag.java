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


        return view;
    }

}

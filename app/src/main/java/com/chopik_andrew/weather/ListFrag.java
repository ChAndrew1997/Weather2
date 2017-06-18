package com.chopik_andrew.weather;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ListFrag extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;
    ListView listView;
    MyMainListAdapter listAdapter;
    SwipeRefreshLayout refreshLayout;
    DBConnect dbConnect;


    static ListFrag newInstance(int page) {
        ListFrag listFrag = new ListFrag();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        listFrag.setArguments(arguments);
        return listFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_frag, null);

        listView = (ListView) view.findViewById(R.id.list_main);

        dbConnect = new DBConnect(getContext());
        if(pageNumber == 1) {
            listAdapter = new MyMainListAdapter(getContext(), dbConnect.getList(5));
        }
        else{
            listAdapter = new MyMainListAdapter(getContext(), dbConnect.getList(16));
        }

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                intent.putIntegerArrayListExtra("date", dbConnect.getDate());
                intent.putExtra("temp", dbConnect.getTemp());
                intent.putStringArrayListExtra("desc", dbConnect.getDesc());
                intent.putIntegerArrayListExtra("clouds", dbConnect.getClouds());
                intent.putExtra("count", dbConnect.getCount());
                startActivity(intent);
            }
        });

        return view;
    }

}

package com.chopik_andrew.weather;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ListFrag extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;
    ListView listView;
    MyMainListAdapter listAdapter;
    final String[] fiveDays = {"1", "2", "3", "4", "5"};
    final String[] sixteenDays = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};

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

        if (pageNumber == 1)
            listAdapter = new MyMainListAdapter(getContext(), fiveDays);
        else
            listAdapter = new MyMainListAdapter(getContext(), sixteenDays);
        listView.setAdapter(listAdapter);

        return view;
    }

}

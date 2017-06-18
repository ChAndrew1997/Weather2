package com.chopik_andrew.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrew on 15.06.2017.
 */

public class MyMainListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListModel> list;
    SimpleDateFormat dateFormat;

    public MyMainListAdapter(Context context, ArrayList<ListModel> list){
        ctx = context;
        this.list = list;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
            return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.main_list_item, parent, false);
        }
        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = dateFormat.format(new Date(list.get(position).getDate() * 1000L));
        ((TextView) view.findViewById(R.id.text_item)).setText(formattedDate);
        ((TextView) view.findViewById(R.id.temp)).setText(Integer.toString((int) list.get(position).getTemp() - 273));

        return view;
    }
}

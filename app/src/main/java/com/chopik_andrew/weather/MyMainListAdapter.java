package com.chopik_andrew.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Andrew on 15.06.2017.
 */

public class MyMainListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    String[] objects;

    public MyMainListAdapter(Context context, String[] objects){
        ctx = context;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Object getItem(int position) {
        return objects[position];
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
        ((TextView) view.findViewById(R.id.text_item)).setText(objects[position]);

        return view;
    }
}

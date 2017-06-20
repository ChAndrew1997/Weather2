package com.chopik_andrew.weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrew on 18.06.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListModel> list;
    SimpleDateFormat dateFormat;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public ImageView image;
        public TextView temp;

        public ViewHolder(View v) {
            super(v);
            time = (TextView) v.findViewById(R.id.recycler_time);
            image = (ImageView) v.findViewById(R.id.recycler_image);
            temp = (TextView) v.findViewById(R.id.recycler_temp);
        }
    }

    public RecyclerAdapter(ArrayList<ListModel> dataset) {
        list = dataset;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dateFormat = new SimpleDateFormat("H:mm");

        holder.time.setText(dateFormat.format(new Date(list.get(position).getDate() * 1000L)));
        holder.temp.setText(Integer.toString((int) list.get(position).getTemp() - 273));
        String desc = list.get(position).getDesc();

        switch (desc){
            case "Rain":
                holder.image.setImageResource(R.drawable.rain);
                break;
            case "Clouds":
                holder.image.setImageResource(R.drawable.cloudy);
                break;
            case "Clear":
                holder.image.setImageResource(R.drawable.sun);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

package com.chopik_andrew.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;

    TextView date;
    ImageView image;
    TextView temp;
    TextView desc;
    TextView clouds;

    SimpleDateFormat dateFormat;

    DescriptionActivity activity;

    static DescriptionFragment newInstance(int page) {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        descriptionFragment.setArguments(arguments);
        return descriptionFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, null);

        activity = (DescriptionActivity) getActivity();
        date = (TextView) view.findViewById(R.id.description_date);
        image = (ImageView) view.findViewById(R.id.description_image);
        temp = (TextView) view.findViewById(R.id.description_temp);
        desc = (TextView) view.findViewById(R.id.description);
        clouds = (TextView) view.findViewById(R.id.clouds);

        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String pageDate = dateFormat.format(new Date(activity.getDate().get(activity.getCount() + pageNumber) * 1000L));
        String pageTemp = Integer.toString((int) activity.getTemp()[activity.getCount() + pageNumber] - 273);
        String pageClouds = Integer.toString(activity.getClouds().get(activity.getCount() + pageNumber));

        date.setText(pageDate);
        temp.setText(pageTemp);
        clouds.setText("Облачность " + pageClouds + "%");

        switch (activity.getDesc().get(activity.getCount() + pageNumber)){
            case "Rain":
                image.setImageResource(R.drawable.rain);
                desc.setText("Дождь");
                break;
            case "Clear":
                image.setImageResource(R.drawable.sun);
                desc.setText("Ясно");
                break;
            case "Clouds":
                image.setImageResource(R.drawable.cloudy);
                desc.setText("Облачно");
                break;
        }


        return view;
    }

}

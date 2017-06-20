package com.chopik_andrew.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
    RecyclerView recyclerView;

    SimpleDateFormat dateFormat;

    DBConnect dbConnect;


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

        date = (TextView) view.findViewById(R.id.description_date);
        image = (ImageView) view.findViewById(R.id.description_image);
        temp = (TextView) view.findViewById(R.id.description_temp);
        desc = (TextView) view.findViewById(R.id.description);
        clouds = (TextView) view.findViewById(R.id.clouds);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        dbConnect = new DBConnect(getContext());

        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        RecyclerAdapter  adapter = new RecyclerAdapter(divideFiveDaysListModel(dbConnect.getFiveDaysList()));
        recyclerView.setAdapter(adapter);

        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String pageDate = dateFormat.format(new Date(dbConnect.getDate().get(dbConnect.getCount() + pageNumber) * 1000L));
        String pageTemp = Integer.toString((int) dbConnect.getTemp()[dbConnect.getCount() + pageNumber] - 273);
        String pageClouds = Integer.toString(dbConnect.getClouds().get(dbConnect.getCount() + pageNumber));

        date.setText(pageDate);
        temp.setText(pageTemp);
        clouds.setText("Облачность " + pageClouds + "%");

        switch (dbConnect.getDesc().get(dbConnect.getCount() + pageNumber)){
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

    private ArrayList<ListModel> divideFiveDaysListModel(ArrayList<ListModel> fiveDays){
        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ArrayList<ListModel> oneDay = new ArrayList<>();
        int page = pageNumber;

        for (int i = 0; i < fiveDays.size(); i++){
            if(page == 0) {
                oneDay.add(fiveDays.get(i));
            }
            if(fiveDays.size() == i + 1){
                break;
            }
            if (!dateFormat.format(new Date(fiveDays.get(i).getDate() * 1000L)).equals(dateFormat.format(new Date(fiveDays.get(i + 1).getDate() * 1000L)))){
                page--;
                if (page < 0)
                    break;
            }
        }
        return oneDay;
    }

}

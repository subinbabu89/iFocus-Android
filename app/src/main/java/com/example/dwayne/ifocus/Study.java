package com.example.dwayne.ifocus;

/**
 * Created by Dwayne on 3/15/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

public class Study extends Fragment {

    CalendarView schedule;
    private ListView Schedule;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewSchedule;
        viewSchedule = inflater.inflate(R.layout.study, container, false);


        schedule = (CalendarView) viewSchedule.findViewById(R.id.Schedule);
        schedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
     @Override
     public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

         Toast.makeText(getActivity(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
     }
 }
        );
        return viewSchedule;
    }
}
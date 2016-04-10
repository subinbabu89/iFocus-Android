package com.example.dwayne.ifocus.health.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.health.pojo.Health;

import java.util.List;

/**
 * Created by Subin on 4/8/2016.
 */
public class HealthHistoryFragment extends Fragment {
    private ListView lst_health_task_history;

    private List<Health> healthHistoryObjects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health_task_history, container, false);
        init(view);
        wiredEvents();
        customizeEvents();
        return view;
    }

    private void init(View view) {
        lst_health_task_history = (ListView)view.findViewById(R.id.lst_health_task_history);
        HealthHistoryDatabase healthDatabase = new HealthHistoryDatabase(getActivity());
        healthHistoryObjects = healthDatabase.retrieveHealthHistory();

        lst_health_task_history.setAdapter(new HealthHistoryAdapter(healthHistoryObjects,getActivity()));
    }

    private void wiredEvents() {

    }

    private void customizeEvents() {
    }
}

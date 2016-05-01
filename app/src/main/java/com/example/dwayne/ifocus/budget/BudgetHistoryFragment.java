package com.example.dwayne.ifocus.budget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dwayne.ifocus.R;

import java.util.List;

/**
 * Created by Subin on 4/9/2016.
 */
public class BudgetHistoryFragment extends Fragment {
    private ListView lst_budget_task_history;

    private List<Budget> healthHistoryObjects;

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
        lst_budget_task_history = (ListView)view.findViewById(R.id.lst_health_task_history);
        BudgetHistoryDatabase budgetHistoryDatabase = new BudgetHistoryDatabase(getActivity());
        healthHistoryObjects = budgetHistoryDatabase.retrieveBudgetHistory();

        lst_budget_task_history.setAdapter(new BudgetHistoryAdapter(healthHistoryObjects,getActivity()));
    }

    private void wiredEvents() {

    }

    private void customizeEvents() {
    }}
package com.example.dwayne.ifocus.study.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.List;

/**
 * Created by Subin on 4/8/2016.
 */
public class StudyHistoryFragment extends Fragment {
    private ListView lst_study_task_history;

    private List<Study> studyHistoryObjects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_task_history, container, false);
        init(view);
        wiredEvents();
        customizeEvents();
        return view;
    }

    private void init(View view) {
        lst_study_task_history = (ListView)view.findViewById(R.id.lst_study_task_history);
        StudyHistoryDatabase studyDatabase = new StudyHistoryDatabase(getActivity());
        studyHistoryObjects = studyDatabase.retrieveStudyHistory();

        lst_study_task_history.setAdapter(new StudyHistoryAdapter(studyHistoryObjects,getActivity()));
    }

    private void wiredEvents() {

    }

    private void customizeEvents() {
    }
}

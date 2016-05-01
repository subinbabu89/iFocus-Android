package com.example.dwayne.ifocus.study;

/**
 * Created by Dwayne on 3/15/2016.
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.study.db.StudyContract;
import com.example.dwayne.ifocus.study.db.StudyLoader;
import com.example.dwayne.ifocus.study.listener.StudyDialogClickListener;
import com.example.dwayne.ifocus.study.listener.StudyEditClickListener;
import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.List;

public class StudyFragment extends Fragment implements StudyDialogClickListener,StudyEditClickListener,LoaderManager.LoaderCallbacks<List<Study>>{

    private ListView lst_study_tasks;
    private FloatingActionButton fab_add_study_task;
    private StudyTaskAdapter studyTaskAdapter;

    public static final int DIALOG_FRAGMENT = 1;

    private ContentResolver contentResolver;
    private static final int LOADER_ID = 1;
    private List<Study> studyList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = init(inflater, container);
        wiredEvents();
        customizeEvents();

        return view;
    }

    private View init(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.fragment_study, container, false);
        lst_study_tasks = (ListView)v.findViewById(R.id.lst_study_tasks);
        fab_add_study_task = (FloatingActionButton)v.findViewById(R.id.fab_add_study_task);

        contentResolver = getActivity().getContentResolver();
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return v;
    }

    private void wiredEvents() {
        studyTaskAdapter = new StudyTaskAdapter(this);
        lst_study_tasks.setAdapter(studyTaskAdapter);

        fab_add_study_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                StudyDialogFragment editNameDialog = new StudyDialogFragment();
                editNameDialog.setTargetFragment(StudyFragment.this, DIALOG_FRAGMENT);
                editNameDialog.show(fm, "fragment_add_task");
            }
        });

    }

    private void customizeEvents() {
    }

    @Override
    public void onCreateOKClick(Study studyTaskObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudyContract.StudyColumns.STUDY_TYPE,studyTaskObject.getType());
        contentValues.put(StudyContract.StudyColumns.STUDY_DESCRIPTION,studyTaskObject.getDescription());
        contentValues.put(StudyContract.StudyColumns.STUDY_DATE,studyTaskObject.getDate());
        contentValues.put(StudyContract.StudyColumns.STUDY_COMPLETION,Boolean.FALSE);

        Uri returnedUri = contentResolver.insert(StudyContract.URI_TABLE,contentValues);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditOKClick(Study study) {
        ContentValues contentValues =new ContentValues();
        contentValues.put(StudyContract.StudyColumns.STUDY_TYPE,study.getType());
        contentValues.put(StudyContract.StudyColumns.STUDY_DESCRIPTION,study.getDescription());
        contentValues.put(StudyContract.StudyColumns.STUDY_DATE,study.getDate());
        contentValues.put(StudyContract.StudyColumns.STUDY_COMPLETION,study.isCompletion()? 1 : 0);
        Uri uri = StudyContract.Study.buildStudyUri(String.valueOf(study.getId()));

        int recordedUpdated = contentResolver.update(uri,contentValues,null,null);
        Toast.makeText(getActivity(), "updated "+recordedUpdated, Toast.LENGTH_SHORT).show();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Study>> onCreateLoader(int id, Bundle args) {
        contentResolver = getActivity().getContentResolver();
        return new StudyLoader(getActivity(),StudyContract.URI_TABLE,contentResolver);
    }



    @Override
    public void onLoadFinished(Loader<List<Study>> loader, List<Study> data) {
        studyList = data;
        studyTaskAdapter.setData(studyList);
        studyTaskAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<Study>> loader) {
        studyTaskAdapter.setData(null);
    }

    @Override
    public void onDeleteClicked(Study study) {
        Uri uri = StudyContract.Study.buildStudyUri(String.valueOf(study.getId()));
        contentResolver.delete(uri,null,null);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditClicked(Study study) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        StudyDialogFragment studyDialogFragment = new StudyDialogFragment();
        studyDialogFragment.setTargetFragment(this, StudyFragment.DIALOG_FRAGMENT);
        Bundle bundle = new Bundle();
        bundle.putString(StudyContract.StudyColumns.STUDY_TYPE,study.getType());
        bundle.putString(StudyContract.StudyColumns.STUDY_DESCRIPTION,study.getDescription());
        bundle.putInt(StudyContract.StudyColumns.STUDY_ID,study.getId());
        studyDialogFragment.setArguments(bundle);
        studyDialogFragment.show(fm, "fragment_add_task");


    }

    @Override
    public void markChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
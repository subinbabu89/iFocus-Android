package com.example.dwayne.ifocus.health;

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

import com.example.dwayne.ifocus.MainActivity;
import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.db.HealthLoader;
import com.example.dwayne.ifocus.health.history.HealthHistoryDao;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;
import com.example.dwayne.ifocus.health.listener.HealthDialogClickListener;
import com.example.dwayne.ifocus.health.listener.HealthEditClickListener;
import com.example.dwayne.ifocus.health.pojo.Health;

import java.util.List;

public class HealthFragment extends Fragment implements HealthDialogClickListener,HealthEditClickListener,LoaderManager.LoaderCallbacks<List<Health>>{

    private ListView lst_health_tasks;
    private FloatingActionButton fab_add_health_task;
    private HealthTaskAdapter healthTaskAdapter;

    public static final int DIALOG_FRAGMENT = 1;

    private ContentResolver contentResolver;
    private static final int LOADER_ID = 1;
    private List<Health> healthList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = init(inflater, container);
        wiredEvents();
        customizeEvents();

        return view;
    }

    private View init(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.health, container, false);
        lst_health_tasks = (ListView)v.findViewById(R.id.lst_health_tasks);
        fab_add_health_task = (FloatingActionButton)v.findViewById(R.id.fab_add_health_task);

        contentResolver = getActivity().getContentResolver();
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return v;
    }

    private void wiredEvents() {
        healthTaskAdapter = new HealthTaskAdapter(this);
        lst_health_tasks.setAdapter(healthTaskAdapter);

        fab_add_health_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                HealthDialogFragment editNameDialog = new HealthDialogFragment();
                editNameDialog.setTargetFragment(HealthFragment.this, DIALOG_FRAGMENT);
                editNameDialog.show(fm, "fragment_add_task");
            }
        });

    }

    private void customizeEvents() {
    }

    @Override
    public void onCreateOKClick(Health healthTaskObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(HealthContract.HealthColumns.HEALTH_TYPE,healthTaskObject.getType());
        contentValues.put(HealthContract.HealthColumns.HEALTH_DESCRIPTION,healthTaskObject.getDescription());
        contentValues.put(HealthContract.HealthColumns.HEALTH_DATE,healthTaskObject.getDate());
        contentValues.put(HealthContract.HealthColumns.HEALTH_COMPLETION,Boolean.FALSE);

        Uri returnedUri = contentResolver.insert(HealthContract.URI_TABLE,contentValues);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditOKClick(Health health) {
        ContentValues contentValues =new ContentValues();
        contentValues.put(HealthContract.HealthColumns.HEALTH_TYPE,health.getType());
        contentValues.put(HealthContract.HealthColumns.HEALTH_DESCRIPTION,health.getDescription());
        contentValues.put(HealthContract.HealthColumns.HEALTH_DATE,health.getDate());
        contentValues.put(HealthContract.HealthColumns.HEALTH_COMPLETION,health.isCompletion()? 1 : 0);
        Uri uri = HealthContract.Health.buildHealthUri(String.valueOf(health.getId()));

        int recordedUpdated = contentResolver.update(uri,contentValues,null,null);
        Toast.makeText(getActivity(), "updated "+recordedUpdated, Toast.LENGTH_SHORT).show();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Health>> onCreateLoader(int id, Bundle args) {
        contentResolver = getActivity().getContentResolver();
        return new HealthLoader(getActivity(),HealthContract.URI_TABLE,contentResolver);
    }



    @Override
    public void onLoadFinished(Loader<List<Health>> loader, List<Health> data) {
        healthList = data;
        healthTaskAdapter.setData(healthList);
        healthTaskAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<Health>> loader) {
        healthTaskAdapter.setData(null);
    }

    @Override
    public void onDeleteClicked(Health health) {
        Uri uri = HealthContract.Health.buildHealthUri(String.valueOf(health.getId()));
        contentResolver.delete(uri,null,null);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditClicked(Health health) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        HealthDialogFragment healthDialogFragment = new HealthDialogFragment();
        healthDialogFragment.setTargetFragment(this, HealthFragment.DIALOG_FRAGMENT);
        Bundle bundle = new Bundle();
        bundle.putString(HealthContract.HealthColumns.HEALTH_TYPE,health.getType());
        bundle.putString(HealthContract.HealthColumns.HEALTH_DESCRIPTION,health.getDescription());
        bundle.putInt(HealthContract.HealthColumns.HEALTH_ID,health.getId());
        healthDialogFragment.setArguments(bundle);
        healthDialogFragment.show(fm, "fragment_add_task");


    }

    @Override
    public void markChanged() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
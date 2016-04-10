package com.example.dwayne.ifocus.health.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.dwayne.ifocus.health.pojo.Health;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subin on 4/8/2016.
 */
public class HealthLoader extends AsyncTaskLoader<List<Health>> {
    private static final String LOG_TAG = HealthLoader.class.getSimpleName();
    private List<Health> healthList;
    private ContentResolver contentResolver;
    private Cursor cursor;


    public HealthLoader(Context context, Uri uri, ContentResolver contentResolver) {
        super(context);
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Health> loadInBackground() {
        String[] projection = {HealthContract.HealthColumns.HEALTH_ID,
                HealthContract.HealthColumns.HEALTH_DESCRIPTION,
                HealthContract.HealthColumns.HEALTH_TYPE,
                HealthContract.HealthColumns.HEALTH_DATE,
                HealthContract.HealthColumns.HEALTH_COMPLETION};

        List<Health> healthEntries = new ArrayList<>();
        cursor = contentResolver.query(HealthContract.URI_TABLE,projection,null,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_ID));
                    String type = cursor.getString(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_TYPE));
                    String description = cursor.getString(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_DESCRIPTION));
                    String date = cursor.getString(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_DATE));
                    int completion = cursor.getInt(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_COMPLETION));
                    boolean completionStatus = completion == 1 ? Boolean.TRUE : Boolean.FALSE;

                    Health health = new Health(type,description,date,completionStatus);
                    health.setId(id);
                    healthEntries.add(health);
                }while(cursor.moveToNext());
            }
        }
        return healthEntries;
    }

    @Override
    public void deliverResult(List<Health> data) {
        if(isReset()){
            if(data!=null){
                cursor.close();
            }
        }

        List<Health> oldHealthList = healthList;
        if(healthList==null||healthList.size()==0){
            Log.d(LOG_TAG,"no data received");
        }
        healthList = data;

        if (isStarted()){
            super.deliverResult(data);
        }

        if(oldHealthList!=null && oldHealthList!=data){
            cursor.close();
        }

    }

    @Override
    protected void onStartLoading() {
        if(healthList!=null){
            deliverResult(healthList);
        }

        if(takeContentChanged()||healthList==null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(cursor!=null){
            cursor.close();
        }
        healthList = null;
    }

    @Override
    public void onCanceled(List<Health> data) {
        super.onCanceled(data);
        if(cursor!=null){
            cursor.close();
        }
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}

package com.example.dwayne.ifocus.study.db;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subin on 4/8/2016.
 */
public class StudyLoader extends AsyncTaskLoader<List<Study>> {
    private static final String LOG_TAG = StudyLoader.class.getSimpleName();
    private List<Study> studyList;
    private ContentResolver contentResolver;
    private Cursor cursor;


    public StudyLoader(Context context, Uri uri, ContentResolver contentResolver) {
        super(context);
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Study> loadInBackground() {
        String[] projection = {StudyContract.StudyColumns.STUDY_ID,
                StudyContract.StudyColumns.STUDY_DESCRIPTION,
                StudyContract.StudyColumns.STUDY_TYPE,
                StudyContract.StudyColumns.STUDY_DATE,
                StudyContract.StudyColumns.STUDY_COMPLETION};

        List<Study> studyEntries = new ArrayList<>();
        cursor = contentResolver.query(StudyContract.URI_TABLE,projection,null,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_ID));
                    String type = cursor.getString(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_TYPE));
                    String description = cursor.getString(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_DESCRIPTION));
                    String date = cursor.getString(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_DATE));
                    int completion = cursor.getInt(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_COMPLETION));
                    boolean completionStatus = completion == 1 ? Boolean.TRUE : Boolean.FALSE;

                    Study study = new Study(type,description,date,completionStatus);
                    study.setId(id);
                    studyEntries.add(study);
                }while(cursor.moveToNext());
            }
        }
        return studyEntries;
    }

    @Override
    public void deliverResult(List<Study> data) {
        if(isReset()){
            if(data!=null){
                cursor.close();
            }
        }

        List<Study> oldStudyList = studyList;
        if(studyList==null||studyList.size()==0){
            Log.d(LOG_TAG,"no data received");
        }
        studyList = data;

        if (isStarted()){
            super.deliverResult(data);
        }

        if(oldStudyList!=null && oldStudyList!=data){
            cursor.close();
        }

    }

    @Override
    protected void onStartLoading() {
        if(studyList!=null){
            deliverResult(studyList);
        }

        if(takeContentChanged()||studyList==null){
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
        studyList = null;
    }

    @Override
    public void onCanceled(List<Study> data) {
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

package com.example.dwayne.ifocus.study.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.dwayne.ifocus.db.iFocusDatabase;

/**
 * Created by Subin on 4/9/2016.
 */
public class StudyProvider extends ContentProvider {

    private iFocusDatabase studyDatabase;

    private static final int STUDY = 104;
    private static final int STUDY_ID = 105;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher =new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = StudyContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,"study",STUDY);
        matcher.addURI(authority,"study/*",STUDY_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        studyDatabase = new iFocusDatabase(getContext());
        return true;
    }

    public void deleteDatabase(){
        studyDatabase.close();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = studyDatabase.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(iFocusDatabase.Tables.STUDY);

        switch (match){
            case STUDY:
                break;
            case STUDY_ID:
                String id = StudyContract.Study.getStudyId(uri);
                builder.appendWhere(StudyContract.StudyColumns.STUDY_ID + " = "+id);
                break;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }

        Cursor cursor = builder.query(database,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match){
            case STUDY:
                return StudyContract.Study.CONTENT_TYPE;
            case STUDY_ID:
                return StudyContract.Study.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = studyDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        switch (match){
            case STUDY:
                long recordID = database.insertOrThrow(iFocusDatabase.Tables.STUDY,null,values);
                return StudyContract.Study.buildStudyUri(String.valueOf(recordID));
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase database = studyDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        if(uri.equals(StudyContract.BASE_CONTENT_URI)){
            deleteDatabase();
            return 0;
        }

        switch (match){
            case STUDY_ID:
                String id = StudyContract.Study.getStudyId(uri);
                String selectionCriteria = StudyContract.StudyColumns.STUDY_ID + " = " + id
                        + (!TextUtils.isEmpty(selection)?" AND (" + selection + ")":"");
                return  database.delete(iFocusDatabase.Tables.STUDY,selectionCriteria,selectionArgs);
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = studyDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        String selectionCriteria = selection;

        switch (match){
            case STUDY:
                break;
            case STUDY_ID:
                String id = StudyContract.Study.getStudyId(uri);
                selectionCriteria = StudyContract.StudyColumns.STUDY_ID + " = " + id
                        +(!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
                break;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
        return database.update(iFocusDatabase.Tables.STUDY,values,selectionCriteria,selectionArgs);
    }
}

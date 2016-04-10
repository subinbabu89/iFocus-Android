package com.example.dwayne.ifocus.health.db;

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
public class HealthProvider extends ContentProvider {

    private iFocusDatabase healthDatabase;

    private static final int HEALTH = 100;
    private static final int HEALTH_ID = 101;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher =new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HealthContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,"health",HEALTH);
        matcher.addURI(authority,"health/*",HEALTH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        healthDatabase = new iFocusDatabase(getContext());
        return true;
    }

    public void deleteDatabase(){
        healthDatabase.close();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = healthDatabase.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(iFocusDatabase.Tables.HEALTH);

        switch (match){
            case HEALTH:
                break;
            case HEALTH_ID:
                String id = HealthContract.Health.getHealthId(uri);
                builder.appendWhere(HealthContract.HealthColumns.HEALTH_ID + " = "+id);
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
            case HEALTH:
                return HealthContract.Health.CONTENT_TYPE;
            case HEALTH_ID:
                return HealthContract.Health.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = healthDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        switch (match){
            case HEALTH:
                long recordID = database.insertOrThrow(iFocusDatabase.Tables.HEALTH,null,values);
                return HealthContract.Health.buildHealthUri(String.valueOf(recordID));
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase database = healthDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        if(uri.equals(HealthContract.BASE_CONTENT_URI)){
            deleteDatabase();
            return 0;
        }

        switch (match){
            case HEALTH_ID:
                String id = HealthContract.Health.getHealthId(uri);
                String selectionCriteria = HealthContract.HealthColumns.HEALTH_ID + " = " + id
                        + (!TextUtils.isEmpty(selection)?" AND (" + selection + ")":"");
                return  database.delete(iFocusDatabase.Tables.HEALTH,selectionCriteria,selectionArgs);
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = healthDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        String selectionCriteria = selection;

        switch (match){
            case HEALTH:
                break;
            case HEALTH_ID:
                String id = HealthContract.Health.getHealthId(uri);
                selectionCriteria = HealthContract.HealthColumns.HEALTH_ID + " = " + id
                        +(!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
                break;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
        return database.update(iFocusDatabase.Tables.HEALTH,values,selectionCriteria,selectionArgs);
    }
}

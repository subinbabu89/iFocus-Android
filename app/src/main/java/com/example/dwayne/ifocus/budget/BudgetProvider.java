package com.example.dwayne.ifocus.budget;

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
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetProvider extends ContentProvider {

    private iFocusDatabase budgetDatabase = new iFocusDatabase(getContext());

    private static final int BUDGET = 100;
    private static final int BUDGET_ID = 101;

    private static final UriMatcher URI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher =new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BudgetContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,"budget",BUDGET);
        matcher.addURI(authority,"budget/*",BUDGET_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        budgetDatabase = new iFocusDatabase(getContext());
        return true;
    }

    public void deleteDatabase(){
        budgetDatabase.close();
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase database = budgetDatabase.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(iFocusDatabase.Tables.BUDGET);

        switch (match){
            case BUDGET:
                break;
            case BUDGET_ID:
                String id = BudgetContract.Budget.getBudgetId(uri);
                builder.appendWhere(BudgetContract.BudgetColumns.BUDGET_ID + " = "+id);
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
            case BUDGET:
                return BudgetContract.Budget.CONTENT_TYPE;
            case BUDGET_ID:
                return BudgetContract.Budget.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = budgetDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        switch (match){
            case BUDGET:
                long recordID = database.insertOrThrow(iFocusDatabase.Tables.BUDGET,null,values);
                return BudgetContract.Budget.buildBudgetUri(String.valueOf(recordID));
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase database = budgetDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        if(uri.equals(BudgetContract.BASE_CONTENT_URI)){
            deleteDatabase();
            return 0;
        }

        switch (match){
            case BUDGET_ID:
                String id = BudgetContract.Budget.getBudgetId(uri);
                String selectionCriteria = BudgetContract.BudgetColumns.BUDGET_ID + " = " + id
                        + (!TextUtils.isEmpty(selection)?" AND (" + selection + ")":"");
                return  database.delete(iFocusDatabase.Tables.BUDGET,selectionCriteria,selectionArgs);
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = budgetDatabase.getWritableDatabase();
        final int match = URI_MATCHER.match(uri);

        String selectionCriteria = selection;

        switch (match){
            case BUDGET:
                break;
            case BUDGET_ID:
                String id = BudgetContract.Budget.getBudgetId(uri);
                selectionCriteria = BudgetContract.BudgetColumns.BUDGET_ID + " = " + id
                        +(!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
                break;
            default:
                throw new IllegalArgumentException("unKnown URI "+ uri);
        }
        return database.update(iFocusDatabase.Tables.BUDGET,values,selectionCriteria,selectionArgs);
    }
}

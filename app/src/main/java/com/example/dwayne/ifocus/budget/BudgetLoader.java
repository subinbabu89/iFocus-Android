package com.example.dwayne.ifocus.budget;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.example.dwayne.ifocus.budget.Budget;

import java.util.List;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetLoader extends AsyncTaskLoader<List<Budget>> {
    private static final String LOG_TAG = BudgetLoader.class.getSimpleName();
    private List<Budget> budgetList;
    private ContentResolver contentResolver;
    private Cursor cursor;


    public BudgetLoader(Context context, Uri uri, ContentResolver contentResolver) {
        super(context);
        this.contentResolver = contentResolver;
    }

    @Override
    public List<Budget> loadInBackground() {
        String[] projection = {BudgetContract.BudgetColumns.BUDGET_ID,
                BudgetContract.BudgetColumns.BUDGET_AMOUNT,
                BudgetContract.BudgetColumns.BUDGET_DESCRIPTION,
                };

        List<Budget> BudgetEntries = new ArrayList<>();
        cursor = contentResolver.query(BudgetContract.URI_TABLE,projection,null,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_ID));
                    String desc = cursor.getString(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION));
                    float amt = cursor.getFloat(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_AMOUNT));


                    Budget Budget = new Budget(amt, desc);
                    Budget.setId(id);
                    BudgetEntries.add(Budget);
                }while(cursor.moveToNext());
            }
        }
        return BudgetEntries;
    }

    @Override
    public void deliverResult(List<Budget> data) {
        if(isReset()){
            if(data!=null){
                cursor.close();
            }
        }

        List<Budget> oldBudgetList = budgetList;
        if( budgetList==null||budgetList.size()==0){
            Log.d(LOG_TAG,"no data received");
        }
        budgetList = data;

        if (isStarted()){
            super.deliverResult(data);
        }

        if(oldBudgetList!=null && oldBudgetList!=data){
            cursor.close();
        }

    }

    @Override
    protected void onStartLoading() {
        if(budgetList!=null){
            deliverResult(budgetList);
        }

        if(takeContentChanged()||budgetList==null){
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
        budgetList = null;
    }

    @Override
    public void onCanceled(List<Budget> data) {
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

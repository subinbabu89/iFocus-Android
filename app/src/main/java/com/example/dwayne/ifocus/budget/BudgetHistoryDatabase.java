package com.example.dwayne.ifocus.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.dwayne.ifocus.db.iFocusDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetHistoryDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME = "ifocus.db";
    private final Context context;

    public interface Columns{
        String BUDGET_ID = BaseColumns._ID;
        String BUDGET_AMOUNT = "Amount";
        String BUDGET_DESCRIPTION = "Description";

    }


    public interface Tables{
        String BUDGET_HISTORY = "budget_history";
        String BUDGET = "budget";
    }

    public BudgetHistoryDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String s = "CREATE TABLE IF NOT EXISTS "+ Tables.BUDGET_HISTORY+" ("
                + Columns.BUDGET_ID+" INTEGER NOT NULL,"
                + Columns.BUDGET_DESCRIPTION+" TEXT NOT NULL,"
                + Columns.BUDGET_AMOUNT+" FLOAT NOT NULL)";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version =oldVersion;
        if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.BUDGET_HISTORY);
            onCreate(db);
        }
        //DATABASE_VERSION++;
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public void insertHistoryRecord(BudgetHistoryDao budgetHistoryDao){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.BUDGET_ID,budgetHistoryDao.get_id());
        contentValues.put(Columns.BUDGET_DESCRIPTION,budgetHistoryDao.getDescription());
        contentValues.put(Columns.BUDGET_AMOUNT,budgetHistoryDao.getAmount());

        database.insert(Tables.BUDGET_HISTORY,null,contentValues);

    }

    public List<Budget> retrieveBudgetHistory(){
        String sql = "SELECT " + "p." + BudgetContract.BudgetColumns.BUDGET_ID +", " +
                "p." + BudgetContract.BudgetColumns.BUDGET_DESCRIPTION + ", " +
                "p." + BudgetContract.BudgetColumns.BUDGET_AMOUNT + ", " +
                " FROM " + Tables.BUDGET_HISTORY + " s " +
                " JOIN " + iFocusDatabase.Tables.BUDGET + " p "+
                " WHERE p." + BudgetContract.BudgetColumns.BUDGET_ID +
                " = s." + Columns.BUDGET_ID;

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        List<Budget> budgets = new ArrayList<>();

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int _id = cursor.getInt(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_ID));
                    String des = cursor.getString(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION));
                    float amt = cursor.getFloat(cursor.getColumnIndex(BudgetContract.BudgetColumns.BUDGET_AMOUNT));


                    Budget budget = new Budget( amt, des);
                    budget.setId(_id);
                    budgets.add(budget);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d("BUDGET","budgets size "+budgets.size());
        return budgets;
    }
}

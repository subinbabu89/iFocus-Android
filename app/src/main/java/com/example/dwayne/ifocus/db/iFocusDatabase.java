package com.example.dwayne.ifocus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dwayne.ifocus.budget.BudgetContract;
import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;
import com.example.dwayne.ifocus.budget.BudgetHistoryDatabase;
import com.example.dwayne.ifocus.study.db.StudyContract;
import com.example.dwayne.ifocus.study.history.StudyHistoryDatabase;
import com.example.dwayne.ifocus.user.UserDatabase;

/**
 * Created by Subin on 4/8/2016.
 */
public class iFocusDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ifocus.db";
    private static final int DATABASE_VERSION= 1;
    //private static int DATABASE_VERSION= 1;
    private final Context context;

    private iFocusDatabase healthDatabase;

    public interface Tables{
        String USER = "user";
        String HEALTH = "health";
        String BUDGET = "budget";
        String STUDY = "study";
    }

    public iFocusDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + iFocusDatabase.Tables.USER + " ("
                        + UserDatabase.UserColumns.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + UserDatabase.UserColumns.USER_EMAIL_ID + " TEXT NOT NULL,"
                        + UserDatabase.UserColumns.USER_PASSWORD + " TEXT NOT NULL,"
                        + UserDatabase.UserColumns.NAME + " TEXT NOT NULL,"
                        + UserDatabase.UserColumns.SUPER_EMAIL_ID + " TEXT NOT NULL,"
                        + UserDatabase.UserColumns.TYPE + " INTEGER NOT NULL,"
                        + UserDatabase.UserColumns.BUDGET + " INTEGER NOT NULL,"
                        + UserDatabase.UserColumns.SCORE + " INTEGER NOT NULL)"
        );

        db.execSQL("CREATE TABLE " + iFocusDatabase.Tables.HEALTH + " ("
                + HealthContract.HealthColumns.HEALTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HealthContract.HealthColumns.HEALTH_TYPE + " TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_DESCRIPTION + " TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_DATE + " TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_COMPLETION + " INTEGER NOT NULL)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + HealthHistoryDatabase.Tables.HEALTH_HISTORY + " ("
                + HealthHistoryDatabase.Columns.HEALTH_ID + " INTEGER NOT NULL,"
                + HealthHistoryDatabase.Columns.HEALTH_COMPLETION_DATE + " TEXT NOT NULL,"
                + HealthHistoryDatabase.Columns.HEALTH_COMPLETION_STATUS + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + iFocusDatabase.Tables.BUDGET + " ("
                + BudgetContract.BudgetColumns.BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BudgetContract.BudgetColumns.BUDGET_AMOUNT + " REAL NOT NULL,"
                + BudgetContract.BudgetColumns.BUDGET_DESCRIPTION + " TEXT NOT NULL)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + BudgetHistoryDatabase.Tables.BUDGET_HISTORY + " ("
                + BudgetHistoryDatabase.Columns.BUDGET_ID + " INTEGER NOT NULL,"
                + BudgetHistoryDatabase.Columns.BUDGET_AMOUNT + " REAL  NOT NULL,"
                + BudgetHistoryDatabase.Columns.BUDGET_DESCRIPTION + " TEXT NOT NULL)"
        );

        db.execSQL("CREATE TABLE " + iFocusDatabase.Tables.STUDY + " ("
                + StudyContract.StudyColumns.STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StudyContract.StudyColumns.STUDY_TYPE + " TEXT NOT NULL,"
                + StudyContract.StudyColumns.STUDY_DESCRIPTION + " TEXT NOT NULL,"
                + StudyContract.StudyColumns.STUDY_DATE + " TEXT NOT NULL,"
                + StudyContract.StudyColumns.STUDY_COMPLETION + " INTEGER NOT NULL)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + StudyHistoryDatabase.Tables.STUDY_HISTORY + " ("
                + StudyHistoryDatabase.Columns.STUDY_ID + " INTEGER NOT NULL,"
                + StudyHistoryDatabase.Columns.STUDY_COMPLETION_DATE + " TEXT NOT NULL,"
                + StudyHistoryDatabase.Columns.STUDY_COMPLETION_STATUS + " INTEGER NOT NULL)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version =oldVersion;
        if(version !=DATABASE_VERSION){
            db.execSQL("DROP TABLE IF EXISTS "+ iFocusDatabase.Tables.HEALTH);
            db.execSQL("DROP TABLE IF EXISTS "+ HealthHistoryDatabase.Tables.HEALTH_HISTORY);
            db.execSQL("DROP TABLE IF EXISTS "+ iFocusDatabase.Tables.BUDGET);
            db.execSQL("DROP TABLE IF EXISTS "+ iFocusDatabase.Tables.STUDY);
            onCreate(db);
        }
        //DATABASE_VERSION++;
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public boolean touchDB() {
        boolean _status = false;
        SQLiteDatabase _db = null;
        try {
            _db = this.getWritableDatabase();
            if (_db != null)
                _status = true;
        } catch (Exception ex) {
            _status = false;
        } finally {
            try {
                _db.close();
            } catch (Exception ex) {
                // Do nothing
            }
        }
        return _status;
    }
}

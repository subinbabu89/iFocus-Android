package com.example.dwayne.ifocus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;

/**
 * Created by Subin on 4/8/2016.
 */
public class iFocusDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ifocus.db";
    private static final int DATABASE_VERSION= 1;
    private final Context context;

    private iFocusDatabase healthDatabase;

    public interface Tables{
        String HEALTH = "health";
    }

    public iFocusDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ iFocusDatabase.Tables.HEALTH+" ("
                + HealthContract.HealthColumns.HEALTH_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HealthContract.HealthColumns.HEALTH_TYPE+" TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_DESCRIPTION+" TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_DATE+" TEXT NOT NULL,"
                + HealthContract.HealthColumns.HEALTH_COMPLETION+" INTEGER NOT NULL)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ HealthHistoryDatabase.Tables.HEALTH_HISTORY+" ("
                + HealthHistoryDatabase.Columns.HEALTH_ID+" INTEGER NOT NULL,"
                + HealthHistoryDatabase.Columns.HEALTH_COMPLETION_DATE+" TEXT NOT NULL,"
                + HealthHistoryDatabase.Columns.HEALTH_COMPLETION_STATUS+" INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version =oldVersion;
        if(version !=DATABASE_VERSION){
            db.execSQL("DROP TABLE IF EXISTS "+ iFocusDatabase.Tables.HEALTH);
            db.execSQL("DROP TABLE IF EXISTS "+ HealthHistoryDatabase.Tables.HEALTH_HISTORY);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}

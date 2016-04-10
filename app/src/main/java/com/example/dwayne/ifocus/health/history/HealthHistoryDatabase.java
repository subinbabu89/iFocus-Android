package com.example.dwayne.ifocus.health.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.db.iFocusDatabase;
import com.example.dwayne.ifocus.health.pojo.Health;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subin on 4/9/2016.
 */
public class HealthHistoryDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME = "ifocus.db";
    private final Context context;

    public interface Columns{
        String HEALTH_ID = BaseColumns._ID;
        String HEALTH_COMPLETION_DATE = "completion_date";
        String HEALTH_COMPLETION_STATUS = "completion_status";
    }


    public interface Tables{
        String HEALTH_HISTORY = "health_history";
    }

    public HealthHistoryDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE IF NOT EXISTS "+ Tables.HEALTH_HISTORY+" ("
                + Columns.HEALTH_ID+" INTEGER NOT NULL,"
                + Columns.HEALTH_COMPLETION_DATE+" TEXT NOT NULL,"
                + Columns.HEALTH_COMPLETION_STATUS+" INTEGER NOT NULL)";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ Tables.HEALTH_HISTORY);
            onCreate(db);
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public void insertHistoryRecord(HealthHistoryDao healthHistoryDao){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.HEALTH_ID,healthHistoryDao.get_id());
        contentValues.put(Columns.HEALTH_COMPLETION_DATE,healthHistoryDao.getCompletionDate());
        contentValues.put(Columns.HEALTH_COMPLETION_STATUS,healthHistoryDao.isCompletionStatus()?1:0);

        database.insert(Tables.HEALTH_HISTORY,null,contentValues);

    }

    public List<Health> retrieveHealthHistory(){
        String sql = "SELECT " + "p." + HealthContract.HealthColumns.HEALTH_ID +", " +
                "p." + HealthContract.HealthColumns.HEALTH_TYPE + ", " +
                "p." + HealthContract.HealthColumns.HEALTH_DESCRIPTION + ", " +
                "s." + Columns.HEALTH_COMPLETION_DATE + ", "+
                "s." + Columns.HEALTH_COMPLETION_STATUS +
                " FROM " + Tables.HEALTH_HISTORY + " s " +
                " JOIN " + iFocusDatabase.Tables.HEALTH + " p "+
                " WHERE p." + HealthContract.HealthColumns.HEALTH_ID +
                " = s." + Columns.HEALTH_ID;

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        List<Health> healths = new ArrayList<>();

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int _id = cursor.getInt(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_ID));
                    String type = cursor.getString(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_TYPE));
                    String description = cursor.getString(cursor.getColumnIndex(HealthContract.HealthColumns.HEALTH_DESCRIPTION));
                    String completionDate = cursor.getString(cursor.getColumnIndex(Columns.HEALTH_COMPLETION_DATE));
                    int completion = cursor.getInt(cursor.getColumnIndex(Columns.HEALTH_COMPLETION_STATUS));
                    boolean completionStatus = completion == 1 ?Boolean.TRUE : Boolean.FALSE;

                    Health health = new Health(type,description,completionDate,completionStatus);
                    health.setId(_id);
                    healths.add(health);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d("HEALTH","healths size "+healths.size());
        return healths;
    }

    public boolean isTaskMarked(int taskID){
        DateFormat df = new android.text.format.DateFormat();
        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

        String sql = "SELECT "+Columns.HEALTH_COMPLETION_DATE+" FROM "+ Tables.HEALTH_HISTORY + " WHERE "+Columns.HEALTH_ID+ " = "+taskID;

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor!=null){
          if(cursor.moveToFirst()){
              do{
                  String formattedDate = cursor.getString(cursor.getColumnIndex(Columns.HEALTH_COMPLETION_DATE));
                  if(formattedDate.equals(currentDate)){
                      return true;
                  }
              }while (cursor.moveToNext());
          }
        }
        return false;
    }
}

package com.example.dwayne.ifocus.study.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.dwayne.ifocus.db.iFocusDatabase;
import com.example.dwayne.ifocus.study.db.StudyContract;
import com.example.dwayne.ifocus.study.pojo.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subin on 4/9/2016.
 */
public class StudyHistoryDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME = "ifocus.db";
    private final Context context;

    public interface Columns{
        String STUDY_ID = BaseColumns._ID;
        String STUDY_COMPLETION_DATE = "completion_date";
        String STUDY_COMPLETION_STATUS = "completion_status";
    }


    public interface Tables{
        String STUDY_HISTORY = "study_history";
    }

    public StudyHistoryDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE IF NOT EXISTS "+ Tables.STUDY_HISTORY+" ("
                + Columns.STUDY_ID+" INTEGER NOT NULL,"
                + Columns.STUDY_COMPLETION_DATE+" TEXT NOT NULL,"
                + Columns.STUDY_COMPLETION_STATUS+" INTEGER NOT NULL)";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ Tables.STUDY_HISTORY);
            onCreate(db);
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }

    public void insertHistoryRecord(StudyHistoryDao studyHistoryDao){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.STUDY_ID,studyHistoryDao.get_id());
        contentValues.put(Columns.STUDY_COMPLETION_DATE,studyHistoryDao.getCompletionDate());
        contentValues.put(Columns.STUDY_COMPLETION_STATUS,studyHistoryDao.isCompletionStatus()?1:0);

        database.insert(Tables.STUDY_HISTORY,null,contentValues);

    }

    public List<Study> retrieveStudyHistory(){
        String sql = "SELECT " + "p." + StudyContract.StudyColumns.STUDY_ID +", " +
                "p." + StudyContract.StudyColumns.STUDY_TYPE + ", " +
                "p." + StudyContract.StudyColumns.STUDY_DESCRIPTION + ", " +
                "s." + Columns.STUDY_COMPLETION_DATE + ", "+
                "s." + Columns.STUDY_COMPLETION_STATUS +
                " FROM " + Tables.STUDY_HISTORY + " s " +
                " JOIN " + iFocusDatabase.Tables.STUDY + " p "+
                " WHERE p." + StudyContract.StudyColumns.STUDY_ID +
                " = s." + Columns.STUDY_ID;

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        List<Study> studys = new ArrayList<>();

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int _id = cursor.getInt(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_ID));
                    String type = cursor.getString(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_TYPE));
                    String description = cursor.getString(cursor.getColumnIndex(StudyContract.StudyColumns.STUDY_DESCRIPTION));
                    String completionDate = cursor.getString(cursor.getColumnIndex(Columns.STUDY_COMPLETION_DATE));
                    int completion = cursor.getInt(cursor.getColumnIndex(Columns.STUDY_COMPLETION_STATUS));
                    boolean completionStatus = completion == 1 ?Boolean.TRUE : Boolean.FALSE;

                    Study study = new Study(type,description,completionDate,completionStatus);
                    study.setId(_id);
                    studys.add(study);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d("STUDY","studys size "+studys.size());
        return studys;
    }

    public boolean isTaskMarked(int taskID){
        DateFormat df = new DateFormat();
        String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();

        String sql = "SELECT "+ Columns.STUDY_COMPLETION_DATE+" FROM "+ Tables.STUDY_HISTORY + " WHERE "+ Columns.STUDY_ID+ " = "+taskID;

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor!=null){
          if(cursor.moveToFirst()){
              do{
                  String formattedDate = cursor.getString(cursor.getColumnIndex(Columns.STUDY_COMPLETION_DATE));
                  if(formattedDate.equals(currentDate)){
                      return true;
                  }
              }while (cursor.moveToNext());
          }
        }
        return false;
    }
}

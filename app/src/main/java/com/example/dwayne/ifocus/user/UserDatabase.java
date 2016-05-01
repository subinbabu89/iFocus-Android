package com.example.dwayne.ifocus.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.dwayne.ifocus.db.iFocusDatabase;
import com.example.dwayne.ifocus.health.db.HealthContract;
import com.example.dwayne.ifocus.health.history.HealthHistoryDao;
import com.example.dwayne.ifocus.health.history.HealthHistoryDatabase;
import com.example.dwayne.ifocus.health.pojo.Health;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sneha on 4/29/2016.
 */
public class UserDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ifocus.db";
    private static final int DATABASE_VERSION= 1;

    private Context context;

    public interface UserColumns {
        String USER_ID = BaseColumns._ID;
        String USER_EMAIL_ID = "user_email_id";
        String USER_PASSWORD = "user_password";
        String NAME = "user_name";
        String SUPER_EMAIL_ID = "supervisor_email";
        String TYPE = "user_type";
        String SCORE = "user_score";
        String BUDGET = "budget";
    }

    public UserDatabase(Context context){
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        int version =oldVersion;
//        if (version != DATABASE_VERSION) {
//            db.execSQL("DROP TABLE IF EXISTS " + iFocusDatabase.Tables.USER);
//            onCreate(db);
//        }
    }

    public static final String PATH_USER = iFocusDatabase.Tables.USER;

    public void insertUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDatabase.UserColumns.USER_PASSWORD,user.getUserPassword());
        contentValues.put(UserDatabase.UserColumns.USER_EMAIL_ID,user.getUserEmailID());
        contentValues.put(UserDatabase.UserColumns.NAME,user.getUserName());
        contentValues.put(UserDatabase.UserColumns.SUPER_EMAIL_ID,user.getUserPartnerEmail());
        contentValues.put(UserDatabase.UserColumns.TYPE,user.getUserType());
        contentValues.put(UserDatabase.UserColumns.SCORE,user.getUserScore());
        contentValues.put(UserDatabase.UserColumns.BUDGET,user.getUserBudget());
        database.insert(PATH_USER, null, contentValues);

    }

    public void setScore(User user, int score){
        String updateSql = "UPDATE "+PATH_USER+" SET " +UserDatabase.UserColumns.SCORE + "="+score + " WHERE " + UserDatabase.UserColumns.USER_EMAIL_ID+" = '"+user.getUserEmailID()+"'";

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(updateSql);
    }

    public int getScore(User user){
        String sql = "SELECT "+UserDatabase.UserColumns.SCORE+" FROM "+ PATH_USER + " WHERE "+UserDatabase.UserColumns.USER_EMAIL_ID+ " = '"+user.getUserEmailID()+"'";
        int score = 0;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    score = cursor.getInt(cursor.getColumnIndex(UserColumns.SCORE));
                }while (cursor.moveToNext());
            }
        }
        return score;
    }

    public void setBudget(User user, int score){
        String updateSql = "UPDATE "+PATH_USER+" SET " + UserColumns.BUDGET + "="+score + " WHERE " + UserDatabase.UserColumns.USER_EMAIL_ID+" = '"+user.getUserEmailID()+"'";

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(updateSql);
    }

    public int getBudget(User user){
        String sql = "SELECT "+ UserColumns.BUDGET+" FROM "+ PATH_USER + " WHERE "+UserDatabase.UserColumns.USER_EMAIL_ID+ " = '"+user.getUserEmailID()+"'";
        int score = 0;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    score = cursor.getInt(cursor.getColumnIndex(UserColumns.BUDGET));
                }while (cursor.moveToNext());
            }
        }
        return score;
    }

    public boolean userPresent(User user){
        String sql = "SELECT "+ UserColumns.USER_ID+" FROM "+ PATH_USER + " WHERE "+UserDatabase.UserColumns.USER_EMAIL_ID+ " = '"+user.getUserEmailID()+"'";
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    return true;
                }while (cursor.moveToNext());
            }
        }
        return false;
    }

    public boolean getUser(String emailID){
        String sql = "SELECT " +  UserColumns.USER_PASSWORD +", " +
                 UserColumns.USER_EMAIL_ID + ", " +
                 UserColumns.NAME + ", " +
                 UserColumns.SUPER_EMAIL_ID + ", "+
                 UserColumns.SCORE + ", "+
                 UserColumns.BUDGET + ", "+
                 UserColumns.TYPE +
                " FROM " + PATH_USER +
                " WHERE " + UserColumns.USER_EMAIL_ID +
                " = '" + emailID+"'";

        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        List<Health> healths = new ArrayList<>();

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    User user = User.getInstance();
                    user.setUserBudget(cursor.getInt(cursor.getColumnIndex(UserColumns.BUDGET)));
                    user.setUserEmailID(cursor.getString(cursor.getColumnIndex(UserColumns.USER_EMAIL_ID)));
                    user.setUserName(cursor.getString(cursor.getColumnIndex(UserColumns.NAME)));
                    user.setUserPartnerEmail(cursor.getString(cursor.getColumnIndex(UserColumns.SUPER_EMAIL_ID)));
                    user.setUserScore(cursor.getInt(cursor.getColumnIndex(UserColumns.SCORE)));
                    user.setUserPassword(cursor.getString(cursor.getColumnIndex(UserColumns.USER_PASSWORD)));
                    user.setUserType(cursor.getString(cursor.getColumnIndex(UserColumns.TYPE)));
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        Log.d("HEALTH","healths size "+healths.size());
        return true;
    }

}

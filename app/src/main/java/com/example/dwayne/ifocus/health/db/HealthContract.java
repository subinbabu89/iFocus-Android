package com.example.dwayne.ifocus.health.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.dwayne.ifocus.db.iFocusDatabase;

import java.io.File;

/**
 * Created by Subin on 4/8/2016.
 */
public class HealthContract {

    public interface HealthColumns {
        String HEALTH_ID = BaseColumns._ID;
        String HEALTH_TYPE = "health_type";
        String HEALTH_DESCRIPTION = "health_description";
        String HEALTH_DATE = "health_date";
        String HEALTH_COMPLETION = "health_completion";
    }

    public static final String CONTENT_AUTHORITY = "com.example.dwayne.ifocus.health.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_HEALTH = iFocusDatabase.Tables.HEALTH;
    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + File.separator + PATH_HEALTH);

    public static class Health implements HealthColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_HEALTH).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".health";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".health";

        public static Uri buildHealthUri(String healthID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(healthID).build();
        }

        public static String getHealthId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}

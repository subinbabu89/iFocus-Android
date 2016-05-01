package com.example.dwayne.ifocus.study.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.dwayne.ifocus.db.iFocusDatabase;

import java.io.File;

/**
 * Created by Subin on 4/8/2016.
 */
public class StudyContract {

    public interface StudyColumns {
        String STUDY_ID = BaseColumns._ID;
        String STUDY_TYPE = "study_type";
        String STUDY_DESCRIPTION = "study_description";
        String STUDY_DATE = "study_date";
        String STUDY_COMPLETION = "study_completion";
    }

    public static final String CONTENT_AUTHORITY = "com.example.dwayne.ifocus.study.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STUDY = iFocusDatabase.Tables.STUDY;
    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + File.separator + PATH_STUDY);

    public static class Study implements StudyColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_STUDY).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".study";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".study";

        public static Uri buildStudyUri(String studyID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(studyID).build();
        }

        public static String getStudyId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}

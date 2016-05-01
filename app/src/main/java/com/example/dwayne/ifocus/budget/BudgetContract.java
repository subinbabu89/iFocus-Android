package com.example.dwayne.ifocus.budget;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.dwayne.ifocus.db.iFocusDatabase;

import java.io.File;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetContract {
    public interface BudgetColumns {
        String BUDGET_ID = BaseColumns._ID;
        String BUDGET_AMOUNT = "budget_amount";
        String BUDGET_DESCRIPTION = "budget_description";

    }

    public static final String CONTENT_AUTHORITY = "com.example.dwayne.ifocus.budget.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BUDGET = iFocusDatabase.Tables.BUDGET;
    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + File.separator + PATH_BUDGET);

    public static class Budget implements BudgetColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_BUDGET).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".budget";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".budget";

        public static Uri buildBudgetUri(String budgetID) {
            return CONTENT_URI.buildUpon().appendEncodedPath(budgetID).build();
        }

        public static String getBudgetId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}

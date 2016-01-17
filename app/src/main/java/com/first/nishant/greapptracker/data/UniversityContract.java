package com.first.nishant.greapptracker.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Nishant on 7/9/2015.
 */
public class UniversityContract {
    public static final String CONTENT_AUTHORITY = "com.first.nishant.greapptracker";
    static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);
    static final String PATH_UNIVERSITY="university";
    public static final class UniversityEntry implements BaseColumns {

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_UNIVERSITY).build();
        public static final String TABLE_NAME = "university";
        public static final String COL_UNIVERSITY_NAME = "university_name";
        public static final String COL_APPLICATION_COST = "app_cost";
        public static final String COL_APPLICATION_DEADLINE = "app_deadline";
        public static final String COL_FIELD = "app_field";
        public static final String COL_CHECK="checkbox_str";
        public static final String COL_COST_CHECK="checkbox_cost";

    }

}

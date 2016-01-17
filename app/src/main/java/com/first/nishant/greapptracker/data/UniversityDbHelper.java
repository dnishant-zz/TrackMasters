package com.first.nishant.greapptracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nishant on 7/9/2015.
 */
public class UniversityDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="university.db";

    public UniversityDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SQL="Create TABLE "+ UniversityContract.UniversityEntry.TABLE_NAME +
                " ("+ UniversityContract.UniversityEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                UniversityContract.UniversityEntry.COL_UNIVERSITY_NAME+" TEXT NOT NULL,"+
                UniversityContract.UniversityEntry.COL_CHECK+" TEXT NOT NULL DEFAULT '0000000000',"+
                UniversityContract.UniversityEntry.COL_COST_CHECK+" TEXT NOT NULL DEFAULT '111',"+
                UniversityContract.UniversityEntry.COL_APPLICATION_DEADLINE+" TEXT NOT NULL,"+
                UniversityContract.UniversityEntry.COL_APPLICATION_COST+" INTEGER NOT NULL,"+
                UniversityContract.UniversityEntry.COL_FIELD+" TEXT NOT NULL );";
        db.execSQL(CREATE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UniversityContract.UniversityEntry.TABLE_NAME);
        onCreate(db);
    }
}

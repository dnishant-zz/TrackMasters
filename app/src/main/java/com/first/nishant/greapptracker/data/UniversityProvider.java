package com.first.nishant.greapptracker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Nishant on 7/9/2015.
 */
public class UniversityProvider extends ContentProvider {
    UniversityDbHelper mOpenHelper;
    @Override
    public boolean onCreate() {
        mOpenHelper=new UniversityDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        retCursor=mOpenHelper.getReadableDatabase().query(UniversityContract.UniversityEntry.TABLE_NAME,
                projection,selection,selectionArgs,null,null,sortOrder);
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long _id=db.insert(UniversityContract.UniversityEntry.TABLE_NAME, null,values);
        if ( _id > 0 )
            returnUri = UniversityContract.UniversityEntry.buildWeatherUri(_id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        int row=db.delete(UniversityContract.UniversityEntry.TABLE_NAME,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        db.close();
        return row;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        int rows=db.update(UniversityContract.UniversityEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return rows;
    }

    public int bulkInsert(Uri uri, ContentValues[] values){
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(UniversityContract.UniversityEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnCount;
    }

    public void shutdown(){
        super.shutdown();
        mOpenHelper.close();
    }
}

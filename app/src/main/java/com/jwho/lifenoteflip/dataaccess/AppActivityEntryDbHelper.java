package com.jwho.lifenoteflip.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.AppActivityEntry.TABLE_CREATE_ENTRIES;
import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.AppActivityEntry.TABLE_DELETE_ENTRIES;

public class AppActivityEntryDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "AppActivityLog.db";

    public AppActivityEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_ENTRIES);
        db.execSQL(AppActivityContract.StatusPost.TABLE_CREATE_ENTRIES);
        db.execSQL(AppActivityContract.MediaResources.TABLE_CREATE_ENTRIES);
        db.execSQL(AppActivityContract.Notes.TABLE_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(TABLE_DELETE_ENTRIES);
        db.execSQL(AppActivityContract.StatusPost.TABLE_DELETE_ENTRIES);
        db.execSQL(AppActivityContract.MediaResources.TABLE_DELETE_ENTRIES);
        db.execSQL(AppActivityContract.Notes.TABLE_DELETE_ENTRIES);
        onCreate(db);
    }


}

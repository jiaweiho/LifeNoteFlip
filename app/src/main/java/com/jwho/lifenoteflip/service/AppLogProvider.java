package com.jwho.lifenoteflip.service;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.jwho.lifenoteflip.dataaccess.AppActivityEntryDbHelper;

public class AppLogProvider extends ContentProvider {
    public static final String AUTHORITY = "com.jwho.lifenoteflip.appactivities.contentprovider";//specific for our our app, will be specified in maninfed
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private AppActivityEntryDbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new AppActivityEntryDbHelper(getContext());
        db = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String table = getTableName(uri);
        long value = db.insert(table, null, contentValues);
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(value));
    }

    private String getTableName(Uri uri) {
        String value = uri.getPath();
        value = value.replace("/", "");//we need to remove '/'
        return value;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public AppActivityEntryDbHelper getDbHelper() {
        return dbHelper;
    }
}

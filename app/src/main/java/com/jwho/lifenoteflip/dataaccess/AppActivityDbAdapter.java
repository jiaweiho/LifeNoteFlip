package com.jwho.lifenoteflip.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jwho.lifenoteflip.service.Services;

import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.AppActivityEntry.*;
import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.MediaResources.COLUMN_ACTIVITY_ID;
import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.MediaResources.COLUMN_RESOURCE_ID;
import static com.jwho.lifenoteflip.dataaccess.AppActivityContract.Notes.COLUMN_NOTE_ID;

public class AppActivityDbAdapter {

    private AppActivityEntryDbHelper dbHelper;
    private SQLiteDatabase db;

    public AppActivityDbAdapter(Context ctx) {
        dbHelper = new AppActivityEntryDbHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insertAppActivity(Services appName, String status, String time) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_APP_NAME, appName.toString());
        values.put(COLUMN_CREATED_DATE, time);
        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public boolean insertResource(String resourceId, String activityId, String status) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE_ID, resourceId);
        values.put(COLUMN_ACTIVITY_ID, activityId);
        values.put(AppActivityContract.MediaResources.COLUMN_STATUS, status);
        return db.insert(AppActivityContract.MediaResources.TABLE_NAME, null, values) != -1;
    }

    public boolean insertNote(String content, String title) {
        ContentValues values = new ContentValues();
        values.put(AppActivityContract.Notes.COLUMN_APP_CONTENT, content);
        values.put(AppActivityContract.Notes.COLUMN_TITLE, title);
        return db.insert(AppActivityContract.Notes.TABLE_NAME, null, values) != -1;
    }

    public void updateNote(String noteId, String activityId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppActivityContract.Notes.COLUMN_ACTIVITY_ID, noteId);
        db.update(AppActivityContract.Notes.TABLE_NAME, contentValues,
                AppActivityContract.Notes.COLUMN_ACTIVITY_ID + " = ?", new String[]{activityId});
    }

    public Cursor read(String status, String appName) {
        //Cursor cursor = db.query(false, TABLE_NAME, new String[]{}, COLUMN_STATUS + "='" + status + "' and " + COLUMN_APP_NAME + "='" + appName + "'", null, null, null, null, null);
        Cursor cursor = db.query(false, TABLE_NAME, new String[]{COLUMN_STATUS}, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}

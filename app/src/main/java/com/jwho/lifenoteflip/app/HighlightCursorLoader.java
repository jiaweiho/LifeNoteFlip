package com.jwho.lifenoteflip.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.jwho.lifenoteflip.dataaccess.AppActivity;
import com.jwho.lifenoteflip.dataaccess.AppActivityDbAdapter;

import java.util.Collections;
import java.util.List;

public class HighlightCursorLoader extends AsyncTaskLoader<List<AppActivity>> {

    private AppActivityDbAdapter dbAdapter;

    public HighlightCursorLoader(Context context, AppActivityDbAdapter dbAdapter) {
        super(context);
        this.dbAdapter = dbAdapter;
    }

    @Override
    public List<AppActivity> loadInBackground() {
        List<AppActivity> apps = Collections.emptyList();
        Cursor cursor = dbAdapter.readAllAppActivities();
        if (cursor.moveToFirst()) {
            do {
                cursor.getColumnIndex()
            } while (cursor.moveToNext());
        }
        return apps;
    }
}

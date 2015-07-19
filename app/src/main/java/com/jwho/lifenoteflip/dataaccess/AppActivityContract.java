package com.jwho.lifenoteflip.dataaccess;

import android.provider.BaseColumns;

public final class AppActivityContract {

    public AppActivityContract() {}

    public abstract class StatusPost implements BaseColumns {
        public static final String TABLE_NAME = "status_posts";
        public static final String COLUMN_STATUS_ID = "status_id";
        public static final String COLUMN_POST = "post";
        public static final String COLUMN_CREATE_DATE = "create_date";
        public static final String COLUMN_UPDATE_DATE = "update_date";
        public static final String TABLE_CREATE_ENTRIES = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_POST + " TEXT," +
                COLUMN_CREATE_DATE + " DATETIME" +
                COLUMN_UPDATE_DATE + " DATETIME" +
                ")";
        public static final String TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public abstract class AppActivityEntry implements BaseColumns {
        public static final String TABLE_NAME = "app_activities";
        public static final String COLUMN_ENTRY_ID = "entry_id";
        public static final String COLUMN_STATUS_ID = "status_id";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_APP_NAME = "app_name";
        public static final String COLUMN_CREATED_DATE = "created_date";
        public static final String TABLE_CREATE_ENTRIES = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_STATUS_ID + " INTEGER," +
                COLUMN_STATUS + " TEXT," +
                COLUMN_APP_NAME + " TEXT," +
                COLUMN_CREATED_DATE + " DATETIME" +
                ")";
        public static final String TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public abstract class MediaResources implements BaseColumns {
        public static final String TABLE_NAME = "resources";
        public static final String COLUMN_RESOURCE_ID = "resourceId";
        public static final String COLUMN_ACTIVITY_ID = "activityId";
        public static final String COLUMN_STATUS = "status";
        public static final String TABLE_CREATE_ENTRIES = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_RESOURCE_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ACTIVITY_ID + " INTEGER," +
                COLUMN_STATUS + " TEXT" +
                ")";
        public static final String TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public abstract class Notes implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NOTE_ID = "noteId";
        public static final String COLUMN_ACTIVITY_ID = "activityId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_APP_CONTENT = "content";
        public static final String TABLE_CREATE_ENTRIES = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COLUMN_NOTE_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ACTIVITY_ID + " INTEGER," +
                COLUMN_APP_CONTENT + " TEXT" +
                ")";
        public static final String TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

package com.example.encryptionreference.database.log;

public class MyConstantsLog {
    public static final String TABLE_NAME = "error";
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String LOG = "log";
    public static final String DB_NAME = "course_project.db";
    public static final int DB_VERSION = 1;

    public static String[] projection = {_ID, DATE, LOG};

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
            DATE + " TEXT," +
            LOG + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}

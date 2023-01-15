package com.example.encryptionreference.database.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MyDbManagerLog {
    private Context context;
    private MyDbHelperLog myDbHelper;
    private SQLiteDatabase db;

    public MyDbManagerLog(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelperLog(context);
    }

    public void openDb() {
        db = myDbHelper.getWritableDatabase();
    }

    public void insertToDb(String date, String log) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstantsLog.DATE, date);
        cv.put(MyConstantsLog.LOG, log);
        db.insert(MyConstantsLog.TABLE_NAME, null, cv);
    }


    public List<String> getFromDb() {
        db = myDbHelper.getReadableDatabase();
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(MyConstantsLog.TABLE_NAME, MyConstantsLog.projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String date = cursor.getString((int) cursor.getColumnIndex(MyConstantsLog.DATE));
            String log = cursor.getString((int) cursor.getColumnIndex(MyConstantsLog.LOG));
            tempList.add("[Date]:" + date + ", [Log] " + log);
        }
        cursor.close();
        return tempList;
    }

    public String getAllLogFromString() {
        String temp = null;
        for (String log : getFromDb())
            temp += log + "\n";
        return temp;
    }


    public void closeDb() {
        myDbHelper.close();
    }

}

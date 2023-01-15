package com.example.encryptionreference.database.log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelperLog extends SQLiteOpenHelper {
    public MyDbHelperLog(@Nullable Context context) {
        super(context, MyConstantsLog.DB_NAME,null, MyConstantsLog.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MyConstantsLog.TABLE_STRUCTURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MyConstantsLog.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
package com.example.finalblogerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "bloger.db";
    public PostBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE table "+BlogerDbSchema.PostTable.NAME+ "(" +
                " p_id integer primary key autoincrement, " +
               BlogerDbSchema.PostTable.Cols.UUID + ", " +
                BlogerDbSchema.PostTable.Cols.TITLE + ", " +
                BlogerDbSchema.PostTable.Cols.DECS +
                BlogerDbSchema.PostTable.Cols.IMG +","+
                BlogerDbSchema.PostTable.Cols.USERID +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

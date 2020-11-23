package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AlbumListHelper extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "ALbumDB";
    public final static String TABLE_NAME1 = "Albums";
    public final static String TABLE_NAME2 = "Songs";
    protected final static int VERSION_NUM = 1;



    AlbumListHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

}
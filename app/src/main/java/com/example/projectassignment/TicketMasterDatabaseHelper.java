package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class TicketMasterDatabaseHelper extends SQLiteOpenHelper {

    protected final static int VERSION_NUM =2;
    protected final static String DATABASE_NAME = "TicketEventDataBase";

    public final static String TABLE_NAME = "Events";

    public final static String col1 = "ID";
    public final static String col2 = "Title";
    public final static String col3 = "Date";
    public final static String col4 = "MinPrice";
    public final static String col5 = "MaxPrice";
    public final static String col6 = "URL";
    public final static String col7 = "ImageURL";

    TicketMasterDatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //String for creating the Albums table
        String createTable;
        createTable = "CREATE TABLE " +TABLE_NAME+"(" + col1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2+" INTEGER , " +
                col3+" TEXT, " +
                col4+" INTEGER, " +
                col5+" TEXT, " +
                col6+" TEXT, " +
                col7+" TEXT)";


        //Executing both strings
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

}
package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class TicketMasterDatabaseHelper extends SQLiteOpenHelper {

    /**
     * creating constants variables for database info, name, rows, etc...
     */

    protected final static int VERSION_NUM = 4;
    protected final static String DATABASE_NAME = "TicketEventDataBase";

    public final static String TABLE_NAME = "Events";
    public final static String col1 = "ID";
    public final static String col2 = "Title";
    public final static String col3 = "Date";
    public final static String col4 = "MinPrice";
    public final static String col5 = "MaxPrice";
    public final static String col6 = "URL";
    public final static String col7 = "ImageURL";

    /**
     *  class to execute the database
     * @param context setting context of db
     */

    TicketMasterDatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,VERSION_NUM);
    }

    /**
     *  method for creating table
     * @param db pass db to parameter to create table
     */

    @Override
    public void onCreate(SQLiteDatabase db){
        //String for creating the event table
        String createTable;
        createTable = "CREATE TABLE " +TABLE_NAME+"(" + col1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2+" TEXT , " +
                col3+" TEXT, " +
                col4+" INTEGER, " +
                col5+" INTEGER, " +
                col6+" TEXT, " +
                col7+" TEXT)";


        db.execSQL(createTable);

    }

    /**
     * method to update the db to newer version
     * @param db pass selecting database to parameter
     * @param oldVersion pass the oldversion to  parameter for change
     * @param newVersion pass the newer verson to parameter for change
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    /**
     *    method revert db to older version
     * @param db pass selecting database to parameter to change
     * @param oldVersion pass the oldversion to parameter to change
     * @param newVersion pass the new version to parameter to change
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }



}
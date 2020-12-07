package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//This class is the database creation class for this application
public class AlbumListHelper extends SQLiteOpenHelper{
    //Initialization for all the database variables
    protected final static int VERSION_NUM = 2;
    protected final static String DATABASE_NAME = "ALbumDB";

    //Initialization for both tables variables used
    public final static String TABLE_NAME1 = "Albums";
    public final static String TABLE_NAME2 = "Songs";
    public final static String T1Col1 = "id";
    public final static String T1Col2 = "AlbumID";
    public final static String T1Col3 = "ArtistName";
    public final static String T1Col4 = "Year";
    public final static String T1Col5 = "Genre";
    public final static String T1Col6 = "Description";
    public final static String T1Col7 = "AlbumName";
    public final static String T2Col1 = "dSoID";
    public final static String T2Col2 = "AlbumID";
    public final static String T2Col3 = "SongName";


//DB constructor
    AlbumListHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //String for creating the Albums table
        String createTable1;
        createTable1 = "CREATE TABLE " +TABLE_NAME1+"(" + T1Col1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T1Col2+" INTEGER , " +
                T1Col3+" TEXT, " +
                T1Col4+" INTEGER, " +
                T1Col5+" TEXT, " +
                T1Col6+" TEXT, " +
                T1Col7+" TEXT)";

        //String for creating the Songs table
        String createTable2;
        createTable2 = "CREATE TABLE " +TABLE_NAME2+"("+T2Col1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T2Col2+" INTEGER, " +
                T2Col3+" TEXT)";

        //Executing both strings
        db.execSQL(createTable1);
        db.execSQL(createTable2);
    }

    //Method for on upgrade drops both tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db);
    }

    //method for on downgrade drops both tables
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db);
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME1);
        db.execSQL("delete from "+ TABLE_NAME2);
        db.close();
    }


}
package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * creates database for this {@code Covid} and application.
 */
public class CovidListHelper extends SQLiteOpenHelper {
    /**
     *Initialize for alldatabase variable
     */
    protected final static int VERSION_NUM = 5;
    protected final static String DATABASE_NAME = "CovidDB";
    /**
     * Initialize for table variables
     */
    public final static String TABLE_NAME1 = "CovidData";
    public final static String T1Column1 = "_id";
    public final static String T1Column2 = "CountryCode";
    public final static String T1Column3 = "CountryName";
    public final static String T1Column4 = "ProvinceName";
    public final static String T1Column5 = "StartDate";
    public final static String T1Column6 = "CovidCases";//covid cases

    /**
     * database constructor that accepts context, and builds database object.
     * @param context
     */
    CovidListHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        /**
         * String for creating the covid table
         */
        String createTable;
        createTable = "CREATE TABLE " +TABLE_NAME1+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T1Column2+" TEXT, " +
                T1Column3+" TEXT, " +
                T1Column4+" TEXT, " +
                T1Column5+" TEXT, " +
                T1Column6+" TEXT)";
        db.execSQL(createTable);
    }

    /**
     * method to update the database with new version.
     * @param db The database object
     * @param oldVersion VERSION_NUM from previous updated database
     * @param newVersion VERSION_NUM from current database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        onCreate(db);
    }

    /**
     * method to downgrade the database with older version
     * @param db The database object
     * @param oldVersion VERSION_NUM from current updated database
     * @param newVersion VERSION_NUM from previous updated database
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
    }
}

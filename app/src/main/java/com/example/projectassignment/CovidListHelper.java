package com.example.projectassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CovidListHelper extends SQLiteOpenHelper {//why 2 tables?
    protected final static int VERSION_NUM = 2;
    protected final static String DATABASE_NAME = "CovidDB";
    public final static String TABLE_NAME1 = "Covid";
    public final static String TABLE_NAME2 = "CovidData";
    public final static String T1Column1 = "id";
    public final static String T1Column2 = "CountryCode";
    public final static String T1Column3 = "CountryName";
    public final static String T1Column4 = "ProvinceName";
    public final static String T1Column5 = "StartDate";
    public final static String T1Column6 = "CovidCases";//covid cases
    public final static String T2Column1 = "IDcovid";
    public final static String T2Column2 = "StartDate";
    public final static String T2Column3 = "CntryNm";
    public final static String T2Column4 = "CovidNumber";

    CovidListHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable1;
        createTable1 = "CREATE TABLE " +TABLE_NAME1+"(" + T1Column1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T1Column2+" TEXT , " +
                T1Column3+" TEXT, " +
                T1Column3+" TEXT, " +
                T1Column4+" TEXT, " +
                T1Column5+" TEXT, " +
                T1Column6+" TEXT)";

        String createTable2;
        createTable2 ="CREATE TABLE " +TABLE_NAME2+"("+T2Column1+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                T2Column2+" INTEGER, " +
                T2Column3+" TEXT, " +
                T2Column4+" TEXT)";
        db.execSQL(createTable1);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        onCreate(db);
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME1);
        db.close();
    }
}

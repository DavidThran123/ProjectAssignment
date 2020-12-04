package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class TicketMasterDatabaseHelper extends SQLiteOpenHelper {

    protected final static int VERSION_NUM =2;
    protected final static String DATABASE_NAME = "TicketEventDataBase";

    public final static String TABLE_NAME = "Events";

    public final static String col1 = "Title";
    public final static String col2 = "Date";
    public final static String col3 = "MinPrice";
    public final static String col4 = "MaxPrice";
    public final static String col5 = "URL";
    public final static String col6 = "ImageURL";

    TicketMasterDatabaseHelper(Context context){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
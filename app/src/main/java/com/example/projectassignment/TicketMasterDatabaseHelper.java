package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TicketMasterDatabaseHelper extends AppCompatActivity {

    protected final static int VERSION_NUM =2;
    protected final static String DATABASE_NAME = "TicketEventDataBase";

    public final static String TABLE_NAME = "Events";

    public final static String col1 = "Event";
    public final static String col2 = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_master_database_helper);
    }
}
package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class TicketMasterDatabase extends AppCompatActivity {

    public ArrayList<Event> events= new ArrayList<>();
    public int position;

    private Button saveEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_master_database);
    }


}
package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class emptyFragmentEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_fragment_event);
        Bundle dataToPass = getIntent().getExtras();

        EventFragment dFragment = new EventFragment();
        dFragment.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emptyFragmentEvent, dFragment)
                .commit();
    }
    }

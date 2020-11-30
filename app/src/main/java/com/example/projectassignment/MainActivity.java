package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Button david = findViewById(R.id.davidsButton);
        david.setOnClickListener(v -> {
            Intent goToAudio = new Intent(MainActivity.this, .class);
            startActivity(goToAudio);

        });
        Button juhoon = findViewById(R.id.juhoonsButton);
        juhoon.setOnClickListener(v -> {
            Intent goToCovidCase = new Intent(MainActivity.this, .class);
            startActivity(goToCovidCase);

        });
        */

        Button nintendo = findViewById(R.id.nintendosButton);
        nintendo.setOnClickListener(v -> {
            Intent goToTicketMaster = new Intent(MainActivity.this, TicketMasterEventSearch.class);
            startActivity(goToTicketMaster);

        });
        /*
        Button matt = findViewById(R.id.mattsButton);
        matt.setOnClickListener(v -> {
            Intent goToRecipe = new Intent(MainActivity.this, .class);
            startActivity(goToRecipe);

        });
        */

    }
}
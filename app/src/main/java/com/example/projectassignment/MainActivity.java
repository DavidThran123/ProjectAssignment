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

        Button david = findViewById(R.id.davidsButton);
        david.setOnClickListener(v -> {
            Intent goToAudio = new Intent(MainActivity.this, AUDIO_DATABASE_API.class);
            startActivity(goToAudio);
        });

        Button juhoon = findViewById(R.id.juhoonsButton);
        juhoon.setOnClickListener((click) -> {
            Intent goToCovidCase = new Intent(MainActivity.this, COVID_19_CASE_DATA.class);
            startActivity(goToCovidCase);
        });

        


        Button nintendo = findViewById(R.id.nintendosButton);
        nintendo.setOnClickListener(v -> {
            Intent goToTicketMaster = new Intent(MainActivity.this, TicketMasterEventSearch.class);
            startActivity(goToTicketMaster);

        });


        //Matthew Chik's Search Recipe Activity
        Button searchRecipeBtn = findViewById(R.id.recipeSearchButton);
        searchRecipeBtn.setOnClickListener(v -> {

            Intent goToRecipe = new Intent(MainActivity.this, RecipeSearchActivity.class);


            startActivity(goToRecipe);


   
        });


    


    }

}
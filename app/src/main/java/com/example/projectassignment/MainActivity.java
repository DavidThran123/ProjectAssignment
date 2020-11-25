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

        //Matthew Chik's Search Recipe Activity
        Button searchRecipeBtn = findViewById(R.id.recipeSearchButton);
        searchRecipeBtn.setOnClickListener(v -> {
            Intent goToRecipe = new Intent(MainActivity.this, RecipeSearchActivity.class);
            startActivity(goToRecipe);

        });

    }
}
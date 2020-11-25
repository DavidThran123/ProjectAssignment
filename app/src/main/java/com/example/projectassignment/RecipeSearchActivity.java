package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RecipeSearchActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        Button searchBtn = findViewById(R.id.recipeSearchBtn);
        searchBtn.setOnClickListener( v -> dispatchRecipeListActivity() );



    }


    void dispatchRecipeListActivity()
    {
        Intent goToRecipeList = new Intent(RecipeSearchActivity.this,RecipeListActivity.class);
        startActivity(goToRecipeList);
    }




}
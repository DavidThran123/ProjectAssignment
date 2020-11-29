package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RecipeSearchActivity extends AppCompatActivity
{
    private SharedPreferences prefs = null;
    private final String savedRecipeKey = "savedRecipe";
    private final String savedIngredientsKey = "savedIngredients";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);


        prefs = getSharedPreferences("recipeSave", Context.MODE_PRIVATE);



        EditText recipeText = findViewById(R.id.recipe);
        EditText ingredientsText =  findViewById(R.id.ingredients);

        String savedRecipe = prefs.getString(savedRecipeKey,"");
        String savedIngredients = prefs.getString(savedIngredientsKey,"");

        recipeText.setText(savedRecipe);
        ingredientsText.setText(savedIngredients);

        Button searchBtn = findViewById(R.id.recipeSearchBtn);
        searchBtn.setOnClickListener
                (v ->
                {
                    saveString(savedRecipeKey,recipeText.getText().toString());
                    saveString(savedIngredientsKey,ingredientsText.getText().toString());
                    dispatchRecipeListActivity();
                });
        Button favouriteBtn = findViewById(R.id.favourite);
        favouriteBtn.setOnClickListener
                (v ->
                {
                    dispatchRecipeSavedActivity();
                });


    }


    private void dispatchRecipeListActivity()
    {
        Intent goToRecipeList = new Intent(RecipeSearchActivity.this,RecipeListActivity.class);

        EditText recipeText = findViewById(R.id.recipe);
        EditText ingredientsText =  findViewById(R.id.ingredients);

        goToRecipeList.putExtra("recipe",recipeText.getText().toString());
        goToRecipeList.putExtra("ingredients",ingredientsText.getText().toString());
        startActivity(goToRecipeList);
    }

    private void saveString(String key, String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, stringToSave);
        editor.commit();
    }


    private void dispatchRecipeSavedActivity()
    {
        Intent goToSavedActivity= new Intent(RecipeSearchActivity.this,RecipeSavedActivity.class);
        startActivity(goToSavedActivity);
    }

}
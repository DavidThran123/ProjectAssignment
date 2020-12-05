package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecipeSearchActivity extends AppCompatActivity
{
    private SharedPreferences prefs = null;
    private final String savedRecipeKey = "savedRecipe";
    private final String savedIngredientsKey = "savedIngredients";

    /**
     * This method is automatically invoked at the beginning
     * of this activity. It is the initialization method for buttons
     * textview, listeners, and other elements.
     *
     * @param savedInstanceState The bundle associated with this activity
     */
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
        Button helpBtn = findViewById(R.id.help);
        helpBtn.setOnClickListener(v->
                {
                    AlertDialog.Builder alertDialogBuilder;
                    alertDialogBuilder = new AlertDialog.Builder(this);
                    //getResources().getString(R.string.[string])
                    alertDialogBuilder.setTitle(getResources().getString(R.string.sayInstructions))
                            .setMessage(  getResources().getString(R.string.sayInstructionsRecipeSearch))
                            .setNeutralButton("Okay",(click,arg)->
                            {
                                //Do nothing
                            })
                            .setView(getLayoutInflater().inflate(R.layout.recipe_row_alert_layout,null))
                            .create().show();
                });

    }


    /**
     * Opens an activity that will show you a list of recipes according to
     * recipe title and the ingredients.
     */
    private void dispatchRecipeListActivity()
    {
        Intent goToRecipeList = new Intent(RecipeSearchActivity.this,RecipeListActivity.class);

        EditText recipeText = findViewById(R.id.recipe);
        EditText ingredientsText =  findViewById(R.id.ingredients);

        goToRecipeList.putExtra("recipe",recipeText.getText().toString());
        goToRecipeList.putExtra("ingredients",ingredientsText.getText().toString());
        startActivity(goToRecipeList);
    }


    /**
     * Saves a string to disk, which will be loaded the next time the user
     * opens the recipe search.
     *
     * @param key The first part of the map 'pair'. It is the identifier for a particular string.
     * @param stringToSave The string to be saved to local disk.
     */
    private void saveString(String key, String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, stringToSave);
        editor.commit();
    }


    /**
     * Opens an activity that will show the user their saved/favorite recipes.
     */
    private void dispatchRecipeSavedActivity()
    {
        Intent goToSavedActivity= new Intent(RecipeSearchActivity.this,RecipeSavedActivity.class);
        startActivity(goToSavedActivity);
    }

}
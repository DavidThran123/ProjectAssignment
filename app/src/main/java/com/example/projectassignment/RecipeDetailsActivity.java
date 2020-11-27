package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        //TODO: Remember to match this with the RecipeListActivity.java
        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
        //setDetailsToPass
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,detailsFragment)
                .commit();


    }
}
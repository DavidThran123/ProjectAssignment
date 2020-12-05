package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RecipeDetailsActivity extends AppCompatActivity {

    /**
     * This method is automatically invoked at the beginning
     * of this activity. It is the initialization method for buttons
     * textview, listeners, and other elements.
     *
     * @param savedInstanceState The bundle associated with this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        //TODO: Remember to match this with the RecipeListActivity.java
        Bundle dataToPass = getIntent().getExtras();
        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
        detailsFragment.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,detailsFragment)
                .commit();


    }
}
package com.example.projectassignment;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RecipeDetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    Bundle bundleFromActivity;

    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View newView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        bundleFromActivity = getArguments();
        String recipe = bundleFromActivity.getString("recipe");
        String website = bundleFromActivity.getString("website");
        String ingredients = bundleFromActivity.getString("ingredients");



        TextView recipeText = newView.findViewById(R.id.recipeTitle);
        recipeText.setText("Recipe:" + recipe);

        TextView ingredientsText = newView.findViewById(R.id.ingredients);
        ingredientsText.setText("Ingredients:" + ingredients);

        Button websiteButton = newView.findViewById(R.id.website);
        websiteButton.setText(website);
        websiteButton.setOnClickListener(
                c->
                {
                    dispatchWebsiteIntent(website);
                }
        );


        Button favouriteBtn = newView.findViewById(R.id.favourite);
        favouriteBtn.setOnClickListener(
                c->
                {
                    saveRecipe();
                }
        );



        // Inflate the layout for this fragment
        return newView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }

    private void saveRecipe()
    {

    }

    private void dispatchWebsiteIntent(String websiteName)
    {
        Uri uri = Uri.parse(websiteName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
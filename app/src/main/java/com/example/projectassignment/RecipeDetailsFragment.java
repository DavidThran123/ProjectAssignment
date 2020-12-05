package com.example.projectassignment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

public class RecipeDetailsFragment extends Fragment {

    private AppCompatActivity parentActivity;
    Bundle bundleFromActivity;

    SQLiteDatabase db;


    /**
     * This fragment will create a new view that will be inflated.
     * The view will display recipe information.
     * It will also display an option to 'favorite' a recipe
     * by saving it to the database.
     *
     * @param inflater The object that allows us to inflate a view in the layout
     * @param container The Viewgroup container
     * @param savedInstanceState The bundle associated with this fragment
     * @return A new view created for this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View newView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        bundleFromActivity = getArguments();
        String recipe = bundleFromActivity.getString("recipe");
        String website = bundleFromActivity.getString("website");
        String ingredients = bundleFromActivity.getString("ingredients");

        MyRecipeOpener dbHelper = new MyRecipeOpener(this.getContext());
        db = dbHelper.getReadableDatabase();

        TextView recipeText = newView.findViewById(R.id.recipeTitle);
        recipeText.setText(getResources().getString(R.string.sayRecipe) + recipe + "\n");

        TextView ingredientsText = newView.findViewById(R.id.ingredients);
        ingredientsText.setText(getResources().getString(R.string.sayIngredients)+"\n" + ingredients);

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
                    Cursor cursor = db.rawQuery("SELECT * FROM " + MyRecipeOpener.TABLE_NAME +  " WHERE " + MyRecipeOpener.COL_RECIPE + "  = ?", new String[]{recipe});
                    if (cursor.getCount() == 0)
                    {
                        Toast.makeText(this.getContext(),  getResources().getString(R.string.sayRecipeSaved), Toast.LENGTH_SHORT).show();
                        saveRecipe();
                    }
                    else
                    {
                        Toast.makeText(this.getContext(), getResources().getString(R.string.sayRecipeAlreadySaved) , Toast.LENGTH_SHORT).show();
                    }

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


    /**
     * Inserts a new recipe record into the SQLite database
     */
    private void saveRecipe()
    {
        //get the data
        String recipe = bundleFromActivity.getString("recipe");
        String website = bundleFromActivity.getString("website");
        String ingredients = bundleFromActivity.getString("ingredients");

        ContentValues newRowValues = new ContentValues();
        newRowValues.put(MyRecipeOpener.COL_RECIPE,recipe);
        newRowValues.put(MyRecipeOpener.COL_WEBSITE,website);
        newRowValues.put(MyRecipeOpener.COL_INGREDIENTS,ingredients);

        long newId = db.insert(MyRecipeOpener.TABLE_NAME,null,newRowValues);

    }


    /**
     * Opens a website intent which has instructions for a recipe.
     *
     * @param websiteName The website to be opened.
     */
    private void dispatchWebsiteIntent(String websiteName)
    {
        Uri uri = Uri.parse(websiteName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecipeSavedActivity extends AppCompatActivity {

    private SavedRecipesAdapter listAdapter;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    SQLiteDatabase db;

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
        setContentView(R.layout.activity_recipe_saved);

        MyRecipeOpener dbHelper = new MyRecipeOpener(this);
        db = dbHelper.getReadableDatabase();

        ListView listView = findViewById(R.id.recipeList);
        listView.setAdapter(listAdapter = new SavedRecipesAdapter());

        listView.setOnItemClickListener(
                (list,view,position,id)->
                {
                    String title = recipes.get(position).getTitle();
                    String website = recipes.get(position).getWebsite();
                    String ingredients = recipes.get(position).getIngredients();

                    AlertDialog.Builder alertDialogBuilder;
                    alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle( getResources().getString(R.string.saySavedRecipe))
                            .setMessage( getResources().getString(R.string.sayTitle) + title + "\n\n" +
                                    getResources().getString(R.string.sayIngredients) + ingredients + "\n\n" +
                                                 getResources().getString(R.string.sayWebsite) + website + "\n")
                            .setPositiveButton(getResources().getString(R.string.sayGoToWebsite),(click, arg) ->
                                    {
                                        dispatchWebsiteIntent(website);
                                    }
                            )
                            .setNeutralButton(getResources().getString(R.string.sayOkay),(click,arg)->
                            {
                                //Do nothing
                            })
                            .setNegativeButton( getResources().getString(R.string.sayDelete),(click, arg) ->
                                    {
                                        Snackbar.make(listView,   getResources().getString(R.string.sayRecipeDeleted) , Snackbar.LENGTH_SHORT).show();
                                        recipes.remove(position);
                                        db.delete(MyRecipeOpener.TABLE_NAME, MyRecipeOpener.COL_ID + "=?", new String[] {Long.toString(id)} );
                                        listAdapter.notifyDataSetChanged();
                                    }
                            )
                            .setView(getLayoutInflater().inflate(R.layout.recipe_row_alert_layout,null))
                            .create().show();
                }
        );
        Button helpBtn = findViewById(R.id.help);
        helpBtn.setOnClickListener(v->
        {
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.sayInstructions))
                    .setMessage( getResources().getString(R.string.sayInstructionsRecipeSaved))
                    .setNeutralButton(getResources().getString(R.string.sayOkay),(click,arg)->
                    {
                        //Do nothing
                    })
                    .setView(getLayoutInflater().inflate(R.layout.recipe_row_alert_layout,null))
                    .create().show();
        });

        loadRecipesFromDatabase();

    }

    public class SavedRecipesAdapter extends BaseAdapter
    {

        /**
         * Gets the size of the 'recipes' arraylist
         *
         * @return The amount of recipes in the list
         */
        @Override
        public int getCount()
        {
            return recipes.size();
        }

        /**
         * Retrieves a recipe from the list.
         *
         * @param position The position of the recipe that we want from the list.
         * @return A recipe from the list.
         */
        @Override
        public Object getItem(int position)
        {
            return recipes.get(position);
        }

        /**
         * Returns the database id of a recipe
         *
         * @param position The position of the recipe
         * @return The database id of the particular recipe object in the list
         */
        @Override
        public long getItemId(int position)
        {
            return ((Recipe)getItem(position)).getId() ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.row_recipe_layout,parent,false);

            TextView titleTextView = newView.findViewById(R.id.titleText);
            titleTextView.setText( ((Recipe)getItem(position)).getTitle()) ;

            return newView;
        }
    }

    /**
     * Load recipes into the list that are stored in the recipe database.
     */
    private void loadRecipesFromDatabase()
    {
        MyRecipeOpener dbHelper = new MyRecipeOpener(this);
        db = dbHelper.getReadableDatabase();

        String [] columns = {MyRecipeOpener.COL_ID, MyRecipeOpener.COL_RECIPE,MyRecipeOpener.COL_WEBSITE,MyRecipeOpener.COL_INGREDIENTS };

        Cursor results = db.query(false, MyRecipeOpener.TABLE_NAME, columns , null, null, null, null, null, null);


        int idColIndex = results.getColumnIndex(MyRecipeOpener.COL_ID);
        int recipeColIndex = results.getColumnIndex(MyRecipeOpener.COL_RECIPE);
        int websiteColIndex = results.getColumnIndex(MyRecipeOpener.COL_WEBSITE);
        int ingredientsColIndex = results.getColumnIndex(MyRecipeOpener.COL_INGREDIENTS);

        while (results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String recipe = results.getString(recipeColIndex);
            String website = results.getString(websiteColIndex);
            String ingredients = results.getString(ingredientsColIndex);

            recipes.add(new Recipe(recipe,website,ingredients,id)); //remember to add id
        }

        listAdapter.notifyDataSetChanged();

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
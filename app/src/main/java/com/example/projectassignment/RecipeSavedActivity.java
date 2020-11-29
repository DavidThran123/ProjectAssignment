package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeSavedActivity extends AppCompatActivity {

    private SavedRecipesAdapter listAdapter;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_saved);

        MyRecipeOpener dbHelper = new MyRecipeOpener(this);
        db = dbHelper.getReadableDatabase();

        ListView listView = findViewById(R.id.recipeList);
        listView.setAdapter(listAdapter = new SavedRecipesAdapter());

        loadMessagesFromDatabase();

    }

    public class SavedRecipesAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return recipes.size();
        }

        @Override
        public Object getItem(int position)
        {
            return recipes.get(position);
        }

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

    private void loadMessagesFromDatabase()
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



}
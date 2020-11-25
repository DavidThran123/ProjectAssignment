package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity
{
    private MyListAdapter listAdapter;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ListView listView = findViewById(R.id.recipeList);
        listView.setAdapter(listAdapter = new MyListAdapter());

        recipes.add(new Recipe("Recipe Title","Recipes.com","Onions, Food"));
        listAdapter.notifyDataSetChanged();

    }


    private class MyListAdapter extends BaseAdapter
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
            //TODO: set this to database id after
            /*return ((MessageText)getItem(position)).getId()*/ ;
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.row_recipe_layout,parent,false);

            TextView titleTextView = newView.findViewById(R.id.titleText);
            titleTextView.setText( ((Recipe)getItem(position)).getTitle()) ;

            //Don't need this information here
            /*
            TextView websiteTextView = newView.findViewById(R.id.websiteText);
            websiteTextView.setText( "Website: " + ((Recipe)getItem(position)).getWebsite());
            TextView ingredientsTextView = newView.findViewById(R.id.ingredientsText);
            ingredientsTextView.setText( "Ingredients: " + ((Recipe)getItem(position)).getIngredients());
            */

            return newView;
        }
    }

    private class Recipe
    {
        //Title
        private String title;
        //Website
        private String website;
        //Ingredients
        private String ingredients;

        Recipe(String title, String website, String ingredients)
        {
            setTitle(title);
            setWebsite(website);
            setIngredients(ingredients);
        }

        void setTitle(String title)
        {
            this.title = title;
        }

        void setWebsite(String website)
        {
            this.website = website;
        }

        void setIngredients(String ingredients)
        {
            this.ingredients = ingredients;
        }

        String getTitle()
        {
            return title;
        }

        String getWebsite()
        {
            return website;
        }

        String getIngredients()
        {
            return ingredients;
        }

    }
}
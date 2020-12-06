package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity
{
    ProgressBar progressBar;

    private MyListAdapter listAdapter;
    private ArrayList<Recipe> recipes = new ArrayList<>();

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
        setContentView(R.layout.activity_recipe_list);

        ListView listView = findViewById(R.id.recipeList);
        listView.setAdapter(listAdapter = new MyListAdapter());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String recipeStr = intent.getStringExtra("recipe");
        String ingredientsStr = intent.getStringExtra("ingredients");

        RecipeQuery query = new RecipeQuery();

        query.execute(ingredientsStr,recipeStr);

        boolean isTablet = findViewById(R.id.frameLayout) != null;

        Button helpBtn = findViewById(R.id.help);
        helpBtn.setOnClickListener(v->
        {
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(this);


            alertDialogBuilder.setTitle( getResources().getString(R.string.sayInstructions) )
                    .setMessage(  getResources().getString(R.string.sayInstructionsRecipeList))
                    .setNeutralButton(getResources().getString(R.string.sayOkay),(click,arg)->
                    {
                        //Do nothing
                    })
                    .setView(getLayoutInflater().inflate(R.layout.recipe_row_alert_layout,null))
                    .create().show();
        });

        listView.setOnItemClickListener((list,view,position,id)->
        {
            Bundle dataToPass = new Bundle();
            dataToPass.putString("recipe",recipes.get(position).getTitle());
            dataToPass.putString("website",recipes.get(position).getWebsite());
            dataToPass.putString("ingredients",recipes.get(position).getIngredients());

            if(isTablet)
            {


                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setArguments(dataToPass);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout,detailsFragment)
                        .commit();

            }
            else
            {
                Intent nextActivity = new Intent(RecipeListActivity.this,RecipeDetailsActivity.class);
                nextActivity.putExtras(dataToPass);
                startActivity(nextActivity);
            }
        });

    }


    public class MyListAdapter extends BaseAdapter
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
            //set this to database id after
            return ((Recipe)getItem(position)).getId();
            //return position;
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



    //It wants recipe, then ingredients, then format
    class RecipeQuery extends AsyncTask<String, Integer,String>
    {
        String title = "";
        String website = "";
        String ingredients = "";
        private ArrayList<Recipe> tempRecipes = new ArrayList<>();

        /**
         * Connects to an online database and loads the recipe information to an array list.
         * The website has arguments for recipe title and ingredients required.
         *
         * @param args The URL that allows us to connect to the online recipe database
         * @return
         */
        @Override
        protected String doInBackground(String... args)
        {
            try
            {
                URL url = new URL("http://www.recipepuppy.com/api/?i="+ args[0] +"&q="+ args[1] +"&p=3&format=xml");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT)
                {


                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equals("title"))
                        {
                            title = xpp.nextText();
                            publishProgress(25);
                        }
                        else if (xpp.getName().equals("href"))
                        {
                            website = xpp.nextText();
                            publishProgress(50);
                        }
                        else if (xpp.getName().equals("ingredients"))
                        {
                            ingredients = xpp.nextText();
                            publishProgress(75);
                        }

                        if (!title.equals("") && !website.equals("") && !ingredients.equals("")) //if we have a full recipe object
                        {
                            recipes.add(new Recipe(title,website,ingredients));
                            title = "";
                            website = "";
                            ingredients = "";

                        }

                    }
                    eventType = xpp.next();

                }


            }
            catch (Exception e)
            {

            }

            //recipes.add(new Recipe("Recipe Title","Recipes.com","Onions, Food"));


            return "Done";
        }

        /**
         * Updates the progress bar.
         *
         * @param values Represents how complete the background task is.
         */
        @Override
        protected void onProgressUpdate(Integer ... values)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        /**
         * When the background task is done, then we will update the recipe list.
         * We will also stop showing the progress bar.
         *
         * @param fromDoInBackground gets the returned value from the doInBackground()
         */
        @Override
        public void onPostExecute(String fromDoInBackground)
        {
            //recipes = tempRecipes;
            listAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

}
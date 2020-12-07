package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TicketMasterEventSearch extends AppCompatActivity {

    /**
     *declare variables
     */

    private static final String API_KEY = "RINP3tjoIuw2xX9fusWR4iOAVdOtZzvj";//Key for website
    //Elements within the ui
    EditText city, radius; //Both edit texts
    Button searchButton , loadEventButton; //Both buttons
    ListView events;//The list of events
    ProgressBar pbar; //Progress bar

    //In class variables
    String cityString, radiusString;
    //List of events Arraylist
    ArrayList<Event> eventAR = new ArrayList<>();
    //Adapter for listview
    EventAdapter adapter = new EventAdapter();

    public SQLiteDatabase dbSQL; //Sql/database connection

    //Bundle constants
    public static final String EVENTNAME = "EVENTNAME";
    public static final String DATE = "DATE";
    public static final String MIN = "MIN";
    public static final String MAX = "MAX";
    public static final String URL = "URL";
    public static final String IMAGEURL = "IMAGEURL";

    //Initialize the shared preference variables
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    /**
     * method to execute the ticket event master search function and load the ticket master event search layout
     * to perform desired result
     * @param savedInstanceState reference to bundle project that is passed to this parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_master_event_search);

        //connecting UI objects to their sources
        city = findViewById(R.id.searchCity);
        radius = findViewById(R.id.searchRadius);
        loadEventButton = findViewById(R.id.SavedSearch);
        searchButton = findViewById(R.id.searchButtonDing);

        //Setting listview source and applying the adapter
        events = findViewById(R.id.listViewResults);
        events.setAdapter(adapter);

        //Progress bar
        pbar = findViewById(R.id.progBar);
        pbar.setVisibility(View.VISIBLE);

        //Collecting on pause data, and setting both edit texts
        sp = this.getPreferences(Context.MODE_PRIVATE);
        if (sp.getString("cityInput", "")!=null) {
            city.setText(sp.getString("cityInput", ""));
        }
        if(sp.getString("radiusInput", "")!=null){
            radius.setText(sp.getString("radiusInput", ""));
        }


        //Setting on click listener for search button
        searchButton.setOnClickListener( clk ->{
            //Get string variables
            cityString = city.getText().toString();
            radiusString = radius.getText().toString();
            eventAR.clear();
            events.removeAllViewsInLayout();
            //make string URL
            String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="+API_KEY+"&city="+cityString+"&radius="+radiusString;
            //Make Event Do in background object/Async task
            EventQuery aQ = new EventQuery();
            //Send the url for parsing
            aQ.execute(url);
        });

        //Launch intent to new activity on click
        loadEventButton.setOnClickListener(clk ->{
            Intent savedEvents = new Intent(TicketMasterEventSearch.this, SavedEvents.class);
            startActivity(savedEvents);
        });

        //Display Alert dialog with help details on click
        Button helpButton = findViewById(R.id.helpButtonDing);
        helpButton.setOnClickListener(v->
        {
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Instructions:")
                    .setMessage( "Enter the City Name in the first search textbox " + "\n" +
                            "Enter the Search Radius in the second search textbox" +  "\n" +
                            "Click Search for result" + "\n"+
                            "After Search completed" + "\n"+
                            "Click View button for event details"+ "\n"+
                            "Click Save button to save events to favourite event"+ "\n"+
                            "Check the saved events by clicking Favourite Event button")
                    .setNeutralButton("Okay",(click,arg)->
                    {

                    })
                    .setView(getLayoutInflater().inflate(R.layout.ticketmaster_alert_layout,null))
                    .create().show();
        });

    }

    /**
     *On pause save the edit text contents with shared preference
     */
    protected void onPause() {
        super.onPause();
        editor = sp.edit();
        editor.putString("cityInput", city.getText().toString());
        editor.putString("radiusInput", radius.getText().toString());
        editor.apply();
    }

    /**
     * This class is used for sorting through JSON details in sent url, and will handle error
     */
    private class EventQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            try {

                setProgress(10);

                //Initialize Connection
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                setProgress(20);

                //Make buffered reader to grab the information
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                setProgress(30);

                //Loop reading each line in HTTP page untill end
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                setProgress(40);

                //Store the results into a string variable
                String albumSearch = sb.toString();

                setProgress(50);

                //Make a JSONObject to store the results
                JSONObject object = new JSONObject(albumSearch);

                setProgress(60);

                JSONObject first = object.getJSONObject("_embedded");

                setProgress(70);

                //So checks if JSON object recieved is not null
                if(first!=null) {
                    //Grap events array within the object
                        JSONArray jsonArray = first.getJSONArray("events");
                        //Loop through JSONArray
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Make a temp Object for every Object found in JSONArray
                            JSONObject singleJO = (JSONObject) jsonArray.get(i);

                            String nameOfEvent = singleJO.getString("name");
                            String urlTicket = singleJO.getString("url");

                            //get object within singleJO "dates"
                            JSONObject dateJO = (JSONObject) singleJO.getJSONObject("dates");
                            //grab start Object from the dateJO
                            JSONObject startJO = (JSONObject) dateJO.getJSONObject("start");
                            //finally grab the local date from startJO and put into string dateStart
                            String dateStart = (String) startJO.getString("localDate");

                            //Get priceRanges array within the singleJO
                            JSONArray priceJO = (JSONArray) singleJO.getJSONArray("priceRanges");
                            //get object within the priceJO array
                            JSONObject price = (JSONObject)priceJO.get(0);
                            //Grab both min and max from the price Object
                            int minPrice = price.getInt("min");
                            int maxPrice = price.getInt("max");

                            //Grap Json array from singleJO
                            JSONArray images = singleJO.getJSONArray("images");
                            //Grab object at first position from images array
                            JSONObject image = (JSONObject)images.get(0);
                            //put url of image within the imageURL from image object
                            String imageUrl = image.getString("url");


                            //Finally add all collected variables to new Event and add to eventAR/ArrayList<Event>
                            eventAR.add(new Event(nameOfEvent, dateStart, minPrice, maxPrice, urlTicket, new Image(imageUrl)));

                    }
                    setProgress(80);

                }

            } catch(JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            e.printStackTrace();
        }
            setProgress(90);

            return "Done";
        }

        /**
         * method for updating the progress bar
         * @param value
         */
        public void onProgressUpdate(Integer... value) {
            pbar.setVisibility(View.VISIBLE);
            pbar.setProgress(value[0]);
        }

        /**
         * Once do in backGround is "Done" set progress bar to invisible and notify set data cahnge
         * @param fromDoInBackground
         */
        public void onPostExecute(String fromDoInBackground) {
            setProgress(100);
            pbar.setVisibility(View.INVISIBLE);

            //Populate listview with results
            adapter.notifyDataSetChanged();

        }
    }

    /**
     * This method is an adapter to inflate the ListView with another row, it is used for
     *  adding the searched events as seperate entities among the listview
     */

    private class EventAdapter extends BaseAdapter {

        @Override // number of items in the list
        public int getCount() {
            return  eventAR.size();
        }

        /**
         * determines what object goes at row i
         * @param i
         * @return
         */
        @Override
        public Object getItem(int i) {
            return eventAR.get(i);
        }

        /**
         *  determines database id of item at row i
         * @param i
         * @return
         */
        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * method to create display view
         * @param i
         * @param old
         * @param parent
         * @return
         */
        @Override
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = inflater.inflate(R.layout.activity_event_layout, parent, false);
            TextView textEvent = newRow.findViewById(R.id.singleEvent);
            TextView textDate = newRow.findViewById(R.id.date);
            Button save = newRow.findViewById(R.id.save);
            Button view = newRow.findViewById(R.id.view);
            Event thisEvent = (Event) getItem(i);
            textEvent.setText(thisEvent.getEventName());
            textDate.setText(thisEvent.getDateOfEvent());

            //set click to save data to a bundle to display view in DetailActvity
            view.setOnClickListener(clk -> {
                            Bundle eventData = new Bundle();

                            String EventName = thisEvent.getEventName();
                            String dateOfEvent = thisEvent.getDateOfEvent();
                            int min = thisEvent.getMin();
                            int max = thisEvent.getMax();
                            String url = thisEvent.getUrl();
                            Image image = thisEvent.getImage();
                            String imageURL = thisEvent.getImage().getURL();

                            eventData.putString("EVENTNAME", EventName);
                            eventData.putString("DATE", dateOfEvent);
                            eventData.putInt("MIN", min);
                            eventData.putInt("MAX", max);
                            eventData.putString("URL", url);
                            eventData.putString("IMAGEURL", imageURL);
                            Intent viewEventDetails = new Intent(TicketMasterEventSearch.this, DetailActivity.class);

                            viewEventDetails.putExtras(eventData);

                            startActivity(viewEventDetails);

            });
            //set click to save the event to database
            save.setOnClickListener(clk -> {
                            TicketMasterDatabaseHelper db = new TicketMasterDatabaseHelper(getApplicationContext());
                            dbSQL = db.getReadableDatabase();
                            ContentValues newRowValues = new ContentValues();
                            newRowValues.put(TicketMasterDatabaseHelper.col2, thisEvent.getEventName());
                            newRowValues.put(TicketMasterDatabaseHelper.col3, thisEvent.getDateOfEvent());
                            newRowValues.put(TicketMasterDatabaseHelper.col4, thisEvent.getMin());
                            newRowValues.put(TicketMasterDatabaseHelper.col5, thisEvent.getMax());
                            newRowValues.put(TicketMasterDatabaseHelper.col6, thisEvent.getUrl());
                            newRowValues.put(TicketMasterDatabaseHelper.col7, thisEvent.getImage().getURL());
                            long newIDofEvent = dbSQL.insert(TicketMasterDatabaseHelper.TABLE_NAME, null, newRowValues);

                            String EventName = thisEvent.getEventName();
                            Snackbar snackbar = Snackbar
                            .make(clk, EventName+" is saved ", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        });
            //return view
            return newRow;
        }
    }
}
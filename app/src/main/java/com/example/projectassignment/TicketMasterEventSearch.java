package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

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

    private static final String API_KEY = "RINP3tjoIuw2xX9fusWR4iOAVdOtZzvj";
    EditText city, radius;
    Button searchButton , loadEventButton;
    ListView events;
    String cityString, radiusString;
    ArrayList<Event> eventAR = new ArrayList<>();
    EventAdapter adapter = new EventAdapter();
    ProgressBar pbar;
    boolean finishedBackgroundtask;
    public SQLiteDatabase dbSQL;

    //Bundle constants
    public static final String EVENTNAME = "EVENTNAME";
    public static final String DATE = "DATE";
    public static final String MIN = "MIN";
    public static final String MAX = "MAX";
    public static final String URL = "URL";
    public static final String IMAGEURL = "IMAGEURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_master_event_search);
        city = findViewById(R.id.searchCity);
        radius = findViewById(R.id.searchRadius);
        loadEventButton = findViewById(R.id.SavedSearch);
        searchButton = findViewById(R.id.searchButtonDing);
        events = findViewById(R.id.listViewResults);
        events.setAdapter(adapter);
        pbar = findViewById(R.id.progBar);

        searchButton.setOnClickListener( clk ->{
            cityString = city.getText().toString();
            radiusString = radius.getText().toString();
            eventAR.clear();
            events.removeAllViewsInLayout();
            String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="+API_KEY+"&city="+cityString+"&radius="+radiusString;
            EventQuery aQ = new EventQuery();
            aQ.execute(url);
        });

        loadEventButton.setOnClickListener(clk ->{
            Intent savedEvents = new Intent(TicketMasterEventSearch.this, SavedEvents.class);
            startActivity(savedEvents);
        });

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
                            "Click view button for event details"+ "\n"+
                            "Click save button to save events"+ "\n"+
                            "Checkout the saved events by clicking saved event button")
                    .setNeutralButton("Okay",(click,arg)->
                    {

                    })
                    .setView(getLayoutInflater().inflate(R.layout.ticketmaster_alert_layout,null))
                    .create().show();
        });


    }



    private class EventQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                //Initialize Connection
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Make buffered reader to grab the information
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                //Store the results into a string variable
                String albumSearch = sb.toString();

                //Make a JSONObject to store the results
                JSONObject object = new JSONObject(albumSearch);

                //Set progress to 0
                setProgress(0);
                JSONObject first = object.getJSONObject("_embedded");
                //Checks to see if the object has any search results
                if(first!=null) {
                        JSONArray jsonArray = first.getJSONArray("events");
                        //Loop through JSONArray
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //Make a temp Object for every Object found in JSONArray
                            JSONObject singleJO = (JSONObject) jsonArray.get(i);
                            String nameOfEvent = singleJO.getString("name");
                            String urlTicket = singleJO.getString("url");

                            JSONObject dateJO = (JSONObject) singleJO.getJSONObject("dates");
                            JSONObject startJO = (JSONObject) dateJO.getJSONObject("start");
                            String dateStart = (String) startJO.getString("localDate");

                            JSONArray priceJO = (JSONArray) singleJO.getJSONArray("priceRanges");
                            JSONObject price = (JSONObject)priceJO.get(0);
                            int minPrice = price.getInt("min");
                            int maxPrice = price.getInt("max");
                            //String info = singleJO.getString("info");

                            JSONArray images = singleJO.getJSONArray("images");
                            JSONObject image = (JSONObject)images.get(0);
                            String imageUrl = image.getString("url");

                            eventAR.add(new Event(nameOfEvent, dateStart, minPrice, maxPrice, urlTicket, new Image(imageUrl)));
                            setProgress((int) ((i + 1) / jsonArray.length() * 100));
                    }
                    }


            } catch(JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            e.printStackTrace();
        }
            return "Done";
        }

        //Updates the progress bar
        public void onProgressUpdate(Integer... value) {
            pbar.setVisibility(View.VISIBLE);
            pbar.setProgress(value[0]);
        }

        //Updates listView with found results and shows a toast button
        public void onPostExecute(String fromDoInBackground) {
            pbar.setVisibility(View.INVISIBLE);
            //Populate listview with results
            adapter.notifyDataSetChanged();

        }
    }




    //This is an adapter to inflate the ListView with another row, it is used for
    //adding the searched events as seperate entities among the listview
    private class EventAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  eventAR.size();
        }

        @Override // what object goes at row i
        public Object getItem(int i) {
            return eventAR.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return i;
        }


        @Override //controls which widgets are on the row
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = inflater.inflate(R.layout.dingeventlayout, parent, false);
            TextView textEvent = newRow.findViewById(R.id.singleEvent);
            TextView textDate = newRow.findViewById(R.id.date);
            Button save = newRow.findViewById(R.id.save);
            Button view = newRow.findViewById(R.id.view);
            Event thisEvent = (Event) getItem(i);
            textEvent.setText(thisEvent.getEventName());
            textDate.setText(thisEvent.getDateOfEvent());

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
                            Toast.makeText(clk.getContext(), "Event id = "+newIDofEvent, Toast.LENGTH_SHORT).show();
                        });

            return newRow;
        }
    }


}
package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedEvents extends AppCompatActivity {

    /**
     *
     */
    // declare textview, listview
    TextView EventNames;
    ListView EventNamesLV;

    //List of events Arraylist
    ArrayList<Event> events = new ArrayList<>();

    //adapter
    EventAdapter eventsAdapter = new EventAdapter();

    //List of saved events Arraylist
    private ArrayList <Event> savedEventList = new ArrayList<>();
    public SQLiteDatabase saveAssist;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        Button savedEventHelpButton = findViewById(R.id.savedEventHelpButtonDing);
        savedEventHelpButton.setOnClickListener(v->
        {
            AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Instructions:")
                    .setMessage( "Instructions:"+"\n"+
                            "Click View button for event details"+ "\n"+
                            "Click delete button to delete saved events"+ "\n"
                            )
                    .setNeutralButton("Okay",(click,arg)->
                    {

                    })
                    .setView(getLayoutInflater().inflate(R.layout.ticketmaster_alert_layout,null))
                    .create().show();
        });

        //load database
        loadEventsFromDatabase();

        //set both views by associated ID
        EventNames = (TextView) findViewById(R.id.EventsSavedTitle);
        EventNamesLV = (ListView) findViewById(R.id.EventsSavedListView);

        //set adapter
        EventNamesLV.setAdapter(eventsAdapter);

        eventsAdapter.notifyDataSetChanged();

    }

    /**
     * method for retrieving data from saved events in db
     */

    private void loadEventsFromDatabase()
    {
        TicketMasterDatabaseHelper db = new TicketMasterDatabaseHelper(this);
        saveAssist = db.getWritableDatabase();

        String [] columns = {db.col1,db.col2, db.col3, db.col4, db.col5, db.col6, db.col7};

        Cursor returnedEvents = saveAssist.query(false, db.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColumn = returnedEvents.getColumnIndex(db.col1);
        int titleColumn = returnedEvents.getColumnIndex(db.col2);
        int dateColumn = returnedEvents.getColumnIndex(db.col3);
        int minColumn = returnedEvents.getColumnIndex(db.col4);
        int maxColumn = returnedEvents.getColumnIndex(db.col5);
        int ticketColumn = returnedEvents.getColumnIndex(db.col6);
        int imageColumn = returnedEvents.getColumnIndex(db.col7);

        while(returnedEvents.moveToNext())
        {
            int id = returnedEvents.getInt(idColumn);
            String nameOfEvent = returnedEvents.getString(titleColumn);
            String urlTicket = returnedEvents.getString(ticketColumn);
            String dateStart = returnedEvents.getString(dateColumn);
            int minPrice = returnedEvents.getInt(minColumn);
            int maxPrice = returnedEvents.getInt(maxColumn);
            String imageUrl = returnedEvents.getString(imageColumn);
            savedEventList.add(new Event(nameOfEvent, dateStart, minPrice, maxPrice, urlTicket, new Image(imageUrl), id));
        }
    }


    /**
     *  an adapter class to inflate the ListView with another row, it is used for
     *      *  adding the searched events as seperate entities among the listview
     */
    private class EventAdapter extends BaseAdapter {

        /**
         * method for get number of items in the list
         * @return number of items
         */

        @Override
        public int getCount() {
            return  savedEventList.size();
        }

        /**
         * method for determines the object that goes at row i
         * @param i int value passed to parameter
         * @return get the value of i
         */
        @Override
        public Object getItem(int i) {
            return savedEventList.get(i);
        }

        /**
         * method for get the id of item at row i
         * @param i int value passed to parameter
         * @return event ID
         */
        @Override
        public long getItemId(int i) {
            return savedEventList.get(i).EventID;
        }


        /**
         * method to create view for saved event including set click button for viewing and deleting the saved events
         * @param i
         * @param old
         * @param parent
         * @return the new row view
         */
        @Override
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = inflater.inflate(R.layout.activity_saved_event_layout, parent, false);
            TextView textEvent = newRow.findViewById(R.id.singleEvent);
            TextView textDate = newRow.findViewById(R.id.date);
            Button delete = newRow.findViewById(R.id.delete);
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
                Intent viewEventDetails = new Intent(SavedEvents.this, DetailActivity.class);

                viewEventDetails.putExtras(eventData);

                startActivity(viewEventDetails);

            });

            delete.setOnClickListener(clk -> {
                TicketMasterDatabaseHelper db = new TicketMasterDatabaseHelper(getApplicationContext());
                saveAssist = db.getReadableDatabase();

                boolean isDeleted = saveAssist.delete(TicketMasterDatabaseHelper.TABLE_NAME, TicketMasterDatabaseHelper.col1 + "=" + thisEvent.getEventID(), null) == 0;
                if(isDeleted){
                    Toast.makeText(clk.getContext(), "Event Deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


            return newRow;
        }
    }

}
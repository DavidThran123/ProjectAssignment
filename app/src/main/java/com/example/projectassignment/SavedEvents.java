package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SavedEvents extends AppCompatActivity {
    TextView EventNames;
    ListView EventNamesLV;
    ArrayList<Event> events = new ArrayList<>();
    EventAdapter eventsAdapter = new EventAdapter();
    private ArrayList <Event> savedEventList = new ArrayList<>();
    public SQLiteDatabase saveAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        loadEventsFromDatabase();

        EventNames = (TextView) findViewById(R.id.EventsSavedTitle);
        EventNamesLV = (ListView) findViewById(R.id.EventsSavedListView);
        EventNamesLV.setAdapter(eventsAdapter);

        eventsAdapter.notifyDataSetChanged();

    }


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


    private class EventAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  savedEventList.size();
        }

        @Override // what object goes at row i
        public Object getItem(int i) {
            return savedEventList.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return savedEventList.get(i).EventID;
        }


        @Override //controls which widgets are on the row
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newRow = inflater.inflate(R.layout.dingeventlayout, parent, false);
            TextView textEvent = newRow.findViewById(R.id.singleEvent);
            TextView textDate = newRow.findViewById(R.id.date);
            Button delete = newRow.findViewById(R.id.save);
            delete.setText("Delete");
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
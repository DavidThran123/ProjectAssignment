package com.example.projectassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SavedEvents extends AppCompatActivity {

    TextView EventNames;
    ListView EventNamesLV;

    private ArrayList <Event> savedEventList = new ArrayList<>();

    public SQLiteDatabase saveAssist;

    Bundle dataInfoPassDing;

    public static final String EVENT_ID = "EventID";
    public static final String EVENT_NAME = "EventName";
    public static final String EVENT_DATE = "EventDate";
    public static final String EVENT_MIN_PRICE = "MinPrice";
    public static final String EVENT_MAX_PRICE = "MaxPrice";
    public static final String EVENT_URL = "URL";
    public static final String EVENT_IMAGE_URL = "ImageURL";

    public EventSavedListAdapter saveAdapt = new EventSavedListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        loadEventsFromDatabse();

        EventNames = (TextView) findViewById(R.id.AlbumsSavedTitle);
        EventNamesLV = (ListView) findViewById(R.id.listedSavedAlbums);

        EventNamesLV.setOnItemClickListener((list,view,position,id) -> {



            AlertDialog.Builder alertDialogBUilder = new AlertDialog.Builder(this);
            dataInfoPassDing = new Bundle();
            dataInfoPassDing.putString(EVENT_NAME, savedEventList.get(position).getEventName() );
            dataInfoPassDing.putString(EVENT_DATE,savedEventList.get(position).getDateOfEvent());
            dataInfoPassDing.putString(EVENT_MIN_PRICE, savedEventList.get(position).getMin());
            dataInfoPassDing.putString(EVENT_MAX_PRICE, savedEventList.get(position).getMax());
            dataInfoPassDing.putString(EVENT_IMAGE_URL, savedEventList.get(position).getUrl());
            dataInfoPassDing.putString(EVENT_IMAGE_URL,savedEventList.get(position).getImage());

            Intent nextActivityFraDing = new Intent(SavedEvents.this, FragmentActivity.class);












        }
    }


}
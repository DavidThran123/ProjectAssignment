package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

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

    Bundle dataInfoPass;

    public static final String EVENT_ID = "EventID";
    public static final String EVENT_NAME = "EventName";
    public static final String EVENT_DATE = "EventDate";
    public static final String EVENT_MIN = "MinPrice";
    public static final String EVENT_MAX = "MaxPrice";
    public static final String EVENT_URL = "URL";
    public static final String EVENT_IMAGE_URL = "ImageURL";

    public EventSavedListAdapter saveAdapt = new EventSavedListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);
    }


}
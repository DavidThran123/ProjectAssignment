package com.example.projectassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class COVID_19_CASE_DATA extends AppCompatActivity {
    /**
     *initizlizedall units inside {@layout activity_covid_case_data}.
     */
    TextView caseTitle;
    EditText caseSearch;
    EditText caseDate;
    Button searchButton;

    Button saveButton;
    EditText dateSearch;
    EditText endDateSearch;
    Button helpButton;

    /**
     *final names for the bundle to pass to {@code CovidFragment}.
     */
    public static final String COUNTRY_NAME = "CountryName";
    public static final String START_DATE = "StartDate";
    public static final String END_DATE = "EndDate";

    /**
     * SharedPreference provides an interface for saving data to a file in emulator.
     * Initialized SharedPreference variables
     */
    SharedPreferences sp = null;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_case_data);

        CovidFragment covidFragment =new CovidFragment();

        /**
         *initialized elements of {@layout activity_covid_case_data}.
         */
        caseSearch = findViewById(R.id.caseSearchCountry);
        dateSearch = findViewById(R.id.calendarEditText);
        endDateSearch = findViewById(R.id.calendarEndDateEditText);

        sp = getSharedPreferences("countrySave",Context.MODE_PRIVATE);

        String country = sp.getString(COUNTRY_NAME,"");
        String date = sp.getString(START_DATE,"");
        String endDate = sp.getString(END_DATE,"");

        caseSearch.setText(country);
        dateSearch.setText(date);
        endDateSearch.setText(endDate);

        caseTitle = findViewById(R.id.caseTitleJPark);

        e = sp.edit();

        /**
         * helpButton sets the message to display when clicked. This shows instructions on how to utilize the {@code COVID_19_CASE_DATA}.
         */
        helpButton =findViewById(R.id.helpCountryButton);
        helpButton.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
            alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Instructions:")
                    .setMessage("Enter country name in the first text box in "+"capital letters"+"\n"+
                            "Enter start date in second text box in YYYY-MM-DD format"+"\n"+
                            "Enter end date in third text box in YYYY-MM-DD format")
                    .setNeutralButton("Back",(click,arg)->{})
                    .setView(getLayoutInflater().inflate(R.layout.activity_alert,null))
                    .create().show();
        });

        /**
         * initialized the search button, and applied a click listener, and saves edit text contents with shared preference
         */
        searchButton = findViewById(R.id.caseSearchCountryButton);
        searchButton.setOnClickListener(v -> {
            e.putString(COUNTRY_NAME,caseSearch.getText().toString());
            e.commit();
            e.putString(START_DATE,dateSearch.getText().toString());
            e.commit();
            e.putString(END_DATE,endDateSearch.getText().toString());
            e.commit();
            loadCovidList();
        });

        caseDate = findViewById(R.id.calendarEditText);

        /**
         *initialized save button and loads {@code SavedCovid}.
         */
        saveButton = findViewById(R.id.savedCountryButton);
        saveButton.setOnClickListener(v -> {
            loadSavedCovid();
        });


    }

    /**
     * saves the input from users on each edit text on activity_covid_case_data
     * loads the data to the CovidList.java with string code
     * also switches to the next page {@code CovidList}.
     */
    private void loadCovidList(){
        Intent goToCovidList = new Intent(COVID_19_CASE_DATA.this,CovidList.class);

        EditText countryText = findViewById(R.id.caseSearchCountry);
        EditText dateText =  findViewById(R.id.calendarEditText);
        EditText endDateText = findViewById(R.id.calendarEndDateEditText);

        goToCovidList.putExtra("country",countryText.getText().toString());
        goToCovidList.putExtra("dates",dateText.getText().toString());
        goToCovidList.putExtra("endDates",endDateText.getText().toString());

        startActivity(goToCovidList);
    }

    /**
     * loads the data to SavedCovid.java
     * also switches to the next page {@code SavedCovid}.
     */
    private void loadSavedCovid(){
        Intent goToSavedCovid = new Intent(COVID_19_CASE_DATA.this,SavedCovid.class);
        startActivity(goToSavedCovid);
    }
}

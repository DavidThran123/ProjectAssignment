package com.example.projectassignment;

import android.app.AlertDialog;
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

public class COVID_19_CASE_DATA extends AppCompatActivity {//question: about differenece between API and COVID
    TextView caseTitle;
    EditText caseSearch;
    EditText caseDate;
    Button searchButton;

    ProgressBar caseBar;
    TextView caseResult;
    ListView covidListView;
    Button saveButton;
    EditText dateSearch;

    public int covidCase;
    public Bundle dataToPassFred;
    public ArrayList<Covid> covidArrayList = new ArrayList<>();
    public CovidListAdapter covidListAdapter = new CovidListAdapter();

    public static final String COUNTRY_NAME = "CountryName";
    public static final String COUNTRY_CODE = "CountryCode";
    public static final String PROVINCE_NAME = "ProvinceName";
    public static final String START_DATE = "StartDate";
    public static final String COVID_CASES = "CovidCases";

    SharedPreferences sp = null;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_case_data);

        //covidListView = findViewById(R.id.caseListView);
        //covidListView.setAdapter(covidListAdapter);

        CovidFragment covidFragment =new CovidFragment();

        caseSearch = findViewById(R.id.caseSearchCountry);//edit text
        dateSearch = findViewById(R.id.calendarEditText);

        sp = getSharedPreferences("countrySave",Context.MODE_PRIVATE);

        String country = sp.getString(COUNTRY_NAME,"");
        String date = sp.getString(START_DATE,"");

        caseSearch.setText(country);
        dateSearch.setText(date);

        caseTitle = findViewById(R.id.caseTitleJPark);

        e = sp.edit();

        searchButton = findViewById(R.id.caseSearchCountryButton);//search button
        searchButton.setOnClickListener(v -> {
            e.putString(COUNTRY_NAME,caseSearch.getText().toString());
            e.commit();
            e.putString(START_DATE,dateSearch.getText().toString());
            e.commit();
            loadCovidList();
        });

        caseDate = findViewById(R.id.calendarEditText);

        //caseBar = findViewById(R.id.caseProgressBar);//progress bar

        saveButton = findViewById(R.id.savedCountryButton);//view saved countries
        saveButton.setOnClickListener(v -> {
            loadSavedCovid();
        });


    }

    private class CovidListAdapter extends BaseAdapter{
        @Override // number of items in the list
        public int getCount() {
            return  covidArrayList.size();
        }

        @Override // what string goes at row i
        public Object getItem(int i) {
            return covidArrayList.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return covidArrayList.get(i).getId();
        }

        @Override //controls which widgets are on the row
        public View getView(int i, View old, ViewGroup parent){
                LayoutInflater inflater = getLayoutInflater();
                View newView = inflater.inflate(R.layout.activity_covid, parent, false);
                TextView textview = newView.findViewById(R.id.covidTitle);
                Covid thisCovid = (Covid) getItem(i);
                textview.setText(thisCovid.getCountry());
                return newView;
        }
    }

    private void loadCovidList(){
        Intent goToCovidList = new Intent(COVID_19_CASE_DATA.this,CovidList.class);

        EditText countryText = findViewById(R.id.caseSearchCountry);
        EditText dateText =  findViewById(R.id.calendarEditText);

        goToCovidList.putExtra("country",countryText.getText().toString());
        goToCovidList.putExtra("dates",dateText.getText().toString());
        startActivity(goToCovidList);
    }

    private void loadSavedCovid(){
        Intent goToSavedCovid = new Intent(COVID_19_CASE_DATA.this,SavedCovid.class);
        startActivity(goToSavedCovid);
    }
}

package com.example.projectassignment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CovidList extends AppCompatActivity {
    ProgressBar progressBar;
    ListView covidList;

    private CovidListAdapter listAdapter;
    private ArrayList<Covid> covidArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidlist);

        covidList = findViewById(R.id.covidListView);
        covidList.setAdapter(listAdapter = new CovidListAdapter());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String countryNameList = intent.getStringExtra("country");
        String startDateList = intent.getStringExtra("dates");

        CovidQuery query = new CovidQuery();
        query.execute("https://api.covid19api.com/country/"+countryNameList+"/status/confirmed/live?from="+startDateList+"T00:00:00Z&to=2020-10-15T00:00:00Z");

        boolean isTablet = findViewById(R.id.frameLayout) != null;

        covidList.setOnItemClickListener((list,view,position,id) -> {
            Bundle dataToPassFred = new Bundle();
            dataToPassFred.putString("country",covidArrayList.get(position).getCountry());
            dataToPassFred.putString("province",covidArrayList.get(position).getProvince());
            dataToPassFred.putString("countryCode",covidArrayList.get(position).getCountryCode());
            dataToPassFred.putString("startDate",covidArrayList.get(position).getStartDate());
            dataToPassFred.putString("cases",covidArrayList.get(position).getCaseNumber());

            if(isTablet){
                CovidFragment cFragment = new CovidFragment();
                cFragment.setArguments(dataToPassFred);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,cFragment).commit();
            }else{
                Intent nextActivity = new Intent(CovidList.this,CovidDetail.class);
                nextActivity.putExtras(dataToPassFred);
                startActivity(nextActivity);
            }
        });
    }

    private class CovidListAdapter extends BaseAdapter {
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

    private class CovidQuery extends AsyncTask<String, Integer, String> {
        public String countryName;
        public String provinceName;
        public int idList;
        public String countryCode;
        public String startDate;
        public String numberOfCase;
        boolean result;
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(xpp.getName().equals("Country")){
                        countryName = xpp.nextText();
                        publishProgress(16);
                    }else if(xpp.getName().equals("CountryCode")){
                        countryCode = xpp.getText();
                        publishProgress(33);
                    }else if(xpp.getName().equals("Province")){
                        provinceName = xpp.getText();
                        publishProgress(50);
                    }else if(xpp.getName().equals("Cases")){
                        numberOfCase = xpp.getText();
                        publishProgress(67);
                    }else if(xpp.getName().equals("Date")){
                        startDate = xpp.getText();
                        publishProgress(83);
                    }
                    if(!countryName.equals("")&&!countryCode.equals("")&&!provinceName.equals("")&&!numberOfCase.equals("")&&!startDate.equals("")){
                        covidArrayList.add(new Covid(countryCode,countryName,provinceName,numberOfCase,startDate));
                        countryCode = "";
                        countryName = "";
                        provinceName = "";
                        numberOfCase = "";
                        startDate = "";
                    }
                    eventType = xpp.next();
                }


            }catch (Exception e){
            }
            return "Done";
        }

        public void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        public void onPostExecute(String fromDoInBackground) {
            listAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

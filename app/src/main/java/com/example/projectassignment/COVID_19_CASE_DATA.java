package com.example.projectassignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

public class COVID_19_CASE_DATA extends AppCompatActivity {
    TextView caseTitle;
    EditText caseSearch;
    Button searchButton;
    ProgressBar caseBar;
    TextView caseResult;
    ListView covidListView;
    public int covidCase;
    public Bundle dataToPassFred;
    public ArrayList<Covid> covidArrayList = new ArrayList<>();
    public CovidListAdapter covidListAdapter = new CovidListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_case_data);

        covidListView = findViewById(R.id.caseListView);
        covidListView.setAdapter(covidListAdapter);

        caseTitle = findViewById(R.id.caseTitleJPark);
        caseSearch = findViewById(R.id.caseSearchCountry);
        searchButton = findViewById(R.id.caseSearchCountryButton);
        caseBar = findViewById(R.id.caseProgressBar);


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

    private class CovidQuery extends AsyncTask<String, Integer, String> {
        public String countryName;
        public String provinceName;
        public int idList;
        public String numberDate;
        public int numberOfCase;
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

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String albumSearch = sb.toString();
                JSONObject object = new JSONObject(albumSearch);

                setProgress(0);
                if(!object.isNull("country")){
                    JSONArray jsonArray = object.getJSONArray("country");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject JO = (JSONObject)jsonArray.get(i);
                        countryName = JO.getString("Country");
                        provinceName = JO.getString("Province");
                        numberOfCase = JO.getInt("Cases");
                        numberDate = JO.getString("Date");
                        covidArrayList.add(new Covid(countryName,provinceName,numberOfCase,numberDate));
                    }
                    text = "Search result found";
                    result = true;
                }else{
                    text = "Search result not found";
                    result = false;
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return "Done";
        }

        public void onProgressUpdate(Integer... value) {
            caseBar.setVisibility(View.VISIBLE);
            caseBar.setProgress(value[0]);
        }

        public void onPostExecute(String fromDoInBackground) {
            covidListAdapter.notifyDataSetChanged();
            if(result){
                caseResult.setText(countryName);
                Toast.makeText(context,text,duration).show();
            }else{
                caseResult.setText("");
                Toast.makeText(context,text,duration).show();
            }
            Log.i("HTTP",fromDoInBackground);
        }
    }

    private class CovidSearch extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... args) {
            return "done";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        public void onPostExecute(String fromDoInBackground) {
            dataToPassFred = new Bundle();
        }
    }

}

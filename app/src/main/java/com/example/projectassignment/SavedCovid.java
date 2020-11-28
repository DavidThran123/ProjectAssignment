package com.example.projectassignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SavedCovid extends AppCompatActivity {
    public static final String COUNTRY_NAME = "CountryName";
    public static final String PROVINCE_NAME = "ProvinceName";
    public static final String START_DATE = "StartDate";
    public static final String COVID_CASES = "CovidCases";
    Bundle dataToPassFred;
    TextView cntryName;
    ListView cntryNames;
    public SQLiteDatabase savedData;
    private ArrayList<Covid> scList = new ArrayList<>();
    public CovidSavedListHelper savedAdapter = new CovidSavedListHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedcovid);

        loadCovidDataFromDatabase();
        cntryName = (TextView)findViewById(R.id.CovidSavedTitle);
        cntryNames = (ListView)findViewById(R.id.SavedCovidListView);
        cntryNames.setAdapter(savedAdapter);
        savedAdapter.notifyDataSetChanged();

        cntryNames.setOnItemClickListener((parent,view,position,id)->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Would you like to enter this country?")
                    .setMessage("Country: "+scList.get(position).getCountry())
                    .setPositiveButton("Yes",(click,arg)->{
                        dataToPassFred = new Bundle();
                        dataToPassFred.putString(COUNTRY_NAME,scList.get(position).getCountry());
                        dataToPassFred.putString(PROVINCE_NAME,scList.get(position).getProvince());
                        dataToPassFred.putString(START_DATE,scList.get(position).getStartDate());

                        dataToPassFred.putInt(COVID_CASES,scList.get(position).getCaseNumber());

                        Intent nextActivityToFragEmpty =new Intent(SavedCovid.this,CovidFragmentEmpty.class);
                        nextActivityToFragEmpty.putExtras(dataToPassFred);
                        startActivityForResult(nextActivityToFragEmpty,10);
                        onActivityResult(500,500,null);
                    })
                    .setNegativeButton("No",(click,arg)->{})
                    .create().show();
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            finish();
        }
    }
    private void loadCovidDataFromDatabase(){
        CovidListHelper dbCovid = new CovidListHelper(this);
        savedData = dbCovid.getWritableDatabase();

        String[] columns1 = {dbCovid.T1Column2,dbCovid.T1Column3,dbCovid.T1Column4,dbCovid.T1Column5,dbCovid.T1Column6};

        Cursor results1 = savedData.query(false, dbCovid.TABLE_NAME1,columns1,null,null,null,null,null,null);

        int countryCodeColumnIndex = results1.getColumnIndex(dbCovid.T1Column2);
        int countryNameColumnIndex = results1.getColumnIndex(dbCovid.T1Column3);
        int provinceNameColumnIndex = results1.getColumnIndex(dbCovid.T1Column4);
        int startDateColumnIndex = results1.getColumnIndex(dbCovid.T1Column5);
        int covidCasesColumnIndex = results1.getColumnIndex(dbCovid.T1Column6);

        while(results1.moveToNext()){
            String countryName = results1.getString(countryNameColumnIndex);
            String provinceName = results1.getString(provinceNameColumnIndex);
            String startDate = results1.getString(startDateColumnIndex);
            int covidCases = results1.getInt(covidCasesColumnIndex);
            String countryCode = results1.getString(countryCodeColumnIndex);
            Cursor results2 = savedData.rawQuery("SELECT * FROM provinceName where covidID = ?", new String[]{String.valueOf(countryCode)});
            ArrayList<String> savedCountries = new ArrayList<>();
            while(results2.moveToNext()){
                int i = results2.getColumnIndex(dbCovid.T1Column3);
                savedCountries.add(results2.getString(i));
            }
            scList.add(new Covid(countryCode,countryName,provinceName,covidCases,startDate));
        }
    }

    private class CovidSavedListHelper extends BaseAdapter {
        @Override // number of items in the list
        public int getCount() {
            return  scList.size();
        }

        @Override // what string goes at row i
        public Object getItem(int i) {
            return scList.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return scList.get(i).getId();
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
}

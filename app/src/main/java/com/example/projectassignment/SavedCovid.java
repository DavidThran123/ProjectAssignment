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

import com.google.android.material.snackbar.Snackbar;

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

        cntryName = (TextView)findViewById(R.id.CovidSavedTitle);
        cntryNames = (ListView)findViewById(R.id.SavedCovidListView);
        cntryNames.setAdapter(savedAdapter);
        savedAdapter.notifyDataSetChanged();

        cntryNames.setOnItemClickListener((parent,view,position,id)->{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Saved data")
                    .setMessage("Country: "+scList.get(position).getCountry()+"\n"+"Country code: "+scList.get(position).getCountryCode()+"\n"+
                            "Province: "+scList.get(position).getProvince()+"\n"+"Start Date: "+ scList.get(position).getStartDate()+"\n"+
                            "Number of cases: "+scList.get(position).getCaseNumber())
                    .setNeutralButton("Back",(click,arg)->{})
                    .setNegativeButton("Delete",(click,arg)->{
                        Snackbar.make(cntryNames,"Data deleted",Snackbar.LENGTH_SHORT).show();
                        scList.remove(position);
                        savedData.delete(CovidListHelper.TABLE_NAME1,CovidListHelper.T1Column1+"=?",new String[]{Long.toString(id)});
                        savedAdapter.notifyDataSetChanged();
                    })
                    .setView(getLayoutInflater().inflate(R.layout.activity_alert,null))
                    .create().show();
        });
        loadCovidDataFromDatabase();
    }

    private void loadCovidDataFromDatabase(){
        CovidListHelper dbCovid = new CovidListHelper(this);
        savedData = dbCovid.getWritableDatabase();

        String[] columns = {dbCovid.T1Column1,dbCovid.T1Column2,dbCovid.T1Column3,dbCovid.T1Column4,dbCovid.T1Column5 ,dbCovid.T1Column6};

        Cursor results1 = savedData.query(false, dbCovid.TABLE_NAME1,columns,null,null,null,null,null,null);

        int idColumnIndex = results1.getColumnIndex(CovidListHelper.T1Column1);
        int countryCodeColumnIndex = results1.getColumnIndex(dbCovid.T1Column2);
        int countryNameColumnIndex = results1.getColumnIndex(dbCovid.T1Column3);
        int provinceNameColumnIndex = results1.getColumnIndex(dbCovid.T1Column4);
        int startDateColumnIndex = results1.getColumnIndex(dbCovid.T1Column5);
        int covidCasesColumnIndex = results1.getColumnIndex(dbCovid.T1Column6);

        while(results1.moveToNext()){
            long covidID = results1.getLong(idColumnIndex);
            String countryCode = results1.getString(countryCodeColumnIndex);
            String countryName = results1.getString(countryNameColumnIndex);
            String provinceName = results1.getString(provinceNameColumnIndex);
            String covidCases = results1.getString(covidCasesColumnIndex);
            String startDate = results1.getString(startDateColumnIndex);

            scList.add(new Covid(covidID,countryCode,countryName,provinceName,covidCases,startDate));
        }
        savedAdapter.notifyDataSetChanged();
    }

    private class CovidSavedListHelper extends BaseAdapter {
        @Override // number of items in the list
        public int getCount() {
            return  scList.size();
        }

        @Override // what string goes at row position
        public Object getItem(int position) {
            return scList.get(position);
        }

        @Override //database id of item at row position
        public long getItemId(int position) {
            return scList.get(position).getId();
        }


        @Override //controls which widgets are on the row
        public View getView(int position, View old, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.activity_covid, parent, false);

            TextView textview = newView.findViewById(R.id.covidTitle);
            Covid thisCovid = (Covid) getItem(position);
            textview.setText(thisCovid.getProvince());

            return newView;
        }
    }
}

package com.example.projectassignment;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CovidFragment extends Fragment{
    private Bundle dataFromActivityFred;
    private AppCompatActivity parentActivityFred;
    private String fragCountryName;
    private String fragProvinceName;
    private String fragStartDate;
    private String fragCountryCode;
    private int fragNumberOfCases;

    private Integer covidID;
    private String fragButtonText;
    private ArrayList<String> fragCovidData;
    public SQLiteDatabase covidDataSave;


    public final static String T1Column2 = "CountryName";
    public final static String T1Column3 = "ProvinceName";
    public final static String T1Column4 = "StartDate";
    public final static String T1Column5 = "EndDate";
    public final static String T1Column6 = "CovidCases";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View covidFileOpened = inflater.inflate(R.layout.activity_covidfragment,container,false);
        dataFromActivityFred = getArguments();
        fragCountryName = dataFromActivityFred.getString(COVID_19_CASE_DATA.COUNTRY_NAME);
        fragCountryCode = dataFromActivityFred.getString(COVID_19_CASE_DATA.COUNTRY_CODE);
        fragProvinceName = dataFromActivityFred.getString(COVID_19_CASE_DATA.PROVINCE_NAME);
        fragStartDate = dataFromActivityFred.getString(COVID_19_CASE_DATA.START_DATE);
        fragNumberOfCases = dataFromActivityFred.getInt(COVID_19_CASE_DATA.COVID_CASES);

        TextView title = (TextView) covidFileOpened.findViewById(R.id.covidFragmentTitle);
        title.setText(fragCountryName);
        TextView country = (TextView)covidFileOpened.findViewById(R.id.covidCountryFragment);
        country.setText("Country: "+fragCountryName);
        TextView province = (TextView)covidFileOpened.findViewById(R.id.covidProvinceFragment);
        province.setText("Province: "+fragProvinceName);
        TextView startDate = (TextView)covidFileOpened.findViewById(R.id.covidStartDateFragment);
        startDate.setText("Start Date: "+fragStartDate);
        TextView countryCode = (TextView)covidFileOpened.findViewById(R.id.covidCountryCodeDateFragment);
        countryCode.setText("Country Code: "+fragCountryCode);
        TextView cases = (TextView)covidFileOpened.findViewById(R.id.covidCasesFragment);
        cases.setText("Cases: "+fragNumberOfCases);

        Button saveCovidData = (Button)covidFileOpened.findViewById(R.id.covidSaveButton);
        saveCovidData.setText(fragButtonText);
        saveCovidData.setOnClickListener(click -> {
            if(saveCovidData.getText().equals("SAVE")){
                CovidListHelper alh = new CovidListHelper(this.getContext());
                covidDataSave = alh.getReadableDatabase();
                Cursor cursor = covidDataSave.rawQuery("SELECT * FROM CountryName WHERE CovidID = ?", new String[]{String.valueOf(fragCountryCode)});
                if(cursor.getCount()==0){

                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(CovidListHelper.T1Column2,fragCountryCode);
                    newRowValues.put(CovidListHelper.T1Column3,fragCountryName);
                    newRowValues.put(CovidListHelper.T1Column4,fragProvinceName);
                    newRowValues.put(CovidListHelper.T1Column5,fragStartDate);
                    newRowValues.put(CovidListHelper.T1Column6,fragNumberOfCases);
                    long newIdCovid = covidDataSave.insert(CovidListHelper.TABLE_NAME1,null,newRowValues);
                    Snackbar snackbar1 =Snackbar.make(click,"Saved data",Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }else{
                    Snackbar snackbar2 = Snackbar.make(click,"Data is already saved",Snackbar.LENGTH_LONG);
                    snackbar2.show();
                }
            }else if(saveCovidData.getText().equals("DELETE")){
                CovidListHelper alh = new CovidListHelper(this.getContext());
                covidDataSave = alh.getReadableDatabase();
                boolean t1 = covidDataSave.delete(CovidListHelper.TABLE_NAME1, CovidListHelper.T1Column2 + "=" + covidID, null) > 0;
                if (t1 == false){
                    getActivity().setResult(500);
                    getActivity().finish();
                }else{
                }
            }
        });
        return covidFileOpened;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivityFred = (AppCompatActivity)context;
    }
}

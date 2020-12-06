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
    private String buttonText;
    private String fragNumberOfCases;
    public SQLiteDatabase covidDataSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View covidFileOpened = inflater.inflate(R.layout.activity_covidfragment,container,false);
        dataFromActivityFred = getArguments();
        fragCountryName = dataFromActivityFred.getString("country");
        fragCountryCode = dataFromActivityFred.getString("countryCode");
        fragProvinceName = dataFromActivityFred.getString("province");
        fragStartDate = dataFromActivityFred.getString("startDate");
        fragNumberOfCases = dataFromActivityFred.getString("cases");

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

        saveCovidData.setOnClickListener(click -> {
                CovidListHelper alh = new CovidListHelper(this.getContext());
                covidDataSave = alh.getReadableDatabase();
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(CovidListHelper.T1Column2,fragCountryCode);
                    newRowValues.put(CovidListHelper.T1Column3,fragCountryName);
                    newRowValues.put(CovidListHelper.T1Column4,fragProvinceName);
                    newRowValues.put(CovidListHelper.T1Column5,fragStartDate);
                    newRowValues.put(CovidListHelper.T1Column6,fragNumberOfCases);
                    covidDataSave.insert(CovidListHelper.TABLE_NAME1,null,newRowValues);
                    Snackbar snackbar1 =Snackbar.make(click,"Saved data",Snackbar.LENGTH_LONG);
                    snackbar1.show();


        });
        return covidFileOpened;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivityFred = (AppCompatActivity)context;
    }
}

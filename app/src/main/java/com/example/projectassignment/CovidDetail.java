package com.example.projectassignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Initiates the fragment activity and pass the bundle of information to this class.
 */
public class CovidDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coviddetail);

        Bundle dataToPassFred = getIntent().getExtras();
        CovidFragment cFrag = new CovidFragment();
        cFrag.setArguments(dataToPassFred);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,cFrag).commit();
    }
}

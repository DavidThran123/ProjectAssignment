package com.example.projectassignment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CovidFragmentEmpty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidfragment_empty);

        Bundle dataToPass = getIntent().getExtras();
        CovidFragment cFrag = new CovidFragment();
        cFrag.setArguments(dataToPass);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,cFrag)
                .commit();
    }
}

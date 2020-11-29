package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//Used to start the fragment activity and pass the bundle of information
//sent
public class AlbumFragmentEmpty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_fragment_empty);
        Bundle dataToPass = getIntent().getExtras();

        AlbumFragment dFragment = new AlbumFragment();
        dFragment.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, dFragment)
                .commit();
    }
}
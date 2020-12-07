package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//This activity is used to view all saved albums
public class SavedAlbums extends AppCompatActivity {
    //Initialize all static variables for the bundle
    public static final String ALBUM_NAME = "ALNAME";
    public static final String ALID = "ALID";
    public static final String ARTIST_NAME = "ABNAME";
    public static final String YEAR = "YEAR";
    public static final String GENRE = "GENRE";
    public static final String DESCRIPTION = "DIS";
    public static final String SOD = "SOD";
    public static final String SONGS = "SONGS";
    //make the bundle variable
    Bundle dataToPassDavid;

    //Initialize elements on view
    TextView AlbumName;
    ListView AlbumNames;

    //Initialize the ArrayList of Albums saved
    private ArrayList<Album> SAList = new ArrayList<>();

    //Make the db variable
    public SQLiteDatabase savedHelper;

    //Initialize the adapter
    public MusicSavedListAdapter savedAdapter = new MusicSavedListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_albums);

        //Called to load saved Albums and songs from database
        loadAlbumsFromDatabase();

        //Initialize elements on page
        AlbumName = (TextView) findViewById(R.id.AlbumsSavedTitle);
        AlbumNames = (ListView) findViewById(R.id.listedSavedAlbums);

        //set adapter and update the listview with data from database
        AlbumNames.setAdapter(savedAdapter);
        savedAdapter.notifyDataSetChanged();

        //Click listener for the listview of Saved Album names
        AlbumNames.setOnItemClickListener((parent, view, position, id) -> {

            //Make alert dialog to ask if user wants to enter the album clicked
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Would you like to enter this Album?")
                    .setMessage("Album: " +SAList.get(position).getAlbumName())
                    .setPositiveButton("Yes", (click, arg) ->{

                        //If yes populate bundle with the selected albums information
                        dataToPassDavid = new Bundle();
                        dataToPassDavid.putString(ALBUM_NAME, SAList.get(position).getAlbumName());
                        dataToPassDavid.putString(ARTIST_NAME, SAList.get(position).getArtistName());
                        dataToPassDavid.putInt(YEAR, SAList.get(position).getYear());
                        dataToPassDavid.putString(GENRE, SAList.get(position).getGenre());
                        //Button text is now Delete instead of save for fragment view
                        dataToPassDavid.putString(SOD, "DELETE");
                        dataToPassDavid.putString(DESCRIPTION, SAList.get(position).getAlbumDis());
                        dataToPassDavid.putStringArrayList(SONGS, SAList.get(position).getSongsInAlbum());
                        dataToPassDavid.putInt(ALID, SAList.get(position).getIdAlbum());

                        //Starts fragment activity to open fragment and populate new view
                        Intent nextActivityFragDave = new Intent(SavedAlbums.this, AlbumFragmentEmpty.class);
                        //send bundle
                        nextActivityFragDave.putExtras(dataToPassDavid);
                        //starts activity for a result
                        startActivityForResult(nextActivityFragDave, 10);
                        //Called when Album gets deleted in fragment activity
                        onActivityResult(500, 500, null);

                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .create().show();
        });
    }

    //If result equals request then finish this activity and go back to parent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            finish();
        }
    }

    //This function is made to load all the data from the database tables
    private void loadAlbumsFromDatabase()
    {
        AlbumListHelper dbAlbum = new AlbumListHelper(this);
        savedHelper = dbAlbum.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer

        //Initialize a String array containg all column names
        String [] columns1 = {dbAlbum.T1Col2, dbAlbum.T1Col3, dbAlbum.T1Col4, dbAlbum.T1Col5, dbAlbum.T1Col6, dbAlbum.T1Col7};

        //Grab all Albums
        Cursor results1 = savedHelper.query(false, dbAlbum.TABLE_NAME1, columns1, null, null, null, null, null, null);

        //Get all column indexes inside the Album Table
        int AlbumIDColumnIndex = results1.getColumnIndex(dbAlbum.T1Col2);
        int ArtistNameColumnIndex = results1.getColumnIndex(dbAlbum.T1Col3);
        int YearolumnIndex = results1.getColumnIndex(dbAlbum.T1Col4);
        int GenreColumnIndex = results1.getColumnIndex(dbAlbum.T1Col5);
        int DescriptionColumnIndex = results1.getColumnIndex(dbAlbum.T1Col6);
        int AlbumNameColumnIndex = results1.getColumnIndex(dbAlbum.T1Col7);

        //Loop till end of Albums table
        while(results1.moveToNext())
        {
            //Initialize all of the Object Albums variables
            String AlbumName = results1.getString(AlbumNameColumnIndex);
            String ArtistName = results1.getString(ArtistNameColumnIndex);
            int year = results1.getInt(YearolumnIndex);
            String genre = results1.getString(GenreColumnIndex);
            int idAlbum = results1.getInt(AlbumIDColumnIndex);
            String albumDis = results1.getString(DescriptionColumnIndex);

            //Make a cursor that contains the results of the Songs DB
            //Grabbing only the songs that have the same AlbumID as the current album
            Cursor results2 = savedHelper.rawQuery("SELECT * FROM Songs WHERE AlbumID = ?", new String[]{String.valueOf(idAlbum)});

            //Initialize an array of strings to store the song names
            ArrayList<String> savedSongs = new ArrayList<>();

            //Loop through all matching songs
            while(results2.moveToNext()){
                //Get the column index of SongName
                int i = results2.getColumnIndex(dbAlbum.T2Col3);
                //And add the song name to the arrayList<String>
                savedSongs.add(results2.getString(i));
            }

            //add all the data of the current album and the arraylist of songs
            //to the ArrayLis<Album> variable creating an object with all needed information
            //each time it loops
            SAList.add(new Album(AlbumName,ArtistName,year,genre,idAlbum,albumDis,savedSongs));
        }
    }


    //This adapter will inflate the saved albums listview with Album names from db
    private class MusicSavedListAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  SAList.size();
        }

        @Override // what object goes at row i
        public Object getItem(int i) {
            return SAList.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return i;
        }

        @Override //controls which widgets are on the row
        public View getView(int i, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.albumlayout, parent, false);
            TextView textview = newView.findViewById(R.id.albumTitle);
            Album thisAlbum = (Album) getItem(i);
            textview.setText(thisAlbum.getAlbumName());
            return newView;

        }
    }
}
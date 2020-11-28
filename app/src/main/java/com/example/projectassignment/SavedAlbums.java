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

import java.util.ArrayList;

public class SavedAlbums extends AppCompatActivity {
    public static final String ALBUM_NAME = "ALNAME";
    public static final String ALID = "ALID";
    public static final String ARTIST_NAME = "ABNAME";
    public static final String YEAR = "YEAR";
    public static final String GENRE = "GENRE";
    public static final String DESCRIPTION = "DIS";
    public static final String SOD = "SOD";
    public static final String SONGS = "SONGS";
    Bundle dataToPassDavid;
    TextView AlbumName;
    ListView AlbumNames;
    public SQLiteDatabase savedHelper;
    private ArrayList<Album> SAList = new ArrayList<>();
    public MusicSavedListAdapter savedAdapter = new MusicSavedListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_albums);
        loadAlbumsFromDatabase();
        AlbumName = (TextView) findViewById(R.id.AlbumsSavedTitle);
        AlbumNames = (ListView) findViewById(R.id.listedSavedAlbums);
        AlbumNames.setAdapter(savedAdapter);
        savedAdapter.notifyDataSetChanged();

        AlbumNames.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Would you like to enter this Album?")
                    .setMessage("Album: " +SAList.get(position).getAlbumName())
                    .setPositiveButton("Yes", (click, arg) ->{
                        dataToPassDavid = new Bundle();
                        dataToPassDavid.putString(ALBUM_NAME, SAList.get(position).getAlbumName());
                        dataToPassDavid.putString(ARTIST_NAME, SAList.get(position).getArtistName());
                        dataToPassDavid.putInt(YEAR, SAList.get(position).getYear());
                        dataToPassDavid.putString(GENRE, SAList.get(position).getGenre());
                        dataToPassDavid.putString(SOD, "DELETE");
                        dataToPassDavid.putString(DESCRIPTION, SAList.get(position).getAlbumDis());
                        dataToPassDavid.putStringArrayList(SONGS, SAList.get(position).getSongsInAlbum());
                        dataToPassDavid.putInt(ALID, SAList.get(position).getIdAlbum());

                        //Starts activity to open fragment and populate new view
                        Intent nextActivityFragDave = new Intent(SavedAlbums.this, AlbumFragmentEmpty.class);
                        nextActivityFragDave.putExtras(dataToPassDavid);
                        startActivityForResult(nextActivityFragDave, 10);
                        onActivityResult(500, 500, null);

                    })
                    .setNegativeButton("No", (click, arg) -> { })
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
    private void loadAlbumsFromDatabase()
    {
        AlbumListHelper dbAlbum = new AlbumListHelper(this);
        savedHelper = dbAlbum.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer
        //Cursor savedAlbums =

        String [] columns1 = {dbAlbum.T1Col2, dbAlbum.T1Col3, dbAlbum.T1Col4, dbAlbum.T1Col5, dbAlbum.T1Col6, dbAlbum.T1Col7};

        Cursor results1 = savedHelper.query(false, dbAlbum.TABLE_NAME1, columns1, null, null, null, null, null, null);

        int AlbumIDColumnIndex = results1.getColumnIndex(dbAlbum.T1Col2);
        int ArtistNameColumnIndex = results1.getColumnIndex(dbAlbum.T1Col3);
        int YearolumnIndex = results1.getColumnIndex(dbAlbum.T1Col4);
        int GenreColumnIndex = results1.getColumnIndex(dbAlbum.T1Col5);
        int DescriptionColumnIndex = results1.getColumnIndex(dbAlbum.T1Col6);
        int AlbumNameColumnIndex = results1.getColumnIndex(dbAlbum.T1Col7);

        while(results1.moveToNext())
        {
            String AlbumName = results1.getString(AlbumNameColumnIndex);
            String ArtistName = results1.getString(ArtistNameColumnIndex);
            int year = results1.getInt(YearolumnIndex);
            String genre = results1.getString(GenreColumnIndex);
            int idAlbum = results1.getInt(AlbumIDColumnIndex);
            String albumDis = results1.getString(DescriptionColumnIndex);
            Cursor results2 = savedHelper.rawQuery("SELECT * FROM Songs WHERE AlbumID = ?", new String[]{String.valueOf(idAlbum)});
            ArrayList<String> savedSongs = new ArrayList<>();
            while(results2.moveToNext()){
                int i = results2.getColumnIndex(dbAlbum.T2Col3);
                savedSongs.add(results2.getString(i));
            }
            SAList.add(new Album(AlbumName,ArtistName,year,genre,idAlbum,albumDis,savedSongs));
        }


    }

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
            return SAList.get(i).getIdAl();
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
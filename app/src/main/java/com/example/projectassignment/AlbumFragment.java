package com.example.projectassignment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class AlbumFragment extends Fragment {
    private Bundle dataFromActivityDavid;
    private AppCompatActivity parentActivityDavid;
    private String AlbumName;
    private String ArtistName;
    private int year;
    private String genre;
    private String buttonText;
    private String dis;
    private Integer albumID;
    public SQLiteDatabase dbAlSave;
    private ArrayList<String> songData;


    public final static String T1Col2 = "AlbumID";
    public final static String T1Col3 = "ArtistName";
    public final static String T1Col4 = "Year";
    public final static String T1Col5 = "Genre";
    public final static String T1Col6 = "Description";
    public final static String T1Col7 = "AlbumName";
    public final static String T2Col2 = "AlbumID";
    public final static String T2Col3 = "SongName";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivityDavid = getArguments();
        AlbumName = dataFromActivityDavid.getString(AUDIO_DATABASE_API.ALBUM_NAME);
        ArtistName = dataFromActivityDavid.getString(AUDIO_DATABASE_API.ARTIST_NAME);
        year = dataFromActivityDavid.getInt(AUDIO_DATABASE_API.YEAR);
        genre = dataFromActivityDavid.getString(AUDIO_DATABASE_API.GENRE);
        buttonText = dataFromActivityDavid.getString(AUDIO_DATABASE_API.SOD);
        dis = dataFromActivityDavid.getString(AUDIO_DATABASE_API.DESCRIPTION);
        songData = dataFromActivityDavid.getStringArrayList(AUDIO_DATABASE_API.SONGS);
        albumID = dataFromActivityDavid.getInt(AUDIO_DATABASE_API.ALID);

        // Inflate the layout for this fragment
        View albumOpened = inflater.inflate(R.layout.fragment_album, container, false);
        TextView title = (TextView) albumOpened.findViewById(R.id.albumFragmentTitle);
        title.setText(AlbumName);
        TextView artist = (TextView) albumOpened.findViewById(R.id.artistFragmentName);
        artist.setText("Artist is: " + ArtistName);
        TextView yer = (TextView) albumOpened.findViewById(R.id.albumFragmentYear);
        yer.setText("Album released: " + year);
        TextView gen = (TextView) albumOpened.findViewById(R.id.albumFragmentGenre);
        gen.setText("Genre: " + genre);
        TextView des = (TextView) albumOpened.findViewById(R.id.albumDescriptionFragment);
        des.setText(dis);
        SongListAdapter SD = new SongListAdapter();
        ListView lv = (ListView) albumOpened.findViewById(R.id.listViewFragmentSong);
        lv.setAdapter(SD);
        SD.notifyDataSetChanged();


        lv.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
            alertDialogBuilder.setTitle("Search "+songData.get(position)+" by "+ArtistName+"?")
                    .setPositiveButton("Yes", (click, arg) ->{
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+ArtistName+" "+songData.get(position))));
                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .create().show();
                });



            Button saveAlbum = (Button) albumOpened.findViewById(R.id.albumSaveButton);
            saveAlbum.setText(buttonText);
            saveAlbum.setOnClickListener(clk -> {

                AlbumListHelper dbChat = new AlbumListHelper(this.getContext());
                dbAlSave = dbChat.getReadableDatabase();
                Cursor cursor = dbAlSave.rawQuery("SELECT * FROM Albums WHERE AlbumID = ?", new String[]{String.valueOf(albumID)});
                if(cursor.getCount() == 0){
                    ContentValues newAlbumRowValues = new ContentValues();
                    newAlbumRowValues.put(AlbumListHelper.T1Col2, albumID);
                    newAlbumRowValues.put(AlbumListHelper.T1Col3, ArtistName);
                    newAlbumRowValues.put(AlbumListHelper.T1Col4, year);
                    newAlbumRowValues.put(AlbumListHelper.T1Col5, genre);
                    newAlbumRowValues.put(AlbumListHelper.T1Col6, dis);
                    newAlbumRowValues.put(AlbumListHelper.T1Col7, AlbumName);
                    long newIdAlbum = dbAlSave.insert(AlbumListHelper.TABLE_NAME1, null, newAlbumRowValues);
                    long newIdSong;
                    for(int i = 0; i < songData.size(); i++) {
                        ContentValues newSongRowValues = new ContentValues();
                        newSongRowValues.put(AlbumListHelper.T2Col2, albumID);
                        newSongRowValues.put(AlbumListHelper.T2Col3, songData.get(i));
                        newIdSong = dbAlSave.insert(AlbumListHelper.TABLE_NAME2, null, newSongRowValues);
                        Log.i("Working", "onCreateView: "+newIdSong+" IDAlbum = "+newIdAlbum );
                    }
                    Snackbar snackbar = Snackbar
                            .make(clk, "Saved album " + AlbumName, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar
                            .make(clk, "Album " + AlbumName + " already is saved", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            });




            return albumOpened;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivityDavid = (AppCompatActivity)context;
    }




    public class SongListAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  songData.size();
        }

        @Override // what string goes at row i
        public Object getItem(int i) {
            return songData.get(i);
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
            String thisSong = (String) getItem(i);
            textview.setText(thisSong);
            return newView;

        }
    }
}
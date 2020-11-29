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
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

//This fragment is used for viewing both the delete and save Album views
public class AlbumFragment extends Fragment {
    //Creating all class variables
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

    //This will populate the whole fragment view with collected date form the Database API
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Grab data from sent Bundle from parent class
        dataFromActivityDavid = getArguments();

        //Initialize all local variables with bundle data sent
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

        //Inistializing the adapter and the listview
        SongListAdapter SD = new SongListAdapter();
        ListView lv = (ListView) albumOpened.findViewById(R.id.listViewFragmentSong);
        lv.setAdapter(SD);
        SD.notifyDataSetChanged();


        //Listview listener for song items inside listview
        //When clicked an alert dialog will show asking if user
        //wants the song to be searched onto google, then launching the google
        //search intent
        lv.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
            alertDialogBuilder.setTitle("Search "+songData.get(position)+" by "+ArtistName+"?")
                    .setPositiveButton("Yes", (click, arg) ->{
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+ArtistName+" "+songData.get(position))));
                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .create().show();
                });

        //Initialize the button and set the text to the text sent
            Button doAlbum = (Button) albumOpened.findViewById(R.id.albumSaveButton);
            doAlbum.setText(buttonText);



                //if the sent string for the button equals "SAVE" save the album
            if(doAlbum.getText().equals("SAVE")) {
                //Click listener for button
                doAlbum.setOnClickListener(clk -> {
                            AlbumListHelper dbChat = new AlbumListHelper(this.getContext());
                            dbAlSave = dbChat.getReadableDatabase();

                            //Checking to see if the album that user wants to save is already saved into database
                            Cursor cursor = dbAlSave.rawQuery("SELECT * FROM Albums WHERE AlbumID = ?", new String[]{String.valueOf(albumID)});
                            if (cursor.getCount() == 0) {
                                //Adding all data into album table in DB
                                ContentValues newAlbumRowValues = new ContentValues();
                                newAlbumRowValues.put(AlbumListHelper.T1Col2, albumID);
                                newAlbumRowValues.put(AlbumListHelper.T1Col3, ArtistName);
                                newAlbumRowValues.put(AlbumListHelper.T1Col4, year);
                                newAlbumRowValues.put(AlbumListHelper.T1Col5, genre);
                                newAlbumRowValues.put(AlbumListHelper.T1Col6, dis);
                                newAlbumRowValues.put(AlbumListHelper.T1Col7, AlbumName);
                                long newIdAlbum = dbAlSave.insert(AlbumListHelper.TABLE_NAME1, null, newAlbumRowValues);
                                long newIdSong;

                                //Loop through the songs ArrayList<String> storing all song names into the song table
                                for (int i = 0; i < songData.size(); i++) {
                                    ContentValues newSongRowValues = new ContentValues();
                                    newSongRowValues.put(AlbumListHelper.T2Col2, albumID);
                                    newSongRowValues.put(AlbumListHelper.T2Col3, songData.get(i));
                                    newIdSong = dbAlSave.insert(AlbumListHelper.TABLE_NAME2, null, newSongRowValues);
                                    Log.i("Working", "onCreateView: IDSONG " + newIdSong + " IDAlbum = " + newIdAlbum);
                                }
                                //Shown if Album is saved
                                Snackbar snackbar = Snackbar
                                        .make(clk, "Saved album " + AlbumName, Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            } else {
                                //Shown if album already exists and cannot be saved
                                Snackbar snackbar = Snackbar
                                        .make(clk, "Album " + AlbumName + " already is saved", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        });
                //Checks to see if the button text sent is equal to DELETE
                //if so then starts delete functionality
            }else if(doAlbum.getText().equals("DELETE")){
                //Click listener for button
                doAlbum.setOnClickListener(clkd -> {
                AlbumListHelper dbChat = new AlbumListHelper(this.getContext());
                dbAlSave = dbChat.getReadableDatabase();
                //First deletes all songs with ALbumid of the album then deletes the album with the selected
                //album id deleting the Album and all the songs inside
                boolean t2 = dbAlSave.delete(AlbumListHelper.TABLE_NAME2, AlbumListHelper.T2Col2 + "=" + albumID, null) > 0;
                boolean t1 = dbAlSave.delete(AlbumListHelper.TABLE_NAME1, AlbumListHelper.T1Col2 + "=" + albumID, null) > 0;

                //Checks to see if delete was successful and sets result code and finishes the activity
                //And makes toast to let user know delete was successful
                if (t2 == false && t1 == false){
                    getActivity().setResult(500);
                    Toast.makeText(clkd.getContext(), "Deleted album "+AlbumName, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }else{

                }
            });
            }

            return albumOpened;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivityDavid = (AppCompatActivity)context;
    }



//Adapter for the song ListView, filled with the songs inside the ArrayList<String>
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
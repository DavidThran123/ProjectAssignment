package com.example.projectassignment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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
    private ArrayList<String> songData;
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

        // Inflate the layout for this fragment
        View albumOpened =  inflater.inflate(R.layout.fragment_album, container, false);
        TextView title = (TextView) albumOpened.findViewById(R.id.albumFragmentTitle);
        title.setText(AlbumName);
        TextView artist = (TextView) albumOpened.findViewById(R.id.artistFragmentName);
                artist.setText("Artist is: "+ArtistName);
        TextView yer = (TextView) albumOpened.findViewById(R.id.albumFragmentYear);
                yer.setText("Album released: "+year);
        TextView gen = (TextView) albumOpened.findViewById(R.id.albumFragmentGenre);
                gen.setText("Genre: "+genre);
        TextView des = (TextView) albumOpened.findViewById(R.id.albumDescriptionFragment);
        des.setText(dis);
        SongListAdapter SD = new SongListAdapter();
        ListView lv = (ListView) albumOpened.findViewById(R.id.listViewFragmentSong);
        lv.setAdapter(SD);
        SD.notifyDataSetChanged();

        // get the delete button, and add a click listener:
        Button button = (Button)albumOpened.findViewById(R.id.albumSaveButton);
        button.setText(buttonText);
        button.setOnClickListener( clk -> {

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
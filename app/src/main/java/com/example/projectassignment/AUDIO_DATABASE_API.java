package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.StringPrepParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AUDIO_DATABASE_API extends AppCompatActivity {
    public MusicListAdapter musicAdapter = new MusicListAdapter();
    public ArrayList<Album> albumsArrayList = new ArrayList<>();
    public int boat;
    public ArrayList<String> songsArrayList = new ArrayList<>();
    private Button search;
    private EditText artistSearch;
    private ProgressBar loadingBar;
    public ListView albumsListView;
    public TextView tv;
    public Bundle dataToPassDavid;
    public static final String ALBUM_NAME = "ALNAME";
    public static final String ARTIST_NAME = "ABNAME";
    public static final String YEAR = "YEAR";
    public static final String GENRE = "GENRE";
    public static final String DESCRIPTION = "DIS";
    public static final String SOD = "SOD";
    public static final String SONGS = "SONGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_database_api);

        AlbumFragment albumFragment = new AlbumFragment();
        loadingBar = findViewById(R.id.progressBar);
        artistSearch = findViewById(R.id.artistName);
        search = findViewById(R.id.searchArtistButton);

        albumsListView = (ListView)findViewById(R.id.listViewAT);
        albumsListView.setAdapter( musicAdapter );



        tv = findViewById(R.id.artistResultsText);

        search.setOnClickListener(v -> {
            albumsListView.removeAllViewsInLayout();
            albumsArrayList.clear();
            AlbumQuery music = new AlbumQuery();
            artistSearch = findViewById(R.id.artistName);
            music.execute("https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s="+artistSearch.getText().toString().replaceAll(" ", "%20"));
            artistSearch.setText("");
        });

        albumsListView.setOnItemClickListener((parent, view, position, id) -> {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Would you like to enter this Album?")
                    .setMessage("Album: " +albumsArrayList.get(position).getAlbumName())
                    .setPositiveButton("Yes", (click, arg) ->{
                        int ids = albumsArrayList.get(position).getIdAlbum();
                        String urlSecond = "https://theaudiodb.com/api/v1/json/1/track.php?m="+ids;
                        SongSearch ss = new SongSearch();
                        boat = position;
                        ss.execute(urlSecond);
                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .create().show();
        });


    }

    private class SongSearch extends AsyncTask<String, String, String>{


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... args) {
            try{
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();


                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String albumSearch = sb.toString(); //result is the whole string
                JSONObject object = new JSONObject(albumSearch);
                if(!object.isNull("track")) {
                    JSONArray jsonArray = object.getJSONArray("track");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject JO = (JSONObject) jsonArray.get(i);
                        String songName = JO.getString("strTrack");
                        songsArrayList.add(songName);
                    }
                }else{

                }


            }catch(Exception e){

            }
            return "done";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        public void onPostExecute(String fromDoInBackground) {
            dataToPassDavid = new Bundle();
            dataToPassDavid.putString(ALBUM_NAME, albumsArrayList.get(boat).getAlbumName());
            dataToPassDavid.putString(ARTIST_NAME, albumsArrayList.get(boat).getArtistName());
            dataToPassDavid.putInt(YEAR, albumsArrayList.get(boat).getYear());
            dataToPassDavid.putString(GENRE, albumsArrayList.get(boat).getGenre());
            dataToPassDavid.putString(SOD, "SAVE");
            dataToPassDavid.putString(DESCRIPTION, albumsArrayList.get(boat).getAlbumDis());
            dataToPassDavid.putStringArrayList(SONGS, songsArrayList);
            albumsArrayList.get(boat).setSongsInAlbum(songsArrayList);
            Intent nextActivityFragDave = new Intent(AUDIO_DATABASE_API.this, AlbumFragmentEmpty.class);
            nextActivityFragDave.putExtras(dataToPassDavid);
            startActivity(nextActivityFragDave);
            songsArrayList.clear();
        }
    }







    private class AlbumQuery extends AsyncTask<String, Integer, String> {
        public String AlbName;
        public String ArtName;
        public int idAl;
        public int year;
        public String genre;
        boolean result;
        String dis = "";
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String albumSearch = sb.toString();
                JSONObject object = new JSONObject(albumSearch);

                setProgress(0);

                if(!object.isNull("album")) {
                    JSONArray jsonArray = object.getJSONArray("album");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject JO = (JSONObject) jsonArray.get(i);
                        AlbName = JO.getString("strAlbum");
                        ArtName = JO.getString("strArtist");
                        idAl = JO.getInt("idAlbum");
                        year = JO.getInt("intYearReleased");
                        genre = JO.getString("strGenre");
                        if(JO.has("strDescriptionEN")) {
                            dis = JO.getString("strDescriptionEN");
                            albumsArrayList.add(new Album(AlbName, ArtName, year, genre, idAl, dis));
                        }else {
                            albumsArrayList.add(new Album(AlbName, ArtName, year, genre, idAl));
                        }
                        setProgress((int) ((i + 1) / jsonArray.length() * 100));
                    }
                    text = "Search results found";
                    result = true;
                }else{
                    text = "No search results";
                    result = false;
                }
                } catch (FileNotFoundException e){
                e.printStackTrace();
                }catch (JSONException e){
                e.printStackTrace();
                } catch(Exception e) {
                e.printStackTrace();
                }
            return "Done";
        }

        public void onProgressUpdate(Integer... value) {
            loadingBar.setVisibility(View.VISIBLE);
            loadingBar.setProgress(value[0]);
        }

        public void onPostExecute(String fromDoInBackground) {
            musicAdapter.notifyDataSetChanged();
            if(result) {
                tv.setText(ArtName + "'s Albums: ");
                Toast.makeText(context, text, duration).show();
            }else{
                tv.setText("");
                Toast.makeText(context, text, duration).show();
            }
            Log.i("HTTP", fromDoInBackground);
        }
    }

   private class MusicListAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  albumsArrayList.size();
        }

        @Override // what string goes at row i
        public Object getItem(int i) {
            return albumsArrayList.get(i);
        }

        @Override //database id of item at row i
        public long getItemId(int i) {
            return albumsArrayList.get(i).getId();
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


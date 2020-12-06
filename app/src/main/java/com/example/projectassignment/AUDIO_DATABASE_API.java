package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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
    public MusicListAdapter musicAdapter = new MusicListAdapter(); //Variable for Album listview adapter
    //Initialize both Arraylist for songs and albums
    public ArrayList<Album> albumsArrayList = new ArrayList<>();
    public ArrayList<String> songsArrayList = new ArrayList<>();

    public int boat; //Variable saves position of item clicked for later use

    //init all units inside layout
    private Button search, helpButton, savedAlbumsButtom;
    private EditText artistSearch;
    private ProgressBar loadingBar;
    public ListView albumsListView;
    public TextView tv;

    //Sets final names for the bundle to pass to fragment
    public Bundle dataToPassDavid;
    public static final String ALBUM_NAME = "ALNAME";
    public static final String ALID = "ALID";
    public static final String ARTIST_NAME = "ABNAME";
    public static final String YEAR = "YEAR";
    public static final String GENRE = "GENRE";
    public static final String DESCRIPTION = "DIS";
    public static final String SOD = "SOD";
    public static final String SONGS = "SONGS";

    //Initialize the shared preference variables
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_database_api);

        //to use for tablets
        AlbumFragment albumFragment = new AlbumFragment();

        //Initialize the elements that user sees
        loadingBar = findViewById(R.id.progressBar);
        artistSearch = findViewById(R.id.artistName);
        tv = findViewById(R.id.artistResultsText);
        albumsListView = (ListView)findViewById(R.id.listViewAT);

        //Set the adapter for the album ListView
        albumsListView.setAdapter( musicAdapter );

        //Initialize the shared preference variable
        sp = this.getPreferences(Context.MODE_PRIVATE);

        //Save the search bar if application is paused
        if (sp.getString("input", "")!=null) {
            artistSearch.setText(sp.getString("input", ""));
        }


        //Initialize and set onClick listener for help button
        helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(clk -> {

            //Make an alert dialog with help information when user clicks help button
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("How to use application AUDIO_DATABASE_API")
                    .setMessage("1>Type artist name\n" +
                            "2>Click on album to view songs\n" +
                            "3>Click on song to search\n" +
                            "4>Click Save while in album to save\n" +
                            "5>Click on My Songs to see saved\n" +
                            "6>Click Delete button inside Album \n" +
                            "to delete it from saved albums\n" +
                            "\n" +
                            "Application created by David Thran")
                    .setPositiveButton("OK", (click, arg) ->{
                    })
                    .create().show();
        });

        //Initialize the search button and add click listener
        search = findViewById(R.id.searchArtistButton);
        search.setOnClickListener(v -> {
            //Clear the list view before populating it
            albumsListView.removeAllViewsInLayout();
            albumsArrayList.clear();

            //Initialize the query varaible
            AlbumQuery music = new AlbumQuery();
            //Execute uml with artists name
            music.execute("https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s="+artistSearch.getText().toString().replaceAll(" ", "%20"));
            //Reset the edit text after search
            artistSearch.setText("");
        });

        //Click listener for the Album listview
        albumsListView.setOnItemClickListener((parent, view, position, id) -> {
            //Alert dialog asking if the user wants to go inside album
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Would you like to enter this Album?")
                    .setMessage("Album: " +albumsArrayList.get(position).getAlbumName())
                    .setPositiveButton("Yes", (click, arg) ->{
                        //Get Album Id and make a string with the url and idAlbum that was collected
                        String urlSecond = "https://theaudiodb.com/api/v1/json/1/track.php?m="+albumsArrayList.get(position).getIdAlbum();
                        //Initialize the song search class
                        SongSearch ss = new SongSearch();
                        //Save the position of selected album for use later
                        boat = position;
                        //execute the search function
                        ss.execute(urlSecond);
                    })
                    .setNegativeButton("No", (click, arg) -> { })
                    .create().show();
        });

        //Initialize the saved albums button and apply a click listener
        savedAlbumsButtom = findViewById(R.id.albumSeeSaved);
        savedAlbumsButtom.setOnClickListener(v ->{
            //Start saved albums activity
            Intent savedAlbumsAct = new Intent(AUDIO_DATABASE_API.this, SavedAlbums.class);
            startActivity(savedAlbumsAct);

        });

    }


    //On pause save the edit text contents with shared preference
    protected void onPause() {
        super.onPause();
        editor = sp.edit();
        editor.putString("input", artistSearch.getText().toString());
        editor.apply();

    }


    //This class is used to search for all songs inside selected album
    private class SongSearch extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... args) {
            try{
                //Initialize the URL
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //create buffer reader to read and initialize the JSON Array inside object track
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String albumSearch = sb.toString(); //result is the whole string

                JSONObject object = new JSONObject(albumSearch);//Make JSONObject for results
                if(!object.isNull("track")) {
                    JSONArray jsonArray = object.getJSONArray("track");//Make JSON object into a JSON array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Grab each object inside array and save all song names to a songs Arraylist of Strings
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


        //This method is used to fill a bundle of information and launch the see Album fragment
        //Uses boat(The position of the listview object clicked) that was saved in OnCreate
        public void onPostExecute(String fromDoInBackground) {
            //This is where the bundle will grab all needed information to pass along to the fragment once
            //do in background grabs all songs and saves them
            dataToPassDavid = new Bundle();

            //Pass all objects variables
            dataToPassDavid.putString(ALBUM_NAME, albumsArrayList.get(boat).getAlbumName());
            dataToPassDavid.putString(ARTIST_NAME, albumsArrayList.get(boat).getArtistName());
            dataToPassDavid.putInt(YEAR, albumsArrayList.get(boat).getYear());
            dataToPassDavid.putString(GENRE, albumsArrayList.get(boat).getGenre());
            dataToPassDavid.putString(DESCRIPTION, albumsArrayList.get(boat).getAlbumDis());
            dataToPassDavid.putStringArrayList(SONGS, songsArrayList);
            dataToPassDavid.putInt(ALID, albumsArrayList.get(boat).getIdAlbum());
            albumsArrayList.get(boat).setSongsInAlbum(songsArrayList);

            //This is setting the buttons text to save in fragment view
            dataToPassDavid.putString(SOD, "SAVE");

            //Starts activity to open fragment and populate new view
            Intent nextActivityFragDave = new Intent(AUDIO_DATABASE_API.this, AlbumFragmentEmpty.class);
            nextActivityFragDave.putExtras(dataToPassDavid);
            startActivity(nextActivityFragDave);

            //clear the songsArrayList for next search
            songsArrayList.clear();
        }
    }


    //Made to find all the albums made from the searched artist
    private class AlbumQuery extends AsyncTask<String, Integer, String> {
        //Making all local variables to store the values
        //until Albums object needs to be made
        public String AlbName;
        public String ArtName;
        public int idAl;
        public int year;
        public String genre;
        boolean result;
        String dis = "";

        //Variables for toast message
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;

        @Override
        protected String doInBackground(String... args) {
            try {
                //Initialize Connection
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Make buffered reader to grab the information
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                //Store the results into a string variable
                String albumSearch = sb.toString();

                //Make a JSONObject to store the results
                JSONObject object = new JSONObject(albumSearch);

                //Set progress to 0
                setProgress(0);

                //Checks to see if the object has any search results
                if(!object.isNull("album")) {
                    //Make JSONObject into an array
                    JSONArray jsonArray = object.getJSONArray("album");
                    //Loop through JSONArray
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Make a temp Object for every Object found in JSONArray
                        JSONObject JO = (JSONObject) jsonArray.get(i);

                        //Initialize the local variables by grabbing the JSONObjects contents
                        AlbName = JO.getString("strAlbum");
                        ArtName = JO.getString("strArtist");
                        idAl = JO.getInt("idAlbum");
                        year = JO.getInt("intYearReleased");
                        genre = JO.getString("strGenre");

                        //If this JSONObject has description grap it if not dont grab it
                        //Make an album object with the results
                        if(JO.has("strDescriptionEN")) {
                            dis = JO.getString("strDescriptionEN");
                            albumsArrayList.add(new Album(AlbName, ArtName, year, genre, idAl, dis));
                        }else {
                            albumsArrayList.add(new Album(AlbName, ArtName, year, genre, idAl));
                        }

                        //Sets progress to the percentage of the array that has been gone through
                        setProgress((int) ((i + 1) / jsonArray.length() * 100));
                    }
                    //Sets the text of the toast for found results and boolean for found results
                    //To true
                    text = "Search results found";
                    result = true;
                }else{
                    //Sets text of toast message to for no results and to results boolean to false
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

        //Updates the progress bar
        public void onProgressUpdate(Integer... value) {
            loadingBar.setVisibility(View.VISIBLE);
            loadingBar.setProgress(value[0]);
        }

        //Updates listView with found results and shows a toast button
        public void onPostExecute(String fromDoInBackground) {
            //Populate listview with results
            musicAdapter.notifyDataSetChanged();

            //If result found make toast and display artists name above
            //the listview of the albums, if not found dont show artist name
            //and set toast anyways
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

    //This is an adapter to inflate the ListView with another row, it is used for
    //adding the searched albums as seperate entities among the listview
   private class MusicListAdapter extends BaseAdapter {


        @Override // number of items in the list
        public int getCount() {
            return  albumsArrayList.size();
        }

        @Override // what object goes at row i
        public Object getItem(int i) {
            return albumsArrayList.get(i);
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


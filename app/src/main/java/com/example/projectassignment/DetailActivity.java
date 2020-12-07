package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {


    /**
     * delcare bitmap varaible for storing image data
     * delcare imageview variable for image view
     */
    Bitmap imageData;
    ImageView imageUrlView;

    /**
     * creating view with specifically for inclusion of image view
     * @param savedInstanceState reference to bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_fragment);
        Bundle dataToPass = getIntent().getExtras();

        //Display all these variables
        String eventName = dataToPass.getString(TicketMasterEventSearch.EVENTNAME);
        String startDate = dataToPass.getString(TicketMasterEventSearch.DATE);
        int min = dataToPass.getInt(TicketMasterEventSearch.MIN);
        int max = dataToPass.getInt(TicketMasterEventSearch.MAX);
        String ticketURL = dataToPass.getString(TicketMasterEventSearch.URL);
        String imageURL = dataToPass.getString(TicketMasterEventSearch.IMAGEURL);

        TextView eventTitle = findViewById(R.id.EventTitle);
        eventTitle.setText(eventName);

        TextView date = findViewById(R.id.StartDate);
        date.setText(startDate);

        TextView minPrice = findViewById(R.id.MinPrice);
        minPrice.setText(String.valueOf(min));

        TextView maxPrice = findViewById(R.id.MaxPrice);
        maxPrice.setText(String.valueOf(max));

        TextView url = findViewById(R.id.URL);
        url.setText(ticketURL);

        imageUrlView = findViewById(R.id.imageURL);
        new GetImageConnection().execute(imageURL);
    }

    /**
     * method to establish connection and retrieve image url to bitmap
     */

    private class GetImageConnection extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {

            String imageURL = args[0];

            if (imageURL != null && !imageURL.equals("")) {
                try {
                    URL getImageUrl = new URL(imageURL);
                    HttpURLConnection conn = (HttpURLConnection) getImageUrl.openConnection();

                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setInstanceFollowRedirects(true);

                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        imageData = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return "Done";
        }

        /**
         * set the bitmap data to actual image view
         * @param fromDoInBackground
         */
        public void onPostExecute(String fromDoInBackground) {
            if(imageData != null) imageUrlView.setImageBitmap(imageData);
        }
    }

}

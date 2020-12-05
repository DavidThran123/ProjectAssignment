package com.example.projectassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class EventFragment extends Fragment {
    private static final String TAG = "LOG";

    private AppCompatActivity parentActivityDing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle catchEventBundle = getArguments();

        //Display all these variables
        String eventName = catchEventBundle.getString(TicketMasterEventSearch.EVENTNAME);
        String startDate = catchEventBundle.getString(TicketMasterEventSearch.DATE);
        int min = catchEventBundle.getInt(TicketMasterEventSearch.MIN);
        int max = catchEventBundle.getInt(TicketMasterEventSearch.MAX);
        String ticketURL = catchEventBundle.getString(TicketMasterEventSearch.URL);

        String imageURL = catchEventBundle.getString(TicketMasterEventSearch.IMAGEURL);




        View eventOpened = inflater.inflate(R.layout.activity_event_fragment, container, false);
        TextView eventTitle = eventOpened.findViewById(R.id.EventTitle);



        eventTitle.setText(eventName);

        TextView date = eventOpened.findViewById(R.id.StartDate);
        date.setText(startDate);

        TextView minPrice = eventOpened.findViewById(R.id.MinPrice);
        minPrice.setText(String.valueOf(min));

        TextView maxPrice = eventOpened.findViewById(R.id.MaxPrice);
        maxPrice.setText(String.valueOf(max));

        TextView url = eventOpened.findViewById(R.id.URL);
        url.setText(ticketURL);




        return eventOpened;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivityDing = (AppCompatActivity)context;
    }


}
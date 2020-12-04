package com.example.projectassignment;

import android.graphics.Bitmap;

import java.util.Date;

public class Event {


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    String EventName;

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    String dateOfEvent;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    int max;
    String info;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
    Image image;

    Bitmap imageData;

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    public Bitmap getImageData() {
        return imageData;
    }

    Event(String EventName, String dateOfEvent, int min, int max, String info, String url, Image image){
    this.EventName = EventName;
    this.dateOfEvent = dateOfEvent;
    this.min = min;
    this.max = max;
    this.info = info;
    this.url = url;
    this.image = image;
}

    public String getEventName() {
        return EventName;
    }
}

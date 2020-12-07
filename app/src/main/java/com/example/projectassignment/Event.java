package com.example.projectassignment;

import android.graphics.Bitmap;

import java.util.Date;

public class Event {

    /**
     * method to set event ID
     * @param eventID, to be passed to parameter and setted
     */
    public void setEventID(int eventID) {
        EventID = eventID;
    }

    /**
     * method to get event ID
     * @return EventID, integer values
     */
    public int getEventID() {
        return EventID;
    }

    /**
     *
    declared int value to store event ID
     */
    public int EventID;

    /**
     * method get get Image object
     * @return an image object
     */

    public Image getImage() {
        return image;
    }

    /**
     * method to set the image object
     * @param image passed in an image object in to parameter
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * method to set the event name
     * @param eventName a string value passed to parameter and to be setted
     */
    public void setEventName(String eventName) {
        EventName = eventName;
    }

    /**
     * declared a string to store event name
     */
    String EventName;

    /**
     * method to get the event date
     * @return the date of event varaible
     */
    public String getDateOfEvent() {
        return dateOfEvent;
    }

    /**
     * method to set the event date
     * @param dateOfEvent the string varaible passed to set the date of event
     */
    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    /**
     * declared string variable to store date of event
     */
    String dateOfEvent;

    /**
     * method to get the minimum price
     * @return min variable that stores minimum price
     */
    public int getMin() {
        return min;
    }

    /**
     * method to set the minimum price
     * @param min value that passed to parameter to set minimum price
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * declared int variable to store the minimum price
     */
    int min;

    /**
     * method to get the maximum price
     * @return int value that stores max price
     */
    public int getMax() {
        return max;
    }

    /**
     * method to set the max price
     * @param max value passed to set the max price
     */

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * varaibles declared to store max price
     */
    int max;

    /**
     * method to get the url
     * @return url value
     */
    public String getUrl() {
        return url;
    }

    /**
     * method to set url
     * @param url value passed to set the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * declared variable to store url, and image object
     */
    String url;
    Image image;

    /**
     * declared variable to sotre bitmap variable
     */
    Bitmap imageData;

    /**
     * method to set the image data
     * @param imageData the image data passed to parameter to set the image data
     */
    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    /**
     * method to get the image data
     * @return the varaible that stores image data
     */
    public Bitmap getImageData() {
        return imageData;
    }

    /**
     * contructor for making event object
     * @param EventName the event name passed to parameter
     * @param dateOfEvent the date of event passed to parameter
     * @param min the minimum price passed to parameter
     * @param max the maximum price passed to parameter
     * @param url the url passed to parameter
     * @param image the image object passed to parameter
     */

    Event(String EventName, String dateOfEvent, int min, int max, String url, Image image){
    this.EventName = EventName;
    this.dateOfEvent = dateOfEvent;
    this.min = min;
    this.max = max;
    this.url = url;
    this.image = image;
}

    /**
     * contructor for making event object with one added parameter chained
     * @param EventName the event name passed to parameter
     * @param dateOfEvent the date of event passed to parameter
     * @param min the minimum price passed to parameter
     * @param max the maximum price passed to parameter
     * @param url the url passed to parameter
     * @param image the image object passed to parameter
     * @param id the event id passed to parameter
     */
    Event(String EventName, String dateOfEvent, int min, int max, String url, Image image, int id){
        this.EventName = EventName;
        this.dateOfEvent = dateOfEvent;
        this.min = min;
        this.max = max;
        this.url = url;
        this.image = image;
        this.EventID = id;
    }

    /**
     * method to get the event name
     * @return event name variable
     */
    public String getEventName() {
        return EventName;
    }
}

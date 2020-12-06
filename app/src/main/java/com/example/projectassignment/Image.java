package com.example.projectassignment;

public class Image {

    /**
     *
     */
    //declared variable for storing image URL as string
    String URL;

    /**
     * method to get image url
     * @return
     */
    public String getURL() {
        return URL;
    }

    /**
     * constructor to set the url
     * @param URL url varaible passed into the parameter to set
     */
    Image(String URL){
        this.URL = URL;
    }

}

package com.example.projectassignment;

import java.lang.reflect.Array;
import java.util.ArrayList;

//This class is used to store album details and song names into a single object
public class Album {
    //Class Variables
    private String AlbumName;
    private String ArtistName;
    private ArrayList<String> songsInAlbum;
    private int idAlbum;
    private int year;
    private long idAl;
    private String albumDis;
    private String genre;

    //Constructor
    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum, String albumDis){
    this.AlbumName = AlbumName;
    this.ArtistName = ArtistName;
    this.year = year;
    this.genre = genre;
    this.idAlbum = idAlbum;
    this.albumDis = albumDis;
    }

    //Constructor with arraylist of songs added
    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum, String albumDis, ArrayList<String> aLS){
        this.AlbumName = AlbumName;
        this.ArtistName = ArtistName;
        this.year = year;
        this.genre = genre;
        this.idAlbum = idAlbum;
        this.albumDis = albumDis;
        this.songsInAlbum = aLS;
    }

    //Constructor without album description given
    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum){
        this.AlbumName = AlbumName;
        this.ArtistName = ArtistName;
        this.year = year;
        this.genre = genre;
        this.idAlbum = idAlbum;
        this.albumDis = "";
    }

    //Setters and getters for object variables
    public String getAlbumName() {
        return AlbumName;
    }
    public String getGenre() {
        return genre;
    }
    public int getYear() {
        return year;
    }
    public int getIdAlbum() {
        return idAlbum;
    }
    public String getArtistName() {
        return ArtistName;
    }
    public String getAlbumDis() {
        return albumDis;
    }
    public ArrayList<String> getSongsInAlbum() {
        return songsInAlbum;
    }
    public void setSongsInAlbum(ArrayList<String> songsInAlbum) {
        this.songsInAlbum = songsInAlbum;
    }
}

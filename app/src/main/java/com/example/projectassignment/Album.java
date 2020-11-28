package com.example.projectassignment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Album {
    private String AlbumName;
    private String ArtistName;
    private ArrayList<String> songsInAlbum;
    private int idAlbum;
    private int year;

    public long getIdAl() {
        return idAl;
    }

    public void setIdAl(long idAl) {
        this.idAl = idAl;
    }

    private long idAl;
    private String albumDis;
    private String genre;

    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum, String albumDis){
    this.AlbumName = AlbumName;
    this.ArtistName = ArtistName;
    this.year = year;
    this.genre = genre;
    this.idAlbum = idAlbum;
    this.albumDis = albumDis;
    }

    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum, String albumDis, ArrayList<String> aLS){
        this.AlbumName = AlbumName;
        this.ArtistName = ArtistName;
        this.year = year;
        this.genre = genre;
        this.idAlbum = idAlbum;
        this.albumDis = albumDis;
        this.songsInAlbum = aLS;
    }

    public Album(String AlbumName, String ArtistName,  int year, String genre, int idAlbum){
        this.AlbumName = AlbumName;
        this.ArtistName = ArtistName;
        this.year = year;
        this.genre = genre;
        this.idAlbum = idAlbum;
        this.albumDis = "";
    }

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

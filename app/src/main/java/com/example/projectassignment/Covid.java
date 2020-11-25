package com.example.projectassignment;

import java.util.ArrayList;

public class Covid {
    private String country;
    private String province;
    private int caseNumber;
    private String dateNumber;
    private long id;
    private ArrayList<String> countryInDB;

    public Covid(long id,String country,String province,int caseNumber,String dateNumber){
        this.id = id;
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.dateNumber = dateNumber;
    }
    public Covid(String country,String province,int caseNumber,String dateNumber){
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.dateNumber = dateNumber;
    }

    public String getCountry(){
        return country;
    }

    public long getId(){
        return id;
    }

    public String getProvince(){
        return province;
    }

    public int getCaseNumber(){
        return caseNumber;
    }

    public String getDateNumber(){
        return dateNumber;
    }

}

package com.example.projectassignment;

import java.util.ArrayList;

public class Covid {
    private String country;
    private String province;
    private int caseNumber;
    private String startDate;
    private long id;
    private String countryCode;
    private ArrayList<String> countryInDB;

    public Covid(long id,String countryCode,String country,String province,int caseNumber,String startDate){
        this.id = id;
        this.countryCode = countryCode;
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.startDate = startDate;

    }
    public Covid(String countryCode,String country,String province,int caseNumber,String startDate){
        this.countryCode = countryCode;
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.startDate = startDate;
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

    public String getStartDate(){
        return startDate;
    }

    public String getCountryCode(){return countryCode;}

}

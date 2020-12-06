package com.example.projectassignment;

/**
 *class used to store and create Covid object
 */
public class Covid {
    private String country;
    private String province;
    private String caseNumber;
    private String startDate;
    private long id;
    private String countryCode;

    /**
     * constructor used to accept long id, String countryCode,String country,String province,String caseNumber,String startDate
     * @param id
     * @param countryCode
     * @param country
     * @param province
     * @param caseNumber
     * @param startDate
     */
    public Covid(long id,String countryCode,String country,String province,String caseNumber,String startDate){
        this.id = id;
        this.countryCode = countryCode;
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.startDate = startDate;
    }

    /**
     * constructor use to accept String countryCode,String country,String province,String caseNumber,String startDate
     * @param countryCode
     * @param country
     * @param province
     * @param caseNumber
     * @param startDate
     */
    public Covid(String countryCode,String country,String province,String caseNumber,String startDate){
        this.countryCode = countryCode;
        this.country = country;
        this.province = province;
        this.caseNumber = caseNumber;
        this.startDate = startDate;
    }

    /**
     *Returns the country of this {@code Covid}.
     * @return A reference to the string containing country stored in this class
     */
    public String getCountry(){
        return country;
    }

    /**
     * Returns the id of this {@code Covid}
     * @return A reference to the string containing id stored in this class
     */
    public long getId(){
        return id;
    }

    /**
     * Returns the province of this {@code Covid}
     * @return A reference to the string containing province stored in this class
     */
    public String getProvince(){
        return province;
    }

    /**
     * Returns the caseNumber of this {@code Covid}
     * @return A reference to the string containing caseNumber stored in this class
     */
    public String getCaseNumber(){
        return caseNumber;
    }

    /**
     * Returns the startDate of this {@code Covid}
     * @return A reference to the string containing startDate stored in this class
     */
    public String getStartDate(){
        return startDate;
    }

    /**
     * Returns the countryCode of this {@code Covid}
     * @return A reference to the string containing countryCode stored in this class
     */
    public String getCountryCode(){return countryCode;}

}

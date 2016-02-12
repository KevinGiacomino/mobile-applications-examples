package com.octo.franceconnectpoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable {

    @SerializedName("sub")
    @Expose
    private String sub;
    @SerializedName("given_name")
    @Expose
    private String givenName;
    @SerializedName("family_name")
    @Expose
    private String familyName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("birthplace")
    @Expose
    private String birthplace;
    @SerializedName("birthcountry")
    @Expose
    private int birthcountry;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private Address address;

    private String stringBirthcountry;
    private String stringBirthplace;

    /**
     *
     * @return
     * The sub
     */
    public String getSub() {
        return sub;
    }

    /**
     *
     * @param sub
     * The sub
     */
    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     *
     * @return
     * The givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     *
     * @param givenName
     * The given_name
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     *
     * @return
     * The familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     *
     * @param familyName
     * The family_name
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     *
     * @param birthdate
     * The birthdate
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     *
     * @return
     * The birthplace
     */
    public String getBirthplace() {
        return birthplace;
    }

    /**
     *
     * @param birthplace
     * The birthplace
     */
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    /**
     *
     * @return
     * The birthcountry
     */
    public int getBirthcountry() {
        return birthcountry;
    }

    /**
     *
     * @param birthcountry
     * The birthcountry
     */
    public void setBirthcountry(int birthcountry) {
        this.birthcountry = birthcountry;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }


    public String getStringBirthcountry() {
        return stringBirthcountry;
    }

    public void setStringBirthcountry(String stringBirthcountry) {
        this.stringBirthcountry = stringBirthcountry;
    }

    public String getStringBirthplace() {
        return stringBirthplace;
    }

    public void setStringBirthplace(String stringBirthplace) {
        this.stringBirthplace = stringBirthplace;
    }
}
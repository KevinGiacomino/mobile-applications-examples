package com.octo.franceconnectpoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lda on 19/11/2015.
 */
public class CogPays {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("libelle")
    @Expose
    private String libelle;

    /**
     * @return The code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return The libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * @param libelle The libelle
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

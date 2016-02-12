package com.octo.franceconnectpoc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lda on 19/11/2015.
 */
public class CogVille {

    @SerializedName("NCC")
    @Expose
    private String libelle;
    @SerializedName("CODE")
    @Expose
    private String code;

    /**
     *
     * @return
     * The libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     *
     * @param libelle
     * The libelle
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }
}

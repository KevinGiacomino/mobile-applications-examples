package com.octo.franceconnectpoc.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import com.octo.franceconnectpoc.model.CogPays;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lda on 19/11/2015.
 */
public final class CogCountryFetcher {

    private CogCountryFetcher() {
    }

    public static String fetch(Context context, int countryCode) throws IOException {

        Gson gson = new Gson();
        InputStream inputStream = context.getAssets().open("cog-pays.json");
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        String countryName = null;

        reader.beginArray();
        while (reader.hasNext()) {
            CogPays pays = gson.fromJson(reader, CogPays.class);
            if (pays.getCode() == countryCode) {
                countryName = pays.getLibelle();
                break;
            }
        }
        reader.close();

        return countryName;
    }
}

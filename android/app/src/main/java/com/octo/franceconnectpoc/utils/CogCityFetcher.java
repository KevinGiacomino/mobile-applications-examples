package com.octo.franceconnectpoc.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import com.octo.franceconnectpoc.model.CogVille;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lda on 19/11/2015.
 */
public final class CogCityFetcher {

    private CogCityFetcher() {
    }

    public static String fetch(Context context, String cityCode) throws IOException {

        Gson gson = new Gson();
        InputStream inputStream = context.getAssets().open("cog-ville.json");
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        String cityName = null;

        reader.beginArray();
        while (reader.hasNext()) {
            CogVille ville = gson.fromJson(reader, CogVille.class);
            if (cityCode.equals(ville.getCode())) {
                cityName = ville.getLibelle();
                break;
            }
        }
        reader.close();

        return cityName;
    }
}

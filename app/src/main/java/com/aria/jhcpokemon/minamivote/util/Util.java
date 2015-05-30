package com.aria.jhcpokemon.minamivote.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.aria.jhcpokemon.minamivote.model.Character;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by jhcpokemon on 05/28/15.
 */
public class Util {
    public static List<com.aria.jhcpokemon.minamivote.model.Character> getCharacterList(Context context) {
        String jsonData = getJsonData(context);
        return JSON.parseArray(jsonData, Character.class);
    }

    public static String getJsonData(Context context) {
        StringBuilder result = new StringBuilder();
        String line;
        try {
            InputStream in = context.getResources().getAssets().open("source.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}

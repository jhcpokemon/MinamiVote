package com.aria.jhcpokemon.minamivote.model;

import android.content.Context;

/**
 * Created by jhcpokemon on 05/27/15.
 */
public class Character {
    private String id;
    private String name;
    private String cv;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId(Context context){
        try {
            return context.getResources().getIdentifier("avatar_"+id,"drawable",context.getPackageName());
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}

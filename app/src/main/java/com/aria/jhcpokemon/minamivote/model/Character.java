package com.aria.jhcpokemon.minamivote.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jhcpokemon on 05/27/15.
 */
public class Character implements Parcelable {
    private String id;
    private String name;
    private String cv;
    private String description;

    public Character(){}

    public Character(String id,String name,String cv,String description){
        this.id = id;
        this.name = name;
        this.cv = cv;
        this.description = description;
    }

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


    public int getImgId(Context context) {
        try {
            return context.getResources().getIdentifier("avatar_" + id, "drawable", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(cv);
        parcel.writeString(description);
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source.readString(),source.readString(),source.readString(),source.readString());
        }

        @Override
        public Character[] newArray(int i) {
            return new Character[0];
        }
    };
}

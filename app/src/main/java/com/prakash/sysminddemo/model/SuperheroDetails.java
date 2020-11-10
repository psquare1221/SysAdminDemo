package com.prakash.sysminddemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SuperheroDetails implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "character_name")
    private String character_name;

    @ColumnInfo(name = "character_desc")
    private String character_desc;

    @ColumnInfo(name = "photo_uri")
    private String photo_uri;


    /*
     * Getters and Setters
     * */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public String getCharacter_desc() {
        return character_desc;
    }

    public void setCharacter_desc(String character_desc) {
        this.character_desc = character_desc;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }
}

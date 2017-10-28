package com.febaisi.deezeralbumsfetch.model;

public class Artist {

    /**
     * Varibles were named following the API Json fields.
     * In the future we may use GSON to convert json data straight to Objects.
     */
    private int id;
    private String name;
    private String picture_xl;
    private String tracklist;

    /** Getters and Setters **/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_xl() {
        return picture_xl;
    }

    public void setPicture_xl(String picture_xl) {
        this.picture_xl = picture_xl;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }
}

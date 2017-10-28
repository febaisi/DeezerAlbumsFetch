package com.febaisi.deezeralbumsfetch.model;

public class Album {

    /**
     * Varibles were named following the API Json fields.
     * In the future we may use GSON to convert json data straight to Objects.
     */
    private int id;
    private String title;
    private String link;
    private String cover_small;
    private String cover_medium;
    private String cover_xl;
    private String release_date;
    private Artist artist;

    /** Getters and Setters **/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public void setCover_medium(String cover_medium) {
        this.cover_medium = cover_medium;
    }

    public String getCover_xl() {
        return cover_xl;
    }

    public void setCover_xl(String cover_xl) {
        this.cover_xl = cover_xl;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}

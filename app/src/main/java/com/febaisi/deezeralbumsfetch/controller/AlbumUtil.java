package com.febaisi.deezeralbumsfetch.controller;

import com.febaisi.deezeralbumsfetch.objects.Album;
import com.febaisi.deezeralbumsfetch.objects.Artist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumUtil {

    /**
     * Parsing the API result into a List of Albums since we can't use GSON
     * in this project.
     *
     * @param albumsJsonResult  | The String JSON that comes from the API
     * @return a List Object of all of Albums (with Artists included)
     * @throws JSONException
     */
    public static List<Album> getAlbumsObjectList(String albumsJsonResult) throws JSONException {
        JSONArray albumsArray = new JSONObject(albumsJsonResult).getJSONArray("data");
        List<Album> albumList = new ArrayList<>();

        for (int i=0 ; i<albumsArray.length(); i++) {
            JSONObject albumJsonObject = albumsArray.getJSONObject(i);
            albumList.add(AlbumUtil.parseAlbumJsonToObject(albumJsonObject));
        }

        return albumList;
    }

    /**
     * @param albumJsonObject
     * Parsing the json data album to an Album object.
     * GSON would get this work done easily, but we are trying to avoid
     * the usage of 3rd libraries as much as possible
     * @return an Album object
     * @throws JSONException
     */
    private static Album parseAlbumJsonToObject(JSONObject albumJsonObject) throws JSONException {
        Album album = new Album();
        album.setId(albumJsonObject.getInt("id"));
        album.setTitle(albumJsonObject.getString("title"));
        album.setLink(albumJsonObject.getString("link"));
        album.setCover_xl(albumJsonObject.getString("cover_xl"));
        album.setRelease_date(albumJsonObject.getString("release_date"));
        album.setArtist(parseArtistJsonToObject(albumJsonObject.getJSONObject("artist")));
        return album;
    }

    /**
     * @param artistJsonObject
     * Parsing the json data artist to an Artist object.
     * GSON library would get this work done easily, but we are trying to avoid
     * the usage of 3rd libraries as much as possible
     * @return an Artist object
     * @throws JSONException
     */
    private static Artist parseArtistJsonToObject(JSONObject artistJsonObject) throws JSONException {
        Artist artist = new Artist();
        artist.setId(artistJsonObject.getInt("id"));
        artist.setName(artistJsonObject.getString("name"));
        artist.setPicture_xl(artistJsonObject.getString("picture_xl"));
        artist.setTracklist(artistJsonObject.getString("tracklist"));
        return artist;
    }
}

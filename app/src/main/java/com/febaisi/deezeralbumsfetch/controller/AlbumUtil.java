package com.febaisi.deezeralbumsfetch.controller;

import com.febaisi.deezeralbumsfetch.model.Album;
import com.febaisi.deezeralbumsfetch.model.Artist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        album.setCover(albumJsonObject.getString("cover"));
        album.setCover_small(albumJsonObject.getString("cover_small"));
        album.setCover_medium(albumJsonObject.getString("cover_medium"));
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

    /**
     * Return the cover id along with the image size as an ID.
     * We can't rely on images URL as an ID because the base web service may change at some point
     * @param coverUrl
     * @return album cover id along with the image size
     */
    public static String getCoverId(String coverUrl) {
        String result = "";

        Pattern LEGAL_KEY_PATTERN = Pattern.compile("(cover|artist)\\/(.+?)-");
        Matcher matcher = LEGAL_KEY_PATTERN.matcher(coverUrl);
        if (matcher.find()) {
            result = matcher.group(2);
            result = result.replace("/", "");
        }

        return result;
    }

}

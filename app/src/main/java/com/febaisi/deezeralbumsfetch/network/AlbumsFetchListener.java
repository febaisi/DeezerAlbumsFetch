package com.febaisi.deezeralbumsfetch.network;

/**
 * Created by felipebaisi on 10/25/17.
 */

public interface AlbumsFetchListener {
    void onAlbumsFetched(String albumsResult);
    void onAlbumsFetchFail(String failResult);
}

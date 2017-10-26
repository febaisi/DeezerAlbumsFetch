package com.febaisi.deezeralbumsfetch.network;

public interface AlbumsFetchListener {
    void onAlbumsFetched(String albumsResult);
    void onAlbumsFetchFail(String failResult);
}

package com.febaisi.deezeralbumsfetch.controller;

import com.febaisi.deezeralbumsfetch.model.Album;

import java.util.List;

public interface OrderedAlbumListRequestListener {
    void onSuccess(List<Album> orderedAlbumList);
    void onError(String userErrorWarnign);
}

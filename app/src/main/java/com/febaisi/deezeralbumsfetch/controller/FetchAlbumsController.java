package com.febaisi.deezeralbumsfetch.controller;

import android.content.Context;

import com.febaisi.deezeralbumsfetch.network.FetchAlbumsAsyncTask;

public class FetchAlbumsController {

    private Context mContext;

    public FetchAlbumsController (Context context) {
        this.mContext = context;
    }

    public void fetchAlbums() {
        new FetchAlbumsAsyncTask(mContext, null).execute();
    }
}

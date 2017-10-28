package com.febaisi.deezeralbumsfetch.controller;

import android.content.Context;

import com.febaisi.deezeralbumsfetch.network.AlbumsFetchListener;
import com.febaisi.deezeralbumsfetch.network.FetchAlbumsAsyncTask;
import com.febaisi.deezeralbumsfetch.model.Album;

import org.json.JSONException;

import java.util.List;

public class FetchAlbumsController {

    private Context mContext;

    public FetchAlbumsController (Context context) {
        this.mContext = context;
    }

    public void fetchAlbums(final OrderedAlbumListRequestListener orderedAlbumListRequestListener) {
        new FetchAlbumsAsyncTask(new AlbumsFetchListener() {
            @Override
            public void onAlbumsFetched(String albumsResult) {
                try {
                    List<Album> albumsList = AlbumUtil.getAlbumsObjectList(albumsResult);

                    if (orderedAlbumListRequestListener!=null) {
                        orderedAlbumListRequestListener.onSuccess(albumsList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAlbumsFetchFail(String failResult) {
            }

        }).execute();
    }

}

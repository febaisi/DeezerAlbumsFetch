package com.febaisi.deezeralbumsfetch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.controller.FetchAlbumsController;
import com.febaisi.deezeralbumsfetch.controller.OrderedAlbumListRequestListener;
import com.febaisi.deezeralbumsfetch.model.Album;
import com.febaisi.deezeralbumsfetch.widgethelper.AlbumRowBuilder;
import com.febaisi.deezeralbumsfetch.widgethelper.WidgetUtil;

import java.util.List;

public class AlbumsFragment extends CustomFragment {

    private static String ALBUMS_FRAG_TAG = "ALBUMS_FRAG_TAG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Declare and populate final variables
        final View view = inflater.inflate(R.layout.fragment_albums, container, false);
        final ProgressBar progressBar = view.findViewById(R.id.album_load_spinner);
        final GridLayout gridLayout = view.findViewById(R.id.album_grid_layout);

        FetchAlbumsController fetchAlbumsController = new FetchAlbumsController(getActivity());
        fetchAlbumsController.fetchAlbums(new OrderedAlbumListRequestListener() {
            @Override
            public void onSuccess(List<Album> orderedAlbumList) {
                progressBar.setVisibility(View.GONE);
                gridLayout.setVisibility(View.VISIBLE);
                gridLayout.setColumnCount(1);

                for (Album album : orderedAlbumList) {
                    AlbumRowBuilder albumRowBuilder = new AlbumRowBuilder(getContext(), gridLayout, album.getCover_medium(), album.getTitle(), album.getArtist().getName());
                    gridLayout.addView(albumRowBuilder.buildRow());
                }
            }

            @Override
            public void onError(String userErrorWarnign) {

            }
        });


        return view;
    }

    @Override
    public String getCustomTag() {
        return ALBUMS_FRAG_TAG;
    }
}

package com.febaisi.deezeralbumsfetch.widgethelper;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.components.CoverDownloaderImageView;
import com.febaisi.deezeralbumsfetch.model.Album;

public class AlbumRowBuilder {

    private GridLayout mGridLayout;
    private Activity mActivity;
    private Album mAlbum;


    public AlbumRowBuilder(Activity activity, GridLayout gridLayout, Album album) {
        this.mActivity = activity;
        this.mGridLayout = gridLayout;
        this.mAlbum = album;
    }

    public View buildRow() {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.album_grid_row, mGridLayout, false);
        ((TextView) v.findViewById(R.id.album_row_album_title)).setText(mAlbum.getTitle());
        ((TextView) v.findViewById(R.id.album_row_artist_title)).setText(mAlbum.getArtist().getName());

        final CoverDownloaderImageView coverDownloaderImageView = v.findViewById(R.id.album_row_cover_image);
        coverDownloaderImageView.setActivity(mActivity);
        coverDownloaderImageView.setAlbumTitle(mAlbum.getTitle());
        coverDownloaderImageView.downloadCover(false, mAlbum.getCover_medium());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Download Artist - Task with high priority added", Toast.LENGTH_LONG).show();
                coverDownloaderImageView.downloadCover(true, mAlbum.getArtist().getPicture_xl());
            }
        });

        return v;
    }
}
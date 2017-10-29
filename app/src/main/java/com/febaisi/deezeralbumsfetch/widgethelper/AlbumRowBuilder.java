package com.febaisi.deezeralbumsfetch.widgethelper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.components.CoverDownloaderImageView;

public class AlbumRowBuilder {

    private Context mContext;
    private GridLayout mGridLayout;
    private String mCoverUrl;
    private String mAlbumName;
    private String mArtistName;


    public AlbumRowBuilder(Context context, GridLayout gridLayout, String coverUrl, String albumName, String artistName) {
        this.mContext = context;
        this.mGridLayout = gridLayout;
        this.mCoverUrl = coverUrl;
        this.mAlbumName = albumName;
        this.mArtistName = artistName;
    }

    public View buildRow() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.album_grid_row, mGridLayout, false);
        ((TextView) v.findViewById(R.id.album_row_album_title)).setText(mAlbumName);
        ((TextView) v.findViewById(R.id.album_row_artist_title)).setText(mArtistName);
        ((CoverDownloaderImageView) v.findViewById(R.id.album_row_cover_image)).setImageUrl(mCoverUrl);
        return v;
    }
}
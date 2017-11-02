package com.febaisi.deezeralbumsfetch.widgethelper;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.components.CoverDownloaderImageView;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.DefaultExecutorSupplier;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.Priority;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.PriorityRunnable;

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

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e( "baisi", "ARTIST CLICKED!!");
                DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                        .submit(new PriorityRunnable(Priority.IMMEDIATE) {
                            @Override
                            public void run() {
                                // do some background work here at high priority.
                                try {
                                    Log.e( "baisi", "fetching artist! AEEEEE!!");
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

        return v;
    }
}
package com.febaisi.deezeralbumsfetch.widgethelper;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.febaisi.deezeralbumsfetch.R;

public class AlbumDetailsViewBuilder {

    private Context mContext;
    private String mAlbumName;
    private String mArtistName;

    public AlbumDetailsViewBuilder(Context context, String albumName, String artistName) {
        this.mContext = context;

        if (albumName != null && !albumName.isEmpty()) {
            this.mAlbumName = albumName;
        } else {
            this.mAlbumName = context.getResources().getString(R.string.default_album_name);
        }

        if (artistName != null && !artistName.isEmpty()) {
            this.mArtistName = albumName;
        } else {
            this.mArtistName = context.getResources().getString(R.string.default_artist_name);
        }
    }

    public LinearLayout buildAlbumDetailsView() {
        //Creating Album and Artist TextView
        TextView albumNameTextView = WidgetUtil.createAlbumDetailTextView(mContext, mAlbumName, true);
        TextView artistNameTextView = WidgetUtil.createAlbumDetailTextView(mContext, mArtistName, false);

        //Adding both TVs to place them in only one grid as a LinearLayout
        LinearLayout linearLayout = WidgetUtil.createCustomLinearLayout(mContext);
        linearLayout.addView(albumNameTextView, WidgetUtil.getTvParams(mContext));
        linearLayout.addView(artistNameTextView, WidgetUtil.getTvParams(mContext));

        return linearLayout;
    }

}

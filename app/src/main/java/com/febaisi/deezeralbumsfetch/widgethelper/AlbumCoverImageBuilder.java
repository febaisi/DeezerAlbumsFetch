package com.febaisi.deezeralbumsfetch.widgethelper;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

public class AlbumCoverImageBuilder {

    private String mImageUrl;
    private Context mContext;

    public AlbumCoverImageBuilder(Context context, String imageUrl) {
        this.mContext = context;
        this.mImageUrl = imageUrl;
    }

    public RelativeLayout buildAlbumCoverView() {
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(layoutParams);

        relativeLayout.addView(new AlbumDefaultImageView(mContext));
        relativeLayout.addView(new CoverDownloaderImageView(mContext, mImageUrl));
        return relativeLayout;
    }
}

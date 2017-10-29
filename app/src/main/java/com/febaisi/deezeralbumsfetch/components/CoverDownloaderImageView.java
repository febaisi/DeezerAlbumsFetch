package com.febaisi.deezeralbumsfetch.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.network.DownloadCoverImageTask;
import com.febaisi.deezeralbumsfetch.network.ImageDownloadListener;


public class CoverDownloaderImageView extends AppCompatImageView {

    private String mImageUrl;
    private Context mContext;

    public CoverDownloaderImageView(Context context, String imageUrl) {
        super(context);
        this.mContext = context;
        this.mImageUrl = imageUrl;
        downloadCover();
    }


    public CoverDownloaderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Default custom configs
        setVisibility(View.INVISIBLE);
    }

    private void downloadCover() {
        new DownloadCoverImageTask(mContext.getApplicationContext(), mImageUrl, new ImageDownloadListener() {
            @Override
            public void onDrawableAvailable(Drawable drawable) {
                setRealAlbumCover(drawable);
            }

            @Override
            public void onRequestFail(String failResult) {

            }
        }).execute();
    }

    private void setRealAlbumCover(final Drawable drawable) {
        setImageDrawable(drawable);
        int imagePixels = getResources().getDimensionPixelSize(R.dimen.album_image_size);
        getLayoutParams().height = imagePixels;
        getLayoutParams().width = imagePixels;

        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_fade_in);
        fadeInAnimation.setFillAfter(true);
        startAnimation(fadeInAnimation);
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
        downloadCover();
    }

}
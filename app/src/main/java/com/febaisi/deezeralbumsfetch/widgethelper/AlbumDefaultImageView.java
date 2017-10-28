package com.febaisi.deezeralbumsfetch.widgethelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.febaisi.deezeralbumsfetch.R;

public class AlbumDefaultImageView extends AppCompatImageView {

    public AlbumDefaultImageView(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Default custom configs
        setImageResource(R.drawable.placeholder);
        setBackgroundColor(Color.LTGRAY);

        int imagePixels = getResources().getDimensionPixelSize(R.dimen.default_album_image_size);
        getLayoutParams().height = imagePixels;
        getLayoutParams().width = imagePixels;
    }

}
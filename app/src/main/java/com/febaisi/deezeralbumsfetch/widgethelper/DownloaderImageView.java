package com.febaisi.deezeralbumsfetch.widgethelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;

import com.febaisi.deezeralbumsfetch.R;

public class DownloaderImageView extends AppCompatImageView {

    public DownloaderImageView(Context context) {
        super(context);
        setImageResource(R.drawable.default_album_image);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Default custom configs
        setImageResource(R.drawable.default_album_image);
        setBackgroundColor(Color.LTGRAY);

        int imagePixels = getResources().getDimensionPixelSize(R.dimen.default_album_image_size);
        getLayoutParams().height = imagePixels;
        getLayoutParams().width = imagePixels;
    }

}
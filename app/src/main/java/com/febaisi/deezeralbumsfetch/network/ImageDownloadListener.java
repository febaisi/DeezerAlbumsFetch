package com.febaisi.deezeralbumsfetch.network;

import android.graphics.drawable.Drawable;

public interface ImageDownloadListener {
    void onDrawableAvailable(Drawable drawable);
    void onRequestFail(String failResult);
}

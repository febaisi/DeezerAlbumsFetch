package com.febaisi.deezeralbumsfetch.network;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.febaisi.deezeralbumsfetch.MainActivity;
import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.cache.ImagesCache;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.Priority;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.PriorityRunnable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImagePriorityRunnable extends PriorityRunnable {

    private boolean mIsArtist;
    private Context mContext;
    private String mCoverUrl;
    private String mAlbumTitle;
    private ImageDownloadListener mImageDownloadListener;
    private ImagesCache mImagesCache;


    public DownloadImagePriorityRunnable(Priority priority, boolean isArtist, Context context, String albumTitle, String coverUrl, ImageDownloadListener imageDownloadListener) {
        super(priority);
        this.mIsArtist = isArtist;
        this.mContext = context;
        this.mCoverUrl = coverUrl;
        this.mAlbumTitle = albumTitle; // Used to debug
        this.mImageDownloadListener = imageDownloadListener;
        mImagesCache = ImagesCache.getInstance();
    }

    @Override
    public void run() {
        try {

            Bitmap bMap = mImagesCache.getImageFromWarehouse(mCoverUrl);

            if (bMap != null) {
                //Image loaded from cache. No need to store it in cache
                Log.i(MainActivity.APP_TAG, "Image loaded from chace.");
                notifyListener(false, bMap);
            } else {
                Thread.sleep(4000);

                URL url = new URL(mCoverUrl);
                InputStream in = url.openStream();
                BufferedInputStream buf = new BufferedInputStream(in);
                bMap = BitmapFactory.decodeStream(buf);

                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                notifyListener(true, bMap);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(MainActivity.APP_TAG, "MainformedUrl");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(MainActivity.APP_TAG, "IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void notifyListener(boolean storeInCache, Bitmap bitmapResult) {
        if (bitmapResult != null) {
            if (storeInCache) {
                mImagesCache.addImageToWarehouse(mCoverUrl, bitmapResult);
            }
            if (mImageDownloadListener!=null) {
                mImageDownloadListener.onDrawableAvailable(mIsArtist, new BitmapDrawable(mContext.getResources(), bitmapResult));
            } else {
                mImageDownloadListener.onRequestFail(mContext.getString(R.string.cover_fetch_fail));
            }
        }
    }

}

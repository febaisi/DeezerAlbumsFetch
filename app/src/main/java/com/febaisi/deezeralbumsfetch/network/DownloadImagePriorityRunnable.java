package com.febaisi.deezeralbumsfetch.network;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.febaisi.deezeralbumsfetch.MainActivity;
import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.cache.DiskLruImageCache;
import com.febaisi.deezeralbumsfetch.cache.MemImageCache;
import com.febaisi.deezeralbumsfetch.controller.AlbumUtil;
import com.febaisi.deezeralbumsfetch.fragments.ConfigFragment;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.Priority;
import com.febaisi.deezeralbumsfetch.network.threadpoolmanagement.PriorityRunnable;
import com.febaisi.deezeralbumsfetch.sharedpreference.SharedPreferenceUtil;

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
    private String mCoverId;
    private ImageDownloadListener mImageDownloadListener;
    private MemImageCache mMemImageCache;
    private DiskLruImageCache mDiskLruImageCache;


    public DownloadImagePriorityRunnable(Priority priority, boolean isArtist, Context context, String albumTitle, String coverUrl, ImageDownloadListener imageDownloadListener) {
        super(priority);
        this.mIsArtist = isArtist;
        this.mContext = context;
        this.mCoverUrl = coverUrl;
        this.mAlbumTitle = albumTitle; // Used to debug
        this.mImageDownloadListener = imageDownloadListener;
        this.mCoverId = AlbumUtil.getCoverId(mCoverUrl);
    }

    @Override
    public void run() {
        try {

            Bitmap bitmap;

            //Checking cache type
            if (SharedPreferenceUtil.getStringPref(mContext, ConfigFragment.CONFIG_CACHE_TYPE, "").equals(ConfigFragment.CONFIG_CACHE_TYPE_MEM)) {
                this.mMemImageCache = MemImageCache.getInstance();
                bitmap = mMemImageCache.getImageFromWarehouse(mCoverUrl);
            } else {
                this.mDiskLruImageCache = DiskLruImageCache.getInstance(mContext);
                bitmap = mDiskLruImageCache.getBitmap(mCoverId);
            }

            boolean slowInternet = SharedPreferenceUtil.getBoolPref(mContext, ConfigFragment.INTERNET_SLOW_PREF);

            if (bitmap != null && !slowInternet) {
                //Image loaded from cache. No need to store it back
                notifyListener(false, bitmap);
                Log.i(MainActivity.APP_TAG, "Image loaded from cache");
            } else {

                if (slowInternet) {
                    Thread.sleep(3000);
                }

                URL url = new URL(mCoverUrl);
                InputStream in = url.openStream();
                BufferedInputStream buf = new BufferedInputStream(in);
                bitmap = BitmapFactory.decodeStream(buf);

                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                notifyListener(true, bitmap);
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
                if (mDiskLruImageCache != null) {
                    mDiskLruImageCache.put(mCoverId, bitmapResult);
                }
                if (mMemImageCache != null) {
                    mMemImageCache.addImageToWarehouse(mCoverUrl, bitmapResult);
                }
            }

            if (mImageDownloadListener!=null) {
                mImageDownloadListener.onDrawableAvailable(mIsArtist, new BitmapDrawable(mContext.getResources(), bitmapResult));
            } else {
                mImageDownloadListener.onRequestFail(mContext.getString(R.string.cover_fetch_fail));
            }

        }
    }

}

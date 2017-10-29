package com.febaisi.deezeralbumsfetch.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.febaisi.deezeralbumsfetch.MainActivity;
import com.febaisi.deezeralbumsfetch.R;
import com.febaisi.deezeralbumsfetch.cache.ImagesCache;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadCoverImageTask extends AsyncTask<Void, Integer, Pair<Boolean, Bitmap>> {

    private String mCoverUrl;
    private Context mContext;
    private ImageDownloadListener mImageDownloadListener;
    private ImagesCache mImagesCache;

    public DownloadCoverImageTask(Context context, String coverUrl, ImageDownloadListener imageDownloadListener) {
        this.mCoverUrl = coverUrl;
        this.mContext = context;
        this.mImageDownloadListener = imageDownloadListener;
        mImagesCache = ImagesCache.getInstance();
    }

    @Override
    protected Pair<Boolean, Bitmap> doInBackground(Void... params) {
        try {

            Bitmap bMap = mImagesCache.getImageFromWarehouse(mCoverUrl);
            if (bMap != null) {
                //Image loaded from cache. No need to store it in cache
                Log.i(MainActivity.APP_TAG, "Image loaded from chace.");
                return new Pair<>(false, bMap);
            }

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

            return new Pair<>(true, bMap);
        } catch (Exception e) {
            Log.e(MainActivity.APP_TAG,"Error parsing the file" + e.toString());
        }

        return null;
    }

    protected void onPostExecute(Pair<Boolean, Bitmap> bitmapReulst) {
        if (bitmapReulst != null) {
            if (bitmapReulst.first) {
                mImagesCache.addImageToWarehouse(mCoverUrl, bitmapReulst.second);
            }
            if (mImageDownloadListener!=null) {
                mImageDownloadListener.onDrawableAvailable(new BitmapDrawable(mContext.getResources(), bitmapReulst.second));
            } else {
                mImageDownloadListener.onRequestFail(mContext.getString(R.string.cover_fetch_fail));
            }
        }
    }


}
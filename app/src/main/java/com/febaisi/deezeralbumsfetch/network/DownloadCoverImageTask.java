package com.febaisi.deezeralbumsfetch.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.febaisi.deezeralbumsfetch.MainActivity;
import com.febaisi.deezeralbumsfetch.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadCoverImageTask extends AsyncTask<Void, Integer, Drawable> {

    private String mCoverUrl;
    private Context mContext;
    private ImageDownloadListener mImageDownloadListener;

    public DownloadCoverImageTask(Context context, String coverUrl, ImageDownloadListener imageDownloadListener) {
        this.mCoverUrl = coverUrl;
        this.mContext = context;
        this.mImageDownloadListener = imageDownloadListener;
    }

    @Override
    protected Drawable doInBackground(Void... params) {
        try {
            URL url = new URL(mCoverUrl);
            InputStream in = url.openStream();
            BufferedInputStream buf = new BufferedInputStream(in);
            Bitmap bMap = BitmapFactory.decodeStream(buf);

            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }

            return new BitmapDrawable(mContext.getResources(), bMap);
        } catch (Exception e) {
            Log.e(MainActivity.APP_TAG,"Error parsing the file" + e.toString());
        }

        return null;
    }

    protected void onPostExecute(Drawable drawable)
    {
        if (mImageDownloadListener!=null) {
            if (drawable!=null) {
                mImageDownloadListener.onDrawableAvailable(drawable);
            } else {
                mImageDownloadListener.onRequestFail(mContext.getString(R.string.cover_fetch_fail));
            }
        }
    }

}
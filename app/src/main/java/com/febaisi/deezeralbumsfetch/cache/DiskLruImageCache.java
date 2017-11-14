package com.febaisi.deezeralbumsfetch.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.febaisi.deezeralbumsfetch.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskLruImageCache {

    private DiskLruCache mDiskCache;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    private int mCompressQuality = 100;
    private static final int APP_VERSION = 1;
    private static final int VALUE_COUNT = 1;
    private static final String UNIQUE_NAME = "thumbnails";
    private static DiskLruImageCache mDiskLruImageCache;

    //It should be based on Phone available storage. Let's skip that for this Deezer demo test
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 100; // 100MB

    protected DiskLruImageCache(Context context) {
        try {
            final File diskCacheDir = getDiskCacheDir(context, UNIQUE_NAME );
            mDiskCache = DiskLruCache.open( diskCacheDir, APP_VERSION, VALUE_COUNT, DISK_CACHE_SIZE );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskLruImageCache getInstance(Context context) {
        if (mDiskLruImageCache == null) {
            mDiskLruImageCache = new DiskLruImageCache(context);
        }
        return mDiskLruImageCache;
    }

    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream( editor.newOutputStream( 0 ), CacheUtil.IO_BUFFER_SIZE );
            return bitmap.compress( mCompressFormat, mCompressQuality, out );
        } finally {
            if ( out != null ) {
                out.close();
            }
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {

        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !CacheUtil.isExternalStorageRemovable() ?
                        CacheUtil.getExternalCacheDir(context).getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    public void put(String key, Bitmap data) {
        DiskLruCache.Editor editor = null;

        try {
            editor = mDiskCache.edit( key );
            if (editor == null) {
                return;
            }

            if (writeBitmapToFile( data, editor)) {
                mDiskCache.flush();
                editor.commit();
            } else {
                editor.abort();
            }
        } catch (IOException e) {
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
                Log.e(MainActivity.APP_TAG, "IOException: " + ignored.getMessage());
            }
        }
    }

    public Bitmap getBitmap(String key) {

        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;

        try {
            snapshot = mDiskCache.get(key);
            if (snapshot == null) {
                return null;
            }

            final InputStream in = snapshot.getInputStream( 0 );
            if (in != null) {
                final BufferedInputStream buffIn = new BufferedInputStream( in, CacheUtil.IO_BUFFER_SIZE );
                bitmap = BitmapFactory.decodeStream( buffIn );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return bitmap;
    }

    public boolean containsKey( String key ) {
        //Let's keep this method for this test
        boolean contained = false;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(key);
            contained = snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return contained;
    }

    public void clearCache() {
        try {
            mDiskCache.delete();
            mDiskLruImageCache = null;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public File getCacheFolder() {
        return mDiskCache.getDirectory();
    }

}
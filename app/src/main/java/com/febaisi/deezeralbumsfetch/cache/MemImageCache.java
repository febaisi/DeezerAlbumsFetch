package com.febaisi.deezeralbumsfetch.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemImageCache {

    private  LruCache<String, Bitmap> mMemoryCache;
    private static MemImageCache memImageCache;

    protected MemImageCache() {
        // Exists only to defeat instantiation.
    }

    /**
     * Using memImageCache as a Singleton class
     * @return MemImageCache object
     */
    public static MemImageCache getInstance() {
        if (memImageCache == null) {
            memImageCache = new MemImageCache();
        }
        return memImageCache;
    }

    public void addImageToWarehouse(String key, Bitmap value) {
        if(mMemoryCache != null && mMemoryCache.get(key) == null) {
            mMemoryCache.put(key, value);
        }
    }

    public Bitmap getImageFromWarehouse(String key) {
        return mMemoryCache.get(key);
    }

    public void removeImageFromWarehouse(String key) {
        mMemoryCache.remove(key);
    }

    public void clearCache() {
        if(mMemoryCache != null) {
            mMemoryCache.evictAll();
        }
    }

    public void initializeCache() {
        // Stored available mem in kilobytes as LruCache takes an int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);

        // Use half of the available memory for this memory memImageCache.
        // In real world we may need to DIVIDE IT BY 8 and customize it with the app needs;
        final int cacheSize = maxMemory / 2;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The memImageCache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }
}

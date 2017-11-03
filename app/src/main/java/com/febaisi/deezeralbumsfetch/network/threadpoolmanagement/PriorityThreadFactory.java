package com.febaisi.deezeralbumsfetch.network.threadpoolmanagement;

import android.os.Process;
import android.util.Log;

import com.febaisi.deezeralbumsfetch.MainActivity;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {

    private final int mThreadPriority;

    public PriorityThreadFactory(int threadPriority) {
        mThreadPriority = threadPriority;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(mThreadPriority);
                } catch (Throwable t) {
                    Log.e(MainActivity.APP_TAG, "We got issues setting thread priority: " + t.getMessage());
                }
                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }

}
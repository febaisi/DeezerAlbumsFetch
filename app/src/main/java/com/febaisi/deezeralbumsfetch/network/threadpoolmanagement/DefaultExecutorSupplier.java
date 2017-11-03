package com.febaisi.deezeralbumsfetch.network.threadpoolmanagement;

import android.os.Process;

import java.util.concurrent.TimeUnit;

public class DefaultExecutorSupplier{


    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private PriorityThreadPoolExecutor mNetworkThreadPoolExecutor;
    private static DefaultExecutorSupplier sInstance;

    public static DefaultExecutorSupplier getInstance() {
        if (sInstance == null) {
            synchronized (DefaultExecutorSupplier.class) {
                sInstance = new DefaultExecutorSupplier();
            }
        }
        return sInstance;
    }

    protected DefaultExecutorSupplier() {

        PriorityThreadFactory priorityThreadFactory = new
                PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);

        mNetworkThreadPoolExecutor = new PriorityThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                120L,
                TimeUnit.SECONDS,
                priorityThreadFactory
        );

    }

    public PriorityThreadPoolExecutor getNetworkThreadPoolExecutor() {
        return mNetworkThreadPoolExecutor;
    }

}
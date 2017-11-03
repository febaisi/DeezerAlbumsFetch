package com.febaisi.deezeralbumsfetch.network.threadpoolmanagement;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {

    public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,new PriorityBlockingQueue<Runnable>(), threadFactory);
    }

    @Override
    public Future<?> submit(Runnable task) {
        PriorityFutureTask futureTask = new PriorityFutureTask((PriorityRunnable) task);
        execute(futureTask);
        return futureTask;
    }

    private class PriorityFutureTask extends FutureTask<PriorityRunnable> implements Comparable<PriorityFutureTask> {

        private PriorityRunnable mPriorityRunnable;

        public PriorityFutureTask(PriorityRunnable mPriorityRunnable) {
            super(mPriorityRunnable, null);
            this.mPriorityRunnable = mPriorityRunnable;
        }

        /*
         * compareTo() method is defined in interface java.lang.Comparable and it is used
         * to implement natural sorting on java classes. natural sorting means the the sort
         * order which naturally applies on object e.g. lexical order for String, numeric
         * order for Integer or Sorting employee by there ID etc. most of the java core
         * classes including String and Integer implements CompareTo() method and provide
         * natural sorting.
         */
        @Override
        public int compareTo(PriorityFutureTask other) {
            Priority p1 = mPriorityRunnable.getPriority();
            Priority p2 = other.mPriorityRunnable.getPriority();
            return p2.ordinal() - p1.ordinal();
        }
    }
}
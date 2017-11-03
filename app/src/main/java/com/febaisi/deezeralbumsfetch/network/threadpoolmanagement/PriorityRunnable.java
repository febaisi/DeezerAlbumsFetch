package com.febaisi.deezeralbumsfetch.network.threadpoolmanagement;

public abstract class PriorityRunnable implements Runnable {

    private final Priority priority;

    public PriorityRunnable(Priority priority) {
        this.priority = priority;
    }

    @Override
    public abstract void run();

    public Priority getPriority() {
        return priority;
    }

}
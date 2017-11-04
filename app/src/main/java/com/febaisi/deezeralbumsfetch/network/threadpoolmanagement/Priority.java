package com.febaisi.deezeralbumsfetch.network.threadpoolmanagement;

/**
 * NOTE: DO NOT CHANGE ORDERING OF THOSE CONSTANTS UNDER ANY CIRCUMSTANCES.
 * Doing so will make ordering incorrect.
 *
 * LOW - Medium priority level. Used for warming of data that might soon get visible.
 * MEDIUM - Medium priority level. Used for warming of data that might soon get visible.
 * HIGH - Highest priority level. Used for data that are currently visible on screen.
 * IMMEDIATE - Highest priority level. Used for data that are required instantly(mainly for emergency).
 */

public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    IMMEDIATE;
}
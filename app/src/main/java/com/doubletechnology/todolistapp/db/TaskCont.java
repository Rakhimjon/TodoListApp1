package com.doubletechnology.todolistapp.db;

import android.provider.BaseColumns;

/**
 * Created by root on 12/1/16.
 */

public class TaskCont {
    public static final String DB_NAME = "com.doubletechnology.todolistapp";
    public static final int DB_VERSION = 1;


    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "pending";
        public static final String TASK_TITLE = "title";
        public static final String PRIORITY = "priority";
    }
}

package com.doubletechnology.todolistapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 12/1/16.
 */

public class TaskHelper extends SQLiteOpenHelper {

    public TaskHelper(Context context) {
        super(context, TaskCont.DB_NAME, null, TaskCont.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable = "CREATE TABLE " + TaskCont.TaskEntry.TABLE + "(" +
                TaskCont.TaskEntry._ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                TaskCont.TaskEntry.TASK_TITLE +" TEXT, " +
                TaskCont.TaskEntry.PRIORITY + " TEXT);";

        db.execSQL(creatTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

    }
}

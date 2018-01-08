package com.example.david.androidbasicshabittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habit.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_NAME + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_HABIT_TABLE);

        String SQL_CREATE_LOG_TABLE = "CREATE TABLE " + HabitContract.LogEntry.TABLE_NAME + " ("
                + HabitContract.LogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.LogEntry.COLUMN_HABIT_ID + " INTEGER NOT NULL, "
                + HabitContract.LogEntry.COLUMN_COMPLETED + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_LOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

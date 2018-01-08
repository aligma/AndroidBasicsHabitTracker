package com.example.david.androidbasicshabittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habit";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
    }

    public static final class LogEntry implements BaseColumns {
        public final static String TABLE_NAME = "log";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_ID = "habit_id";
        public final static String COLUMN_COMPLETED = "completed";
    }
}

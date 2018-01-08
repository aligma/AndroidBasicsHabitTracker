package com.example.david.androidbasicshabittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.david.androidbasicshabittracker.data.HabitContract;
import com.example.david.androidbasicshabittracker.data.HabitDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper dbHelper;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final String WAKE_UP_EARLY = "Wake up early";
    private final String BRUSH_TEETH = "Brush teeth";
    private final String TAKE_VITAMINS = "Take vitamins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // useful command to delete the database during testing
        // deleteDatabase();

        dbHelper = new HabitDbHelper(this);

        // INSERT SAMPLE DATA - Habits
        long wakeUpEarlyId = insertHabit(WAKE_UP_EARLY);
        long brushTeethId = insertHabit(BRUSH_TEETH);
        long takeVitaminsId = insertHabit(TAKE_VITAMINS);

        // INSERT SAMPLE DATA - Log entries; the person's tracked habits
        Calendar wokeUpMonday = Calendar.getInstance();
        wokeUpMonday.set(118 + 1900, 0, 1, 6, 0, 0);
        insertLog(wakeUpEarlyId, wokeUpMonday.getTimeInMillis());

        Calendar tookVitaminsMonday = Calendar.getInstance();
        tookVitaminsMonday.set(118 + 1900, 0, 1, 6, 5, 0);
        insertLog(takeVitaminsId, tookVitaminsMonday.getTimeInMillis());

        Calendar wokeUpTuesday = Calendar.getInstance();
        wokeUpTuesday.set(118 + 1900, 0, 2, 6, 20, 0);
        insertLog(wakeUpEarlyId, wokeUpTuesday.getTimeInMillis());

        Calendar brushedTeethTuesday = Calendar.getInstance();
        brushedTeethTuesday.set(118 + 1900, 0, 2, 6, 30, 0);
        insertLog(brushTeethId, brushedTeethTuesday.getTimeInMillis());

        Calendar wokeUpWednesday = Calendar.getInstance();
        wokeUpWednesday.set(118 + 1900, 0, 3, 6, 15, 0);
        insertLog(wakeUpEarlyId, wokeUpWednesday.getTimeInMillis());

        Calendar brushedTeethWednesday = Calendar.getInstance();
        brushedTeethWednesday.set(118 + 1900, 0, 2, 6, 25, 0);
        insertLog(brushTeethId, brushedTeethWednesday.getTimeInMillis());

        Calendar tookVitaminsWednesday = Calendar.getInstance();
        tookVitaminsWednesday.set(118 + 1900, 0, 1, 6, 30, 0);
        insertLog(takeVitaminsId, tookVitaminsWednesday.getTimeInMillis());

        displayDatabaseInfo();
    }

    private void deleteDatabase()
    {
        this.getApplicationContext().deleteDatabase("habit.db");
    }

    private long insertHabit(String habitName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_NAME, habitName);
        return db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }

    private long insertLog(long habitId, long at) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitContract.LogEntry.COLUMN_HABIT_ID, habitId);
        values.put(HabitContract.LogEntry.COLUMN_COMPLETED, at);
        return db.insert(HabitContract.LogEntry.TABLE_NAME, null, values);
    }

    private void displayDatabaseInfo() {

        Cursor cursor = read();

        try {

            int habitNameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_NAME);
            int logCompletedColumnIndex = cursor.getColumnIndex(HabitContract.LogEntry.COLUMN_COMPLETED);

            while (cursor.moveToNext()) {
                String habitName = cursor.getString(habitNameColumnIndex);
                long completed = cursor.getLong(logCompletedColumnIndex);

                Date completedDate = new Date(completed);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                Log.i(LOG_TAG, "Completed the habit " + habitName + " at " + df.format(completedDate));
            }
        } finally {
            cursor.close();
        }
    }

    /* pre review comments I have named this method read
       "You have the code for reading from the database and create a cursor, but it should be refactored in a method called "read()" as per rubric requirement. You can declare it private and the use it within the any other method. It will return a cursor then can be used to extract data from it."
     */
    private Cursor read()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry.COLUMN_NAME,
                HabitContract.LogEntry.COLUMN_COMPLETED
        };

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME + ", " + HabitContract.LogEntry.TABLE_NAME,
                projection,
                HabitContract.LogEntry.COLUMN_HABIT_ID + " = " + HabitContract.HabitEntry.TABLE_NAME + "." + HabitContract.HabitEntry._ID,
                null,
                null,
                null,
                null
        );

        return cursor;
    }
}

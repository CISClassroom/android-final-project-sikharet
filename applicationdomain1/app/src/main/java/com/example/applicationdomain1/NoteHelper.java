package com.example.applicationdomain1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.applicationdomain1.Constants.CONTENT;
import static com.example.applicationdomain1.Constants.TIME;
import static com.example.applicationdomain1.Constants.TABLE_NAME;
import static com.example.applicationdomain1.Constants._ID;

public class NoteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_note.db";
    private static final int DATABASE_VERSION = 1;

    public NoteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIME + " INTEGER, " + CONTENT + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

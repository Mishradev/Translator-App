package com.nitish.android.speech;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class SpeechStorageDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "speech.db";
    private static final int DATABASE_VERSION = 1;



    private static final String SCHEAMA_TRANSLATE = "CREATE TABLE IF NOT EXISTS " + SpeechStorageDescriptor.SpeechStorage.NAME+ " ( " +
            SpeechStorageDescriptor.SpeechStorage.Cols._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.SOURCE_LANGUAGE + " TEXT NOT NULL, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.AUDIO_FILE_PATH 	+ " TEXT  NOT NULL, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.DESTINATION_TEXT_FILE_PATH + " TEXT NOT NULL, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.RECORDING_TIME + " TEXT NOT NULL, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.Source_AUDIO_ENCODING + " TEXT NOT NULL, " +
            SpeechStorageDescriptor.SpeechStorage.Cols.Source_AUDIO_SUBJECT + " TEXT NOT NULL, " +
            "UNIQUE (" +
            SpeechStorageDescriptor.SpeechStorage.Cols._ID +
            ") ON CONFLICT REPLACE)";



    public SpeechStorageDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCHEAMA_TRANSLATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

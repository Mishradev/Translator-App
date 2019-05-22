package com.nitish.android.speech;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpeechStorageProvider extends ContentProvider {
    private SpeechStorageDatabaseHelper mDataBasehelper;

    private static final String TAG  =  SpeechStorageProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mDataBasehelper = new SpeechStorageDatabaseHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = SpeechStorageDescriptor.URI_MATCHER.match(uri);
        switch(match){
            case SpeechStorageDescriptor.SpeechStorage.PATH_TOKEN:
                return SpeechStorageDescriptor.SpeechStorage.CONTENT_TYPE_DIR;
            case SpeechStorageDescriptor.SpeechStorage.PATH_FOR_ID_TOKEN:
                return SpeechStorageDescriptor.SpeechStorage.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException ("URI " + uri + " is not supported.");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mDataBasehelper.getWritableDatabase();
        int token = SpeechStorageDescriptor.URI_MATCHER.match(uri);
        switch(token){
            case SpeechStorageDescriptor.SpeechStorage.PATH_TOKEN:{
                long id = db.insert(SpeechStorageDescriptor.SpeechStorage.NAME, null, values);
                //getContext().getContentResolver().notifyChange(uri, null);
                return SpeechStorageDescriptor.SpeechStorage.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }


    /**
     * Function to query the content provider.  This example queries the backing database.
     * It uses the SQLite API to retrieve wscontacts data based on the URI specified.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDataBasehelper.getReadableDatabase();
        final int match = SpeechStorageDescriptor.URI_MATCHER.match(uri);
        switch(match){
            // retrieve wscontacts list
            case SpeechStorageDescriptor.SpeechStorage.PATH_TOKEN:{
                Log.d(TAG,"executing query method in COntent Provider");
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(SpeechStorageDescriptor.SpeechStorage.NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case SpeechStorageDescriptor.SpeechStorage.PATH_FOR_ID_TOKEN:{
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(SpeechStorageDescriptor.SpeechStorage.NAME);
                String _id = uri.getLastPathSegment();
                selection = SpeechStorageDescriptor.SpeechStorage.Cols._ID+" = ?";
                selectionArgs = new String[]{_id};
                return builder.query(db, null, selection, selectionArgs, null, null, sortOrder);
            }
            default: return null;
        }
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDataBasehelper.getWritableDatabase();
        final int match = SpeechStorageDescriptor.URI_MATCHER.match(uri);

        switch(match){
            // retrieve wscontacts list
            case SpeechStorageDescriptor.SpeechStorage.PATH_TOKEN:{
                return db.delete(SpeechStorageDescriptor.SpeechStorage.NAME,selection,selectionArgs);
            }
            case SpeechStorageDescriptor.SpeechStorage.PATH_FOR_ID_TOKEN:{
                String _id = uri.getLastPathSegment();
                selection = SpeechStorageDescriptor.SpeechStorage.Cols._ID+" = ?";
                selectionArgs = new String[]{_id};
                db.delete(SpeechStorageDescriptor.SpeechStorage.NAME,selection,selectionArgs);
            }
            default: return 0;
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

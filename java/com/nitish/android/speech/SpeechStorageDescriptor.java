package com.nitish.android.speech;


import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class SpeechStorageDescriptor {
    // utility variables
    public static final String AUTHORITY = "com.rast.android.speech";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private SpeechStorageDescriptor(){};

    // register identifying URIs for Restaurant entity
    // the TOKEN value is associated with each URI registered
    private static  UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, SpeechStorage.PATH, SpeechStorage.PATH_TOKEN);
        matcher.addURI(authority, SpeechStorage.PATH_FOR_ID, SpeechStorage.PATH_FOR_ID_TOKEN);
        return matcher;
    }

    // Define a static class that represents description of stored content entity.
    // Here we define contacts
    public static class SpeechStorage {
        // an identifying name for table
        public static final String NAME = "translate";

        public static final String PATH = "translate";
        public static final int PATH_TOKEN = 100;
        public static final String PATH_FOR_ID = "translate/*";
        public static final int PATH_FOR_ID_TOKEN = 200;

        // URI to use to query the table
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        // define content mime type for entity
        public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.wscontact.app";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.wscontact.app";

        // a static class to store columns in entity
        public static class Cols implements  BaseColumns {
            /** The language at which speech is translating from, ISO Language code. See the list of language supoporting is
             *  at
             * **/
            public static final String SOURCE_LANGUAGE = "source_language";
            public static final String AUDIO_FILE_PATH  = "source_path";
            public static final String DESTINATION_TEXT_FILE_PATH = "destination_path";
            public static final String RECORDING_TIME = "recording_time";
            public static final String Source_AUDIO_ENCODING = "audio_encoding";
            public static final String Source_AUDIO_SUBJECT = "audio_subject";
        }

    }


}
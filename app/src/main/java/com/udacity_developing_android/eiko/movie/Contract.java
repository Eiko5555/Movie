package com.udacity_developing_android.eiko.movie;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eiko on 11/9/2017.
 */

public class Contract {
    public static final String CONTENT_AUTHORITY =
            "com.udacity_developing_android.eiko.movie";
    public static final Uri BASE_URI = Uri.parse(
            "content://" + CONTENT_AUTHORITY);
    public static final String PATH = "favorite";

    private Contract() {
    }

    public static final class Entry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_URI, PATH);
        public static String TABLE_NAME = "favoritelist";
        public static String COLUMN_MOVIE_ID = BaseColumns._ID;
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_POSTER = "poster";
        public static String COLUMN_OVERVIEW = "overview";
        public static String COLUMN_RATING = "rating";
        public static String COLUMN_RELEASEDATE = "releasedate";
        public static String CONTENT_LIST = ContentResolver.
                CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static String CONTENT_ITEM = ContentResolver.
                CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

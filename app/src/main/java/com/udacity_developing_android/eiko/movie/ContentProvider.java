package com.udacity_developing_android.eiko.movie;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by eiko on 11/13/2017.
 */

public class ContentProvider extends android.content.ContentProvider {
    public static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);
//    public static String favoritemovieId =
//            Contract.Entry.COLUMN_MOVIE_ID + " = ? ";
    static int FAVORITE = 100;
    static int FAVORITE_ID = 101;

    static {
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,
                Contract.PATH, FAVORITE);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,
                Contract.PATH + "/#", FAVORITE_ID);
    }

    private DbHelper mDpHelper;

    @Override
    public boolean onCreate() {
        mDpHelper = new DbHelper(getContext());
        return true;
    }

//    public Cursor getAll() {
//        return mDpHelper.getReadableDatabase().query(
//                Contract.Entry.TABLE_NAME, null, null, null,
//                null, null, null);
//    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = mDpHelper.getReadableDatabase();
        Cursor c = null;

        int urimatcher = uriMatcher.match(uri);
        if (urimatcher == FAVORITE) {
            c = db.query(Contract.Entry.TABLE_NAME, projection,
                    selection, selectionArgs, null, null, sortOrder);
        } else if (urimatcher == FAVORITE_ID) {
            selection = Contract.Entry._ID + "=?";
//            projection = {Contract.Entry.COLUMN_TITLE};
            selectionArgs = new String[]
                    {String.valueOf(ContentUris.parseId(uri))};
            c = db.query(Contract.Entry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
        }
        if (c.getCount() < 0) {
            Log.v("Content", "cursor is empty");
        }
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        if (match == FAVORITE) {
            return Contract.Entry.CONTENT_LIST;
        } else if (match == FAVORITE_ID) {
            return Contract.Entry.CONTENT_ITEM;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = uriMatcher.match(uri);
        Log.v("insert", String.valueOf(match));
        if (match == FAVORITE) {
            return insertFavorite(uri, values);
        }
        return null;
    }

    public Uri insertFavorite(Uri uri, ContentValues values) {
        Log.v("ContentProvider", "insert START");
        SQLiteDatabase db = mDpHelper.getWritableDatabase();
        String selection = null;
        String[] selectionArgs  = null;
        long id = 0;
        /*if (id == -1){
            db.update(Contract.Entry.TABLE_NAME, values,
                    selection, selectionArgs);
        }*/

         id = db.insert(Contract.Entry.TABLE_NAME, null, values);
        Log.v("insertFav", String.valueOf(id));

        if (id > 0)
            uri = Contract.Entry.buildMovieUri(id);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDpHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);
        final int match = uriMatcher.match(uri);
        int deletedOne = 0;
        if (match == FAVORITE) {
            deletedOne = db.delete(Contract.Entry.TABLE_NAME, selection
                    , selectionArgs);
        } else if (match == FAVORITE_ID) {
            selection = Contract.Entry._ID + "= ?";
            selectionArgs = new String[]{
                    String.valueOf(ContentUris.parseId(uri))};
            deletedOne = db.delete(Contract.Entry.TABLE_NAME,
                    selection, selectionArgs);
        }
        return deletedOne;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);
        if (match == FAVORITE) {
            return updateFavorite(uri, values, selection, selectionArgs);
        } else if (match == FAVORITE_ID) {
            selection = Contract.Entry._ID + "= ?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return updateFavorite(uri, values, selection, selectionArgs);
        }
        return match;
    }

    private int updateFavorite(Uri uri, ContentValues values,
                               String selection, String[] selectionArgs) {
        if (values.containsKey(Contract.Entry.COLUMN_TITLE)){
            values.getAsString(Contract.Entry.COLUMN_TITLE);
        }

        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase db = mDpHelper.getWritableDatabase();
        int rowUpdate = db.update(Contract.Entry.TABLE_NAME, values,
                selection, selectionArgs);
        if (rowUpdate != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdate;
    }
}

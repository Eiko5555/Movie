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

/**
 * Created by eiko on 11/13/2017.
 */

public class ContentProvider extends android.content.ContentProvider{
    public static String favoritemovieId =
            Contract.Entry.COLUMN_MOVIE_ID + " = ? ";
    private DbHelper mDpHelper;
    static int FAVORITE = 1;
    static int FAVORITE_ID = 101;
    public static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,
                Contract.PATH, FAVORITE);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,
                Contract.PATH + "/#", FAVORITE_ID);
    }
    @Override
    public boolean onCreate() {
        mDpHelper = new DbHelper(getContext());
        return true;
    }
    public Cursor getAll(){
        return mDpHelper.getReadableDatabase().query(
                Contract.Entry.TABLE_NAME, null, null, null,
                null, null, null);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = mDpHelper.getReadableDatabase();
        Cursor c = null;
        int urimatcher = uriMatcher.match(uri);
        if (urimatcher == FAVORITE){
                c = db.query(Contract.Entry.TABLE_NAME, projection,
                        selection,selectionArgs,null,null, sortOrder);
        }else if (urimatcher == FAVORITE_ID){
            selection = Contract.Entry._ID + "= ?";
            selectionArgs = new String[]
                    {String.valueOf(ContentUris.parseId(uri))};
            c = db.query(Contract.Entry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        if(match == FAVORITE){
            return Contract.Entry.CONTENT_LIST;
        }else if (match == FAVORITE_ID){
            return Contract.Entry.CONTENT_ITEM;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = uriMatcher.match(uri);
        if (match == FAVORITE) {
            return insertFavorite(uri, values);
        }
        return null;
    }
    public Uri insertFavorite(Uri uri, ContentValues values){
        Log.v("ContentProvider", "insert START");
        SQLiteDatabase db = mDpHelper.getWritableDatabase();
        long id = db.insert(Contract.Entry.TABLE_NAME, null, values);
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
        if (match == FAVORITE){
            deletedOne = db.delete(Contract.Entry.TABLE_NAME, selection
            , selectionArgs);
        }else if (match == FAVORITE_ID){
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
        if (match == FAVORITE){
            return updateFavorite(uri, values, selection, selectionArgs);
        }else if (match == FAVORITE_ID){
            selection = Contract.Entry._ID + "= ?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return updateFavorite(uri, values, selection, selectionArgs);
        }
        return match;
    }

    private int updateFavorite(Uri uri, ContentValues values,
                               String selection, String[] selectionArgs) {

        if (values.size() == 0){ return  0;}
        SQLiteDatabase db = mDpHelper.getWritableDatabase();
        int rowUpdate = db.update(Contract.Entry.TABLE_NAME, values,
                selection, selectionArgs);
        if (rowUpdate !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdate;
    }
}

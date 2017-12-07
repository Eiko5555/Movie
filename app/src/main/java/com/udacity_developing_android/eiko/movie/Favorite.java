package com.udacity_developing_android.eiko.movie;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiko on 11/16/2017.
 */

public class Favorite extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    static final int CURSOR_ID = 2;
    List<Poster> movie = new ArrayList<>();
    String[] projection = new String[]{
            Contract.Entry._ID,
            Contract.Entry.COLUMN_TITLE,
            Contract.Entry.COLUMN_POSTER,
            Contract.Entry.COLUMN_OVERVIEW,
            Contract.Entry.COLUMN_RATING,
            Contract.Entry.COLUMN_RELEASEDATE
    };
    private Uri favoriteUri = Contract.Entry.CONTENT_URI;
    private FavoriteAdapter mFavoriteAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_listview);
        mFavoriteAdapter = new FavoriteAdapter(this, null);
        ListView listView = (ListView) findViewById(R.id.favorite_listview);
        listView.setAdapter(mFavoriteAdapter);
        getSupportLoaderManager().initLoader(CURSOR_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(
                CURSOR_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v("Favorite", "onCreateLoader");
        return new CursorLoader(this, favoriteUri, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoriteAdapter.swapCursor(null);
    }
}

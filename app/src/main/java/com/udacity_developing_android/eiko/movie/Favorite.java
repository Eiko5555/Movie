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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiko on 11/16/2017.
 */

public class Favorite extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    private Uri favoriteUri = Contract.Entry.CONTENT_URI;
    static final int CURSOR_ID = 2;
    private FavoriteAdapter mFavoriteAdapter;
    List<Poster> movie = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        mFavoriteAdapter = new FavoriteAdapter(this, null);
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(mFavoriteAdapter);
        getSupportLoaderManager().initLoader(CURSOR_ID, null, this);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("favoriteList",
//                (ArrayList));
//        super.onSaveInstanceState(outState);
//    }

    String[] projection = new String[]{
            Contract.Entry.COLUMN_TITLE,
            Contract.Entry.COLUMN_POSTER,
            Contract.Entry.COLUMN_OVERVIEW,
            Contract.Entry.COLUMN_RATING,
            Contract.Entry.COLUMN_RELEASEDATE
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v("Favorite", "onCreateLoader");
        return new CursorLoader(this, favoriteUri, projection,null,null,null);
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

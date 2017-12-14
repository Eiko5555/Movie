package com.udacity_developing_android.eiko.movie;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiko on 11/16/2017.
 */

public class Favorite extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    static final int CURSOR_ID = 2;

    private Uri favoriteUri = Contract.Entry.CONTENT_URI;
    private FavoriteAdapter mFavoriteAdapter;

    String[] projection = new String[]{
            Contract.Entry._ID,
            Contract.Entry.COLUMN_TITLE,
            Contract.Entry.COLUMN_POSTER,
            Contract.Entry.COLUMN_OVERVIEW,
            Contract.Entry.COLUMN_RATING,
            Contract.Entry.COLUMN_RELEASEDATE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        TextView textviewLoading = (TextView)findViewById(R.id.loading_text);
        Log.v("favorite--", String.valueOf(projection));
        Log.v("favorite--", String.valueOf(favoriteUri));
        mFavoriteAdapter = new FavoriteAdapter(this, null);
        final GridView gridView = (GridView)findViewById(R.id.gridview);
        if (mFavoriteAdapter != null){
            textviewLoading.setVisibility(View.INVISIBLE);
        }
        gridView.setAdapter(mFavoriteAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.v("favAdater", "saved poster clicked.");
//                Intent intent = new Intent(Favorite.this, DetailActivity.class);
//                Poster itemPosition = mFavoriteAdapter.getItem(position);//
//                intent.putExtra("poster_path", itemPosition.getImage());
//                intent.putExtra("title", itemPosition.getTitle());
//                intent.putExtra("release_date", itemPosition.getReleaseDate());
//                intent.putExtra("vote_average", itemPosition.getVoteAverage());
//                intent.putExtra("overview", itemPosition.getOverview());
//                intent.putExtra("id", itemPosition.getId());
//
//                int movieid = getIntent().getExtras().getInt("id");
//                String movieidString = String.valueOf(movieid);
//                intent.putExtra("id",movieidString);
//                intent.putExtra("poster_path",
//                        String.valueOf(getIntent().getStringExtra("poster_path")));
//                intent.putExtra("title",
//                        String.valueOf(getIntent().getStringExtra("title")));
//                intent.putExtra("release_date",
//                        String.valueOf(getIntent().getStringExtra("release_date")));
//                intent.putExtra("vote_average",
//                        String.valueOf(getIntent().getStringExtra("vote_average")));
//                intent.putExtra("overview",
//                        String.valueOf(getIntent().getStringExtra("vote_average")));
//                Poster current = new Poster(titleFavorite, releasedateFavorite
//                , rateFavorite, overviewFavorite,posterFavorite, idFavoriteINT);
//                Intent intent = new Intent(context, DetailActivity.class);
//                Log.v("favAdapt", current.toString());
//                intent.putExtra("results", current);
//                startActivity(intent);
//            }
//        });
//        ListView listView = (ListView) findViewById(R.id.favorite_listview);
//        listView.setAdapter(mFavoriteAdapter);
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

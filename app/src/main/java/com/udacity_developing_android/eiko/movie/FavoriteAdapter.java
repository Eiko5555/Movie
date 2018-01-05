package com.udacity_developing_android.eiko.movie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by eiko on 11/16/2017.
 */

public class FavoriteAdapter extends CursorAdapter {
    Context mContext;
    Cursor c;

    FavoriteAdapter(Context context, Cursor c) {
        super(context, c);
        this.mContext = context;
        this.c = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.image_grid, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        int position = cursor.getColumnIndex(Contract.Entry.COLUMN_POSTER);
        final String poster = cursor.getString(position);
        ImageView posterImage = (ImageView) view.findViewById(R.id.image_for_grid);
        Log.v("favadapter 2 ", poster);
        Picasso.with(mContext).load(poster).into(posterImage);

        int id = cursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_ID);
        final int idFavoriteINT = cursor.getInt(id);
        int title = cursor.getColumnIndex(Contract.Entry.COLUMN_TITLE);
        final String titleFavorite = cursor.getString(title);
        int rate = cursor.getColumnIndex(Contract.Entry.COLUMN_RATING);
        final String rateFavorite = cursor.getString(rate);
        int overview = cursor.getColumnIndex(Contract.Entry.COLUMN_OVERVIEW);
        final String overviewFavorite = cursor.getString(overview);
        int releasedate = cursor.getColumnIndex(Contract.Entry.COLUMN_RELEASEDATE);
        final String releasedateFavorite = cursor.getString(releasedate);
        cursor.getPosition();
        posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("favAdater", "saved poster clicked.");
                Log.v("favoriteAdapter", titleFavorite);
                Log.v("favoriteAdapter", releasedateFavorite);
                Log.v("favoriteAdapter", rateFavorite);
                Log.v("favoriteAdapter", overviewFavorite);
                Log.v("favoriteAdapter", poster);
                Log.v("favoriteAdapter", String.valueOf(idFavoriteINT));

                Intent intent = new Intent(context, Favorite_Detail.class);
                intent.putExtra("poster_path", poster);
                intent.putExtra("title", titleFavorite);
                intent.putExtra("release_date", releasedateFavorite);
                intent.putExtra("vote_average", rateFavorite);
                intent.putExtra("overview", overviewFavorite);
                intent.putExtra("id", String.valueOf(idFavoriteINT));
                mContext.startActivity(intent);
            }
        });
    }
}

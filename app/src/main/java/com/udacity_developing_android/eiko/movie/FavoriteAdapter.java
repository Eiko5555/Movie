package com.udacity_developing_android.eiko.movie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
     FavoriteAdapter(Context context, Cursor c) {
        super(context, c);
         this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.image_grid, parent, false );
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        int position = cursor.getColumnIndex(Contract.Entry.COLUMN_POSTER);
        final String poster = cursor.getString(position);
        ImageView posterImage = (ImageView)view.findViewById(R.id.image_for_grid);
        String POSTER_URL_BASE = "http://image.tmdb.org/t/p/w185";
        String POSTER_URL = POSTER_URL_BASE + poster;
        Picasso.with(mContext).load(POSTER_URL).into(posterImage);
        cursor.getPosition();
        posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = cursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_ID);
                int idFavorite = cursor.getInt(id);
//                String idFavorite = cursor.getString(id);
                int title = cursor.getColumnIndex(Contract.Entry.COLUMN_TITLE);
                String titleFavorite = cursor.getString(title);
                int rate = cursor.getColumnIndex(Contract.Entry.COLUMN_RATING);
                String rateFavorite = cursor.getString(rate);
                int overview = cursor.getColumnIndex(Contract.Entry.COLUMN_OVERVIEW);
                String overviewFavorite = cursor.getString(overview);
                int releasedate = cursor.getColumnIndex(Contract.Entry.COLUMN_RELEASEDATE);
                String releasedateFavorite = cursor.getString(releasedate);
                int poster_path = cursor.getColumnIndex(Contract.Entry.COLUMN_POSTER);
                String posterFavorite = cursor.getString(poster_path);

                Poster current = new Poster(titleFavorite, releasedateFavorite
                , rateFavorite, overviewFavorite,posterFavorite, idFavorite);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("results", current);
                context.startActivity(intent);
            }
        });
    }
}

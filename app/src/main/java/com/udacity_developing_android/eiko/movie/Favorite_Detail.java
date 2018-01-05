package com.udacity_developing_android.eiko.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by eiko on 1/4/2018.
 */

public class Favorite_Detail extends Activity{
    private ImageView imageview;
    private TextView tv_title, tv_releasedate, tv_rate, tv_summery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_detail);

        imageview = (ImageView) findViewById(R.id.poster);
        tv_title = (TextView) findViewById(R.id.title);
        tv_releasedate = (TextView) findViewById(R.id.date);
        tv_rate = (TextView) findViewById(R.id.rating);
        tv_summery = (TextView) findViewById(R.id.summery);

        tv_title.setText(getIntent().getExtras().getString("title"));
        tv_releasedate.setText(getIntent().getExtras().getString("release_date"));
        tv_rate.setText(getIntent().getExtras().getString("vote_average"));
        tv_summery.setText(getIntent().getExtras().getString("overview"));

        String image = getIntent().getStringExtra("poster_path");
        Picasso.with(this).load(image).into(imageview);

    }
}

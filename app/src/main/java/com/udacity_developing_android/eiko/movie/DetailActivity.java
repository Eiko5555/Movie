package com.udacity_developing_android.eiko.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by eiko on 8/12/2017.
 */

public class DetailActivity extends Activity {
    Poster item;
    private ImageView imageview;
    private TextView tv_title;
    private TextView tv_releasedate;
    private TextView tv_rate;
    private TextView tv_summery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailview);
        Log.v("DetailActivity", "passing onCeate");

//        Intent intent = getIntent();
//        item = getIntent().getParcelableExtra("current");

        imageview = (ImageView) findViewById(R.id.poster);
        tv_title = (TextView) findViewById(R.id.title);
        tv_releasedate = (TextView) findViewById(R.id.date);
        tv_rate = (TextView) findViewById(R.id.rating);
        tv_summery = (TextView) findViewById(R.id.summery);

//        if (intent != null) {
//            String title = intent.getStringExtra("title");
//        }
//        tv_title.setText(title);


//        if (item != null) {
//            tv_title.setText(item.getTitle());
        tv_title.setText(getIntent().getExtras().getString("title"));
        tv_releasedate.setText(getIntent().getExtras().getString("release_date"));
        tv_rate.setText(getIntent().getExtras().getString("vote_average"));
        tv_summery.setText(getIntent().getExtras().getString("overview"));

        String image = getIntent().getStringExtra("poster_path");
        Picasso.with(this).load(image).into(imageview);
    }
}

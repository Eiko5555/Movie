package com.udacity_developing_android.eiko.movie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


        item = getIntent().getParcelableExtra("current");

//        imageview = (ImageView)findViewById(R.id.poster);
        tv_title = (TextView) findViewById(R.id.title);
//        tv_releasedate = (TextView)findViewById(R.id.date);
//        tv_rate = (TextView)findViewById(R.id.rating);

//        String image = bundle.getString("poster_path");

        if (item != null) {
            tv_title.setText(item.getTitle());
//        Picasso.with(this).load(image).into(imageview);
        }
    }
}

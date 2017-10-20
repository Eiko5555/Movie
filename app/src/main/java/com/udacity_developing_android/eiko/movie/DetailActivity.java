package com.udacity_developing_android.eiko.movie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    private ToggleButton favoriteButton;
    private List<String> trailerListkey = new ArrayList<>();
    private List<String> trailerName = new ArrayList<>();
    private String API_KEY = "a4f36a9495b94f99828b2636e79fb982";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailview);
        Log.v("DetailActivity", "passing onCeate");
        Poster currentMovie = this.getIntent().getParcelableExtra("movie");

        imageview = (ImageView) findViewById(R.id.poster);
        tv_title = (TextView) findViewById(R.id.title);
        tv_releasedate = (TextView) findViewById(R.id.date);
        tv_rate = (TextView) findViewById(R.id.rating);
        tv_summery = (TextView) findViewById(R.id.summery);
        favoriteButton = (ToggleButton) findViewById(R.id.img_button);
        RecyclerView recyclerView = (RecyclerView)findViewById(
                R.id.trailerrecyclerview) ;

        tv_title.setText(getIntent().getExtras().getString("title"));
        tv_releasedate.setText(getIntent().getExtras().getString("release_date"));
        tv_rate.setText(getIntent().getExtras().getString("vote_average"));
        tv_summery.setText(getIntent().getExtras().getString("overview"));
        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(DetailActivity.this,
                        "star clicked", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DetailActivity.this,
                            "star clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
        //        imgButtonfavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imgButtonfavorite.setBackgroundResource(R.drawable.selector);
//                Toast.makeText(DetailActivity.this,
//                        "star clicked", Toast.LENGTH_LONG).show();
//            }
//        });
//        imgButtonfavorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isFavorite = true;
//                imgButtonfavorite.setImageDrawable(ContextCompat.getDrawable(
//                        getApplicationContext(),
//                        android.R.drawable.btn_star_big_on));
//                if (isFavorite){
//                    imgButtonfavorite.setImageDrawable(ContextCompat.getDrawable(
//                            getApplicationContext(),
//                            android.R.drawable.btn_star_big_on));
//                }else {
//                    imgButtonfavorite.setImageDrawable(
//                            ContextCompat.getDrawable(
//                            getApplicationContext(),
//                            android.R.drawable.btn_star_big_off));
//                }
//            }
//        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String image = getIntent().getStringExtra("poster_path");
        Picasso.with(this).load(image).into(imageview);

//        int currentId = currentMovie.getId();
//        Log.i("detail", ", ID: "+ currentId);
//        String currentMovieId = String.valueOf(currentId);
//        Log.i("detail", ", ID: "+ currentMovieId);
        this.excuteTrailer();
    }
    private void excuteTrailer(){
        FetchTrailer fetchTrailer = new FetchTrailer();
        fetchTrailer.execute();
    }
    private class FetchTrailer extends AsyncTask<String, Void, String>{
        Intent intent = getIntent();
        Poster currentMovie = intent.getParcelableExtra("movie");
        int movieId = currentMovie.getId();
        String idString = String.valueOf(movieId);

        private void getJsonData(String json)throws JSONException{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            final String youtubeURL = "https://www.youtube.com/watch?v=";
            for (int i = 0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getString("site").contentEquals("YouTube")){
                    trailerListkey.add(i, object.getString("key"));
                    trailerName.add(i, object.getString("name"));
                }
                Log.i("DetailActivity: ", "URL for youtube = "+ youtubeURL
                        + trailerListkey.get(i));
                Log.i("DetailActivity: ", "Trailer name: "+ trailerName.get(i));
            }
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection httpUrlConnection = null;
            BufferedReader bufferReader = null;
            String dataString = null;
            try{
                final String URL_BASE = "http://api.themoviedb.org/3/movie/"
            + idString + "/videos";
                Uri uri = Uri.parse(URL_BASE).buildUpon().appendQueryParameter(
                        "api_key", API_KEY).build();
                URL url = new URL(uri.toString());
                Log.i("DetailActivity", "trailer url: "+ url);

            httpUrlConnection = (HttpURLConnection)url.openConnection();
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.connect();
                InputStream inputStream = httpUrlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                bufferReader = new BufferedReader(new InputStreamReader(
                        inputStream));
                String string;
                while ((string = bufferReader.readLine()) != null){
                    stringBuilder.append(string).append("\n");
                }
                dataString = stringBuilder.toString();
                getJsonData(dataString);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            final ArrayList<Trailer> trailerArrayList = new ArrayList<>();
            final String youtubeUrl = "https://www.youtube.com/watch?v=";
            for (int i=0; i<trailerListkey.size(); i++){
                Trailer trailer = new Trailer();
                trailer.key = trailerListkey.get(i);
                trailer.name = trailerName.get(i);
                trailerArrayList.add(trailer);
            }
            Log.i("Detail PostExecute ","traler url: " +
                    youtubeUrl + trailerListkey);
            Log.i("Detail PostExecute ","traler name: " +
                    trailerName);
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.trailerrecyclerview);
            final TrailerAdapter trailerAdapter = new TrailerAdapter(
                    getApplicationContext(), trailerArrayList);
            recyclerView.setAdapter(trailerAdapter);
        }
    }
}

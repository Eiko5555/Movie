package com.udacity_developing_android.eiko.movie;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ProgressBar;

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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private ProgressBar progressbar;
    ImageAdapter mImageAdapter;
    ArrayList<Poster> mGridImage;
//    String URL_IMAGE = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);

        gridview = (GridView) findViewById(R.id.gridview);
        mGridImage = new ArrayList<>();
        mImageAdapter = new ImageAdapter(this,
                R.layout.image_grid, mGridImage);
        gridview.setAdapter(mImageAdapter);
        new AsyncHttpTask().execute();
    }

    public class AsyncHttpTask extends AsyncTask<Void,
            Void, Poster[]> {
        public String SCHEME = "http";
        public String URL_BASE = "api.themoviedb.org";
        public String PATH_ONE = "3";
        public String PATH_TWO = "movie";
        public String IMAGE_PATH = "http://image.tmdb.org/t/p/w185";
        public String API_KEY = "my api key";
        String SORT = "popular";
        String SORT_TARE = "top_rated";

        @Override
        protected Poster[] doInBackground(Void... params) {
            HttpURLConnection urlConnect = null;
            BufferedReader buffReader = null;
            String result = null;
            try {
                Uri uri = new Uri.Builder()
                        .scheme(SCHEME)
                        .authority(URL_BASE)
                        .appendPath(PATH_ONE)
                        .appendPath(PATH_TWO)
                        .appendPath(SORT)
                        .appendQueryParameter("api_key", API_KEY )
                        .build();
                Log.v("MainActivity.java", uri.toString());
                String makeUrl = uri.toString();
                URL url = new URL(makeUrl);
                urlConnect = (HttpURLConnection) url.openConnection();
                urlConnect.setRequestMethod("GET");
                urlConnect.connect();
                InputStream is = urlConnect.getInputStream();
                StringBuffer sb = new StringBuffer();
                buffReader = new BufferedReader(
                        new InputStreamReader(is));
                String line;
                while ((line = buffReader.readLine())!= null) {
                    sb.append(line + "\n");
                    Log.v("line ", line);
                }
                if (sb.length()==0){
                    return null;
                }
                result = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return catchingdata(result);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("catcningdate", "jsooon exception",e);
            }
            return null;
        }

        private Poster[] catchingdata(String result) throws
                JSONException {
            String RESULTS = "results";
            String POSTER_PATH = "poster_path";
            String OVERVIEW = "overview";
            String TITLE = "title";
            String RELEASE_DATE = "release_date";
            String VOTE_AVERAGE = "vote_average";

            JSONObject fetchJSON = new JSONObject(result);
            JSONArray jsonArray = fetchJSON.getJSONArray(RESULTS);
            Poster[] movieArray = new Poster[jsonArray.length()];
            for (int i = 0; i < movieArray.length; ++i) {
                JSONObject getJSon = jsonArray.getJSONObject(i);
                String title = getJSon.getString(TITLE);
                String releasedate = getJSon.getString(RELEASE_DATE);
                double rate = getJSon.getDouble(VOTE_AVERAGE);
                String overview = getJSon.getString(OVERVIEW);
//                Uri imagepath = new Uri.Builder().scheme(SCHEME)
//                        .authority(IMAGE)
//                        .appendEncodedPath(getJSon.getString(POSTER_PATH))
//                        .build();
                String poster_path = getJSon.getString(POSTER_PATH);
                String poster_url = IMAGE_PATH +poster_path;
                Log.v("mainactivity",poster_url);
                movieArray[i] = new Poster(title,
                        releasedate, rate, overview, poster_url);
            }
            return movieArray;
        }

        @Override
        protected void onPostExecute(Poster[] posters) {
//            if (posters != null){
//                mImageAdapter = new ImageAdapter(getAc(),
//                        Arrays.asList(posters));
//            }
            gridview.setAdapter(mImageAdapter);

        }
    }
}

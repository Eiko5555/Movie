package com.udacity_developing_android.eiko.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eiko on 8/12/2017.
 */

public class Poster implements Parcelable {
    private String poster_path;
    private String overview;
    private String title;
    private String release_date;
    private double vote_average;


    public Poster(String title, String releasedate,
                  double rate, String overview,
                  String poster_path) {
        this.title = title;
        this.release_date = releasedate;
        this.vote_average = rate;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    protected Poster(Parcel in) {
        poster_path = in.readString();
    }

    public static final Creator<Poster> CREATOR =
            new Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        @Override
        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };

    public String getImage() {
        return poster_path;
    }

//    public void setImage(String image) {
//        this.poster = image;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
    }
}

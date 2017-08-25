package com.udacity_developing_android.eiko.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eiko on 8/12/2017.
 */

public class Poster implements Parcelable {
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
    private String poster_path;
    private String overview;
    private String title;
    private String release_date;
    private String vote_average;
    private int id;

    public Poster(String title, String releasedate,
                  String rate, String overview,
                  String poster_path, int id) {
        this.title = title;
        this.release_date = releasedate;
        this.vote_average = rate;
        this.overview = overview;
        this.poster_path = poster_path;
        this.id = id;
    }

    protected Poster(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        overview = in.readString();
        id = in.readInt();
    }

    public String getImage() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public String getVoteAverage() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(vote_average);
        dest.writeString(overview);
    }
}

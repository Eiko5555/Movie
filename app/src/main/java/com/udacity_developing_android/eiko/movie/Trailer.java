package com.udacity_developing_android.eiko.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eiko on 10/12/2017.
 */

public class Trailer implements Parcelable {

    public final static Parcelable.Creator<Trailer> CREATOR
            = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            Trailer trailerParcel = new Trailer();
            trailerParcel.key = ((String) source.readValue(
                    String.class.getClassLoader()));
            trailerParcel.name = ((String) source.readValue(
                    String.class.getClassLoader()));
            return trailerParcel;
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
    public String key, name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
    }
}

package com.udacity_developing_android.eiko.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eiko on 11/8/2017.
 */

public class Review implements Parcelable {
    public final static Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            Review mReview = new Review();
//            mReview.id = ((String)in.readValue(String.class.getClassLoader()));
            mReview.content = ((String)in.readValue(String.class.getClassLoader()));
            return  mReview;
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];}
    };
//    public String getId(){
//        return id;
//    }
    public String id, content;
    public String getContent(){ return content; }
    @Override
    public int describeContents() { return 0; }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(id);
        dest.writeString(content);
    }
}
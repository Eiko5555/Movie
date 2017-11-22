package com.udacity_developing_android.eiko.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiko on 11/8/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public List<Review> review_List;
    public Context mContext;

    public ReviewAdapter(Context context, List<Review> reviewsArrayList) {
        this.mContext = context;
        this.review_List = reviewsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater = LayoutInflater.
                from(parent.getContext());
        View view = layoutinflater.inflate(R.layout.review_item, parent, false);
        Log.i("ReviewAdapter", "oncreateviewholder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = review_List.get(position);
        holder.reviewtextview.setText(review.getContent());
        Log.i("review, onBindviewH", review.getContent());
    }

    @Override
    public int getItemCount() {
        return review_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewtextview;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewtextview = (TextView)
                    itemView.findViewById(R.id.review_textview);
        }
    }
}


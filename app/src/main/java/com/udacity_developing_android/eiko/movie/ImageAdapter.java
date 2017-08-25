package com.udacity_developing_android.eiko.movie;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eiko on 8/12/2017.
 */

public class ImageAdapter extends ArrayAdapter<Poster> {
    ArrayList<Poster> mGridImage = new ArrayList<>();
    private Context mContext;
    private int layoutResourceId;

    public ImageAdapter(Context mContext, int layoutResourceId,
                        ArrayList<Poster> mGridImage) {
        super(mContext, layoutResourceId, mGridImage);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.mGridImage = mGridImage;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext)
                    .getLayoutInflater();
            convertView = inflater.inflate(
                    R.layout.image_grid, parent, false);
            holder = new ViewHolder();
            holder.imageview = (ImageView) convertView.
                    findViewById(R.id.image_for_grid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Poster item = mGridImage.get(position);
    Picasso.with(mContext).load(item.getImage())
                .into(holder.imageview);
        Log.v("ImageAdapter", item.getImage());
        return convertView;
    }

    static class ViewHolder {
        ImageView imageview;
    }
}

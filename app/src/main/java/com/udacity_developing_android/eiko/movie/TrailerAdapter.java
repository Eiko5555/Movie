package com.udacity_developing_android.eiko.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by eiko on 10/12/2017.
 */

 class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    public List<Trailer> trailers;
    public Context context;

    public TrailerAdapter(Context context, List<Trailer> trailerList)
    {this.context = context;
    this.trailers = trailerList;}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater =  LayoutInflater.from(
                parent.getContext());
        View view = layoutinflater.inflate(R.layout.trailter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        final Trailer trailer =  trailers.get(position);
        holder.trailertitle.setText(trailer.getName());
    }

    @Override
   public int getItemCount() {
      return trailers.size();
   }
   public static class ViewHolder extends RecyclerView.ViewHolder{
       TextView trailertitle;

       public ViewHolder(View itemView) {
           super(itemView);
           trailertitle = (TextView)itemView.findViewById(
                   R.id.trailer_title);
       }
   }
}

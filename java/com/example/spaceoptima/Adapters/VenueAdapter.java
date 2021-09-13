package com.example.spaceoptima.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaceoptima.Building;
import com.example.spaceoptima.R;
import com.example.spaceoptima.Models.Venue;

import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.MyViewHolder>
{
    private Context mContext;
    private List<Venue> mData;


    public VenueAdapter(Context mContext, List<Venue> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_venue, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
       holder.tv_venue_title.setText(mData.get(position).getTitle());
       holder.img_venue_thumbnail.setImageResource(mData.get(position).getThumbnail());
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Intent intent = new Intent(mContext, Building.class);

               //passing data to Building_activity
               intent.putExtra("Title", mData.get(position).getTitle());
               intent.putExtra("Description", mData.get(position).getDescription());
               intent.putExtra("Faculty", mData.get(position).getFaculty());
               intent.putExtra("Theatres", mData.get(position).getTheatres());
               intent.putExtra("Students", mData.get(position).getStudents());
               intent.putExtra("Modules", mData.get(position).getModules());
               intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
               //Start the activity
               mContext.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_venue_title;
        ImageView img_venue_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            tv_venue_title = itemView.findViewById(R.id.venue_title_id);
            img_venue_thumbnail = itemView.findViewById(R.id.venue_img_id);
            cardView = itemView.findViewById(R.id.cardView_id);
        }
    }
}

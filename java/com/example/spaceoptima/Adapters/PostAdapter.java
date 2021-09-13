package com.example.spaceoptima.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spaceoptima.Models.Post;
import com.example.spaceoptima.PostDetailsActivity;
import com.example.spaceoptima.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>
{

    Context mContext;
    List<Post> mData;

    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvVenue.setText(mData.get(position).getVenue());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.imgPostProfile);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvTitle, tvVenue;
        ImageView imgPost;
        ImageView imgPostProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            tvTitle = itemView.findViewById(R.id.row_post_title);
            tvVenue = itemView.findViewById(R.id.row_post_venue);
            imgPost = itemView.findViewById(R.id.row_post_img);
            imgPostProfile = itemView.findViewById(R.id.row_post_profile);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postDetailsActivity = new Intent(mContext, PostDetailsActivity.class);
                    int position = getAdapterPosition();

                    postDetailsActivity.putExtra("title", mData.get(position).getTitle());
                    postDetailsActivity.putExtra("postImage", mData.get(position).getPicture());
                    postDetailsActivity.putExtra("description", mData.get(position).getDescription());
                    postDetailsActivity.putExtra("postKey", mData.get(position).getPostKey());
                    postDetailsActivity.putExtra("userPhoto", mData.get(position).getUserPhoto());
                    postDetailsActivity.putExtra("venue", mData.get(position).getVenue());

                    //postDetailsActivity.putExtra("userName", mData.get(position).getUserName());

                    long timeStamp = (long) mData.get(position).getTimeStamp();
                    postDetailsActivity.putExtra("postDate", timeStamp);
                    mContext.startActivity(postDetailsActivity);

                }
            });
        }

    }
}

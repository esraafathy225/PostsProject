package com.company.postsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context context;
    ArrayList<UserDetails> users;

    public UserAdapter(Context context,ArrayList<UserDetails> users){
        this.context=context;
        this.users=users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.custom_row,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserDetails userDetails=users.get(position);
        holder.name.setText(userDetails.getName());
        holder.likes.setText("Liked by: "+userDetails.getLikes());

        Glide.with(context).load(userDetails.getImageUrl()).into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,likes;
        ImageView profileImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.txt1);
            likes=itemView.findViewById(R.id.txt2);
            profileImage=itemView.findViewById(R.id.img);
        }
    }
}

package com.example.finalblogerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BlogerAdapter extends RecyclerView.Adapter<BlogerAdapter.BlogerViewHolder> {

    int globalPosition=0;
    ArrayList<PostModel> pList;
    Context ctx;

    public BlogerAdapter(ArrayList<PostModel> postList, Context ctx) {
        this.pList = postList;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public BlogerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.post_items, parent, false);
        return new BlogerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogerViewHolder holder, int position) {
        globalPosition=position;

        holder.postTitle.setText(pList.get(position).getTitle());
        holder.postDesc.setText(pList.get(position).getContent());
        holder.postUser.setText(pList.get(position).getUserName());
        holder.postTime.setText(pList.get(position).getTimeStamp());
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(pList.get(position).toString());
    }

    public class BlogerViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        TextView postTitle,postDesc,postUser,postTime;


        public BlogerViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage=itemView.findViewById(R.id.post_image);
            postTitle=itemView.findViewById(R.id.post_title);
            postDesc=itemView.findViewById(R.id.post_desc);
            postUser=itemView.findViewById(R.id.post_user);
            postTime=itemView.findViewById(R.id.p_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent postDetailActivity = new Intent(ctx, PostDetaliActivity.class);
                    int position = globalPosition;

                    Toast.makeText(ctx,getItemId()+"Bloger",Toast.LENGTH_LONG).show();

                    postDetailActivity.putExtra("pID",pList.get(position).getpID());
                    postDetailActivity.putExtra("currentUserID",pList.get(position).getCurrentUser());
                   postDetailActivity.putExtra("uName",pList.get(position).getUserName());
                    postDetailActivity.putExtra("pContent",pList.get(position).getContent());
                    postDetailActivity.putExtra("postCategory",pList.get(position).getCategory());
                    postDetailActivity.putExtra("postDate",pList.get(position).getTimeStamp()) ;
                    //postDetailActivity.putExtra("postImage",pData.get(position).getPicture());
                    //postDetailActivity.putExtra("userPhoto",pData.get(position).getUserPhoto());

                   ctx.startActivity(postDetailActivity);

                }
            });
        }
    }

}

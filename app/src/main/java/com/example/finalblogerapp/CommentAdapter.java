package com.example.finalblogerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder>{

    private Context mContext;

    public CommentAdapter(ArrayList<CommentModel> mData,Context mContext) {
        this.mData = mData;
        this.mContext = mContext;    }

    private ArrayList<CommentModel> mData;

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.comment_template, parent, false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getUname());
        holder.tv_content.setText(mData.get(position).getComText());
        holder.tv_date.setText(mData.get(position).getComTime());

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

}

class CommentViewHolder extends RecyclerView.ViewHolder
{
    TextView tv_name, tv_content, tv_date;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_name = itemView.findViewById(R.id.comment_username);
        tv_date = itemView.findViewById(R.id.comment_date);
        tv_content = itemView.findViewById(R.id.comment_content);
    }
}


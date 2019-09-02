package com.example.instahappy.paid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instahappy.R;
import com.example.instahappy.paid.MediaInfo;
import com.example.instahappy.paid.photos.Photo;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    public ArrayList<Photo> images;
    public ImageAdapter(ArrayList<Photo> items) {
        images = items;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        holder.photo= images.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final ImageView imageView;
        public Photo photo;

        public ViewHolder(View view){
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.item_image);

        }
    }
}
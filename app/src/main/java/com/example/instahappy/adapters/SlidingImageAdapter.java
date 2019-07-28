package com.example.instahappy.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.instahappy.R;
import com.example.instahappy.model.Photo;

import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private final Context context;
    private final List<Photo> imageUrls;

    @Override
    public int getCount() {
        return imageUrls.size();
    }


    public SlidingImageAdapter(Context context,List<Photo> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_photo, view, false);
        ImageView imageView = itemView.findViewById(R.id.image);
        TextView authorTv = itemView.findViewById(R.id.item_photo_author);

        Photo model = imageUrls.get(position);

        Glide.with(context)
                .load(model.getUrls().getRegular())
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(imageView);


        authorTv.setText(context.getString(R.string.by_concat_string) + imageUrls.get(position).user.name + context.getString(R.string.on_Unsplash_concat));
        view.addView(itemView);

        return itemView;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }



}


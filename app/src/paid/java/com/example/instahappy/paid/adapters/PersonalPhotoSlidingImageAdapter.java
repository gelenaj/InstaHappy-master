package com.example.instahappy.paid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.instahappy.R;
import com.example.instahappy.paid.model.PersonalPhoto;

import java.util.List;

public class PersonalPhotoSlidingImageAdapter extends PagerAdapter {
    private final Context context;
    private final List<PersonalPhoto> images;

    @Override
    public int getCount() {

        Log.d("Personal Afdapter", String.valueOf(images.size()));
        return images.size();
    }

    public PersonalPhotoSlidingImageAdapter(Context context, List<PersonalPhoto> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_personal_photo, view, false);
        ImageView imageView = itemView.findViewById(R.id.personal_image);

        PersonalPhoto personalPhoto = images.get(position);


        Glide.with(context)
                .load(personalPhoto.getImageUrl())
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_background))
                .into(imageView);


        view.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}


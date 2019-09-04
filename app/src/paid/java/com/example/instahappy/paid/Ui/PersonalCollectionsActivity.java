package com.example.instahappy.paid.Ui;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.instahappy.R;
import com.example.instahappy.paid.adapters.PersonalPhotoSlidingImageAdapter;
import com.example.instahappy.paid.model.PersonalPhoto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalCollectionsActivity extends AppCompatActivity {
    private List<PersonalPhoto> photos;
    private DatabaseReference mDatabaseRef;
    private TextView txtError;
    private String TAG = "PersonalCollectionsActivity.class";
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    PersonalPhotoSlidingImageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_category_collection);
        String personalCollectionsLabel = getIntent().getStringExtra("personalCollectionId");
        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitle("Personal Collection");
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "starting personalCollection ");
        errorLayout = findViewById(R.id.personal_error_layout);

        txtError = findViewById(R.id.no_photos_tv);
        photos = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.child("user-images").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PersonalPhoto pp = ds.getValue(PersonalPhoto.class);
                    Log.d(TAG, "pp.imageUrl =" + pp.getImageUrl());
                    Log.d(TAG, "pp.getCategpry =" + pp.getCategory());

                    if (personalCollectionsLabel.equals("Kids")) {
                        if (pp.getCategory().equals("Kids")) {
                            photos.add(pp);

                        }
                    } else if (personalCollectionsLabel.equals("Pets")) {
                        if (pp.getCategory().equals("Pets")) {
                            photos.add(pp);
                        }
                    } else if (personalCollectionsLabel.equals("Nature")) {
                        if (pp.getCategory().equals("Nature")) {
                            photos.add(pp);

                        }
                    } else {
                        if (pp.getCategory().equals("Love")) {
                            photos.add(pp);
                        }
                    }

                    if(photos.size() == 0){
                        errorLayout.setVisibility(View.VISIBLE);
                        txtError.setVisibility(View.VISIBLE);
                    }
                }

                ViewPager viewPager = findViewById(R.id.personal_view_pager);
                adapter = new PersonalPhotoSlidingImageAdapter(PersonalCollectionsActivity.this, photos);
                Log.d(TAG, "size of photos = " + photos.size());
                viewPager.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


    }
}
package com.example.instahappy.paid.Ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.example.instahappy.R;
import com.example.instahappy.model.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Tab2Fragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private CoordinatorLayout coordinator;
    private URI mMediaUri;
    String currentPhotoPath;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public Tab2Fragment() {

    }

    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPref = getActivity().getPreferences(MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        Button kidsBtn = rootView.findViewById(R.id.personal_kids_button_tab2);
        Button petsBtn = rootView.findViewById(R.id.personal_pets_button_tab2);
        Button natureBtn = rootView.findViewById(R.id.personal_nature_button_tab2);
        Button loveBtn = rootView.findViewById(R.id.personal_love_button_tab2);
        FloatingActionButton camera_fab = rootView.findViewById(R.id.camera_fab);
        FloatingActionButton gallery_fab = rootView.findViewById(R.id.image_gallery_fab);
        coordinator = rootView.findViewById(R.id.coordinatorLayout);
        kidsBtn.setOnClickListener(this);
        petsBtn.setOnClickListener(this);
        natureBtn.setOnClickListener(this);
        loveBtn.setOnClickListener(this);

        gallery_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!sharedPref.getBoolean("isLoggedIn", false)) {
                    Log.d("TabFragment", "User does not exists");
                    Toast.makeText(getContext(), "Please log in to continue.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("UploadPhotoActivity", "User exists");
                    Intent intent = new Intent(getActivity(), UploadPhotoActivity.class);
                    startActivity(intent);

                }

            }
        });

        camera_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.example.instahappy.paid",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
        return rootView;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private URI getOutputMediaFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {
            return null;
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Context mContext = getContext();

        switch (v.getId()) {
            case R.id.personal_kids_button_tab2:
                intent.putExtra("personalCollectionId", Constants.personalKidsCollectionLabel);
                intent.setClass(mContext, PersonalCollectionsActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.personal_pets_button_tab2:
                intent.putExtra("personalCollectionId", Constants.personalPetsCollectionLabel);
                intent.setClass(mContext, PersonalCollectionsActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.personal_nature_button_tab2:
                intent.putExtra("personalCollectionId", Constants.personalNatureCollectionLabel);
                intent.setClass(mContext, PersonalCollectionsActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.personal_love_button_tab2:
                intent.putExtra("personalCollectionId", Constants.personalLoveCollectionLabel);
                intent.setClass(mContext, PersonalCollectionsActivity.class);
                mContext.startActivity(intent);
                break;
        }


    }
}
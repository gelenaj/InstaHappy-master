package com.example.instahappy.paid.Ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.instahappy.R;
//import com.example.instahappy.paid.ImagePresenter;
import com.example.instahappy.paid.PersonalPhoto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class GalleryActivity extends AppCompatActivity {
    static final int REQUEST_GALLERY_PHOTO = 102;
    private static final int PICK_IMAGE_REQUEST = 1;
    static String[] permissions = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ImageView mUploadButton;
    private Uri mImageUri;
   // private ImagePresenter mPresenter;
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private EditText mEditTextFileName;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public static final String TAG = "UploadActivity";
    public static final String PHOTOS_FIREBASE_KEY = "photos";
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        imageView = findViewById(R.id.image);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        openFileChooser();
        mProgressBar = findViewById(R.id.progress_bar);
        mUploadButton = findViewById(R.id.uploadBtn);
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = database.getReference("uploads");
        FirebaseUser user = mAuth.getCurrentUser();
    }

    @Override

    public void onBackPressed() {

        Fragment fragment = new Tab2Fragment();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.tab2_framelayout, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }


    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void upload() {
        //final String userId = getUid;

        if(mImageUri !=null) {
            StorageReference fileReference = mStorageRef.child(
                    System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000);
                            Toast.makeText(GalleryActivity.this, "personalPhoto successful", Toast.LENGTH_SHORT).show();

                            String key = mDatabaseRef.child("uploads").push().getKey();

                            PersonalPhoto personalPhoto = new PersonalPhoto(
                                    mAuth.getUid(),mAuth.getCurrentUser(),
                                    mEditTextFileName.getText().toString().trim(),
                                    mStorageRef.getDownloadUrl().toString());

                            String uploadId = myRef.push().getKey();

                            myRef.child(uploadId).setValue(personalPhoto);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GalleryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageView);
        }
    }
}

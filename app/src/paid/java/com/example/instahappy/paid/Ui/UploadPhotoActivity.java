package com.example.instahappy.paid.Ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.instahappy.R;
//import com.example.instahappy.paid.ImagePresenter;
import com.example.instahappy.paid.model.PersonalPhoto;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadPhotoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    static final int REQUEST_GALLERY_PHOTO = 102;
    private static final int PICK_IMAGE_REQUEST = 1;
    static String[] permissions = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ImageView mUploadButton;
    private Uri mImageFile;
   // private ImagePresenter mPresenter;
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private EditText mEditTextFileName;
    private TextView uploadStatusTv;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public static final String TAG = "UploadActivity";
    public static final String PHOTOS_FIREBASE_KEY = "photos";
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    LinearLayout uploadName;
    LinearLayout uploadDetails;
    private SharedPreferences sharedPref;
    boolean isLoggedIn;
    SharedPreferences.Editor editor;

    private Task<Uri> mUploadTask;
    FirebaseUser user;
    Button chooseImageBtn;


    Spinner spin;
    String catName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        imageView = findViewById(R.id.image_view);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        uploadStatusTv = findViewById(R.id.upload_status_tv);
        uploadDetails = findViewById(R.id.uploadFileDetails);
        uploadName = findViewById(R.id.uploadFileDetailsName);
        chooseImageBtn = findViewById(R.id.button_choose_image);
        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = database.getReference("uploads");

        mAuth= FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mProgressBar = findViewById(R.id.progress_bar);
        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  openFileChooser();
                                              }
                                          });

        mUploadButton = findViewById(R.id.uploadBtn);
            mUploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("UploadPhotoActivity", "User exists");
                    upload();



                }
            });


        spin = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
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
        if(mImageFile !=null) {
            mProgressBar.setVisibility(View.VISIBLE);

//            final StorageReference imageRef = mStorageRef.child("images/"+
//                    System.currentTimeMillis() + "." + getFileExtension(mImageFile));
//            Log.d(TAG, "wtf am i saving = "+ imageRef);
//
//            FirebaseStorage storage = FirebaseStorage.getInstance();
//            StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images"+imageRef.getPath());
//            Log.d(TAG, "last try storage ref = "+ storageRef);
//
//            Log.d(TAG, "image url = "+ imageRef.getPath() + " and " + imageRef + imageRef.getDownloadUrl() + imageRef.getName());


            //Uri file = Uri.fromFile(new File("https://firebasestorage.googleapis.com/v0/b/happy305-248321.appspot.com/o/uploads" ));
            StorageReference riversRef = mStorageRef.child("images/"+System.currentTimeMillis() + "." + getFileExtension(mImageFile));
            mUploadTask = riversRef.putFile(mImageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    Log.d("TAG", "oh shit" + riversRef.getDownloadUrl());


                    return riversRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downUri = task.getResult();

                        Log.d(TAG, "onComplete: Url: "+ downUri.toString());
                        String key = mDatabaseRef.child("uploads").push().getKey();


                        PersonalPhoto personalPhoto = new PersonalPhoto(
                                user.getUid(),
                                mEditTextFileName.getText().toString().trim(),
                                downUri.toString(),
                                catName);

                        Log.d("TAG","!!!this is it = " +  riversRef.getDownloadUrl());


                        Map<String, Object> imageValues = personalPhoto.toMap();

                        Map<String, Object> imageUpdates =new HashMap<>();

                        imageUpdates.put("/user-images/" + user.getUid() +"/"+ key, imageValues);

                        mDatabaseRef.updateChildren(imageUpdates);

                        uploadDetails.setVisibility(View.INVISIBLE);
                        uploadName.setVisibility(View.INVISIBLE);

                        finish();
                    }
                }
            })
            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    mProgressBar.setProgress(0);
                                }
                            }, 4000);
                            uploadStatusTv.setVisibility(View.VISIBLE);

                }
            });

    }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageFile = data.getData();
            Log.d(TAG, "download url from Upload activity"+ mStorageRef.getDownloadUrl());

            Picasso.get().load(mImageFile).into(imageView);


        }else{
            Log.d("UploadPhotoActivity", "Error with Result" + resultCode);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        catName = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

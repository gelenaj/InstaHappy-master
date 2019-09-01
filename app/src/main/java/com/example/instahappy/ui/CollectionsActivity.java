package com.example.instahappy.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.instahappy.R;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.instahappy.adapters.SlidingImageAdapter;
import com.example.instahappy.api.ApiClient;
import com.example.instahappy.api.IUnsplashService;
import com.example.instahappy.model.Photo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsActivity extends AppCompatActivity  {
    private List<Photo> photos = new ArrayList<>();
    private TextView txtError;
    private String TAG= Collections.class.getSimpleName();
    private ProgressBar progressBar;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.category_collection);
        super.onCreate(savedInstanceState);
        progressBar = findViewById(R.id.main_progress);
        errorLayout = findViewById(R.id.error_layout);
        Button btnRetry = findViewById(R.id.error_btn_retry);
        txtError = findViewById(R.id.error_txt_cause);

        int collectionsId = getIntent().getIntExtra("btnClickedId", 0);
        LoadJson(collectionsId);

        btnRetry.setOnClickListener(view -> LoadJson(collectionsId));
        Log.d("CollectionsActivity**", String.valueOf(collectionsId));
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void LoadJson(int collectionsId) {
        hideErrorView();
        if (isNetworkAvailable()) {
            Toast.makeText(CollectionsActivity.this, "in loadJson", Snackbar.LENGTH_LONG).show();

            IUnsplashService apiInterface = ApiClient.getApiClient().create(IUnsplashService.class);
            Call<List<Photo>> call;
            call = apiInterface.getPhotosFromService(collectionsId);

            call.enqueue(new Callback<List<Photo>>() {
                @Override
                public void onResponse(@NonNull Call<List<Photo>> call, @NonNull Response<List<Photo>> response) {
                    Toast.makeText(CollectionsActivity.this, "in on Response***", Snackbar.LENGTH_LONG).show();
                        if (response.isSuccessful() && response.body() != null) {
                            progressBar.setVisibility(View.GONE);
                            hideErrorView();
                            if (photos.isEmpty()) {
                                photos.clear();
                            }
                            photos = response.body();
                            ViewPager viewPager = findViewById(R.id.view_pager);

                            SlidingImageAdapter adapter = new SlidingImageAdapter(getApplicationContext(), photos);
                            viewPager.setAdapter(adapter);
                            Toast.makeText(CollectionsActivity.this, "success", Snackbar.LENGTH_LONG).show();

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            String error;
                            switch (response.code()){
                                case 404:
                                    error = "HTTP ERROR";
                                    break;
                                case 500:
                                    error = "There is a problem with the server.";
                                    break;

                                    default:
                                    error="An unknown error0";
                                    break;
                            }
                            showErrorView(error);
                        }
                }
                @Override
                public void onFailure(@NonNull Call<List<Photo>> call, @NonNull Throwable t) {
                        showErrorView("Please connect to the internet and try again.");
                    }
            });

        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void showErrorView(String msg) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            txtError.setText(msg);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}

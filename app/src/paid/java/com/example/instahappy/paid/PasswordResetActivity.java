package com.example.instahappy.paid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.instahappy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText userEmail;
    Button resetBtn;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getString(R.string.reset_password));
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.loadingReset);
        resetBtn = findViewById(R.id.resetButton);
        userEmail = findViewById(R.id.emailEditText);
        resetBtn = findViewById(R.id.resetButton);

        firebaseAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    Toast.makeText(PasswordResetActivity.this,"Reset instructions sent to your email", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(PasswordResetActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
            }
        });
    }
}

package com.example.instahappy.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.instahappy.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class AlertDialogFragment extends DialogFragment {
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private static final int SIGN_IN_REQUEST = 9001;
    private AlertDialogListner listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("InstaHappy want to use \"google.com\" to Sign In.\nThis allows the app and website to share information about you.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isContinueTrue = true;
                        listener.permissionGranted(isContinueTrue);

                    }
                })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isContinueFalse=false;
                listener.permissionGranted(isContinueFalse);
            }
        });

        return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (AlertDialogListner) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement AlertDialogListener");
        }

    }

    public interface AlertDialogListner{
        void permissionGranted(boolean isContinue);
    }
}

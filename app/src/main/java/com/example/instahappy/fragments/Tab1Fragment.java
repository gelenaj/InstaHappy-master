package com.example.instahappy.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.example.instahappy.CollectionsActivity;
import com.example.instahappy.R;
import com.example.instahappy.model.Constants;

public class Tab1Fragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Tab1Fragment() {
    }

    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_tab1, container, false);

        FrameLayout tab1 = rootView.findViewById(R.id.tab1_framelayout);
        Button kidsBtn = rootView.findViewById(R.id.kids_button_tab2);
        Button natureBtn = rootView.findViewById(R.id.nature_button);
        Button petsBtn = rootView.findViewById(R.id.pets_button_tab2);
        Button loveBtn = rootView.findViewById(R.id.love_button);

        kidsBtn.setOnClickListener(this);
        natureBtn.setOnClickListener(this);
        petsBtn.setOnClickListener(this);
        loveBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Context mContext = getContext();

        switch(v.getId()){
            case R.id.kids_button_tab2:
                intent.putExtra("btnClickedId", Constants.kidsCollectionId);
                break;
            case R.id.pets_button_tab2:
                intent.putExtra("btnClickedId", Constants.petCollectionId);
                break;
            case R.id.nature_button:
                intent.putExtra("btnClickedId", Constants.natureCollectionId);
                break;
            case R.id.love_button:
                intent.putExtra("btnClickedId", Constants.loveCollectionId);
                break;
        }

        intent.setClass(mContext, CollectionsActivity.class);
        mContext.startActivity(intent);

    }

}

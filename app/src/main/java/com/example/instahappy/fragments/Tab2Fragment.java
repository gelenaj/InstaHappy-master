package com.example.instahappy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.instahappy.R;


public class Tab2Fragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Tab2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        Button kidsBtn = rootView.findViewById(R.id.kids_button_tab2);
        Button petsBtn = rootView.findViewById(R.id.pets_button_tab2);
        Button natureBtn = rootView.findViewById(R.id.nature_button_tab2);
        Button loveBtn = rootView.findViewById(R.id.love_button_tab2);

        kidsBtn.setOnClickListener(v -> Toast.makeText(getActivity(),"Please upgrade",Toast.LENGTH_LONG).show());
        petsBtn.setOnClickListener(v -> Toast.makeText(getActivity(),"Please upgrade",Toast.LENGTH_LONG).show());

        natureBtn.setOnClickListener(v -> Toast.makeText(getActivity(),"Please upgrade",Toast.LENGTH_LONG).show());
        loveBtn.setOnClickListener(v -> Toast.makeText(getActivity(),"Please upgrade",Toast.LENGTH_LONG).show());
        return rootView;
    }

}
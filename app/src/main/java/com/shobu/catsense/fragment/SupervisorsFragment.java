package com.shobu.catsense.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shobu.catsense.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupervisorsFragment extends Fragment {


    public SupervisorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supervisors, container, false);
    }

}

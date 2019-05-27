package com.shobu.catsense.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shobu.catsense.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {

    FloatingActionButton addActivityButton;


    public ActivitiesFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_activities, container, false);
        getActivity().setTitle("Activities");

        addActivityButton = view.findViewById(R.id.fab_add_activity);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ShowAddActivityDialog();
            }
        });

        return view;
    }

    private void ShowAddActivityDialog()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddActivityDialogFullScreenFragment addActivity = new AddActivityDialogFullScreenFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.content_main, addActivity).addToBackStack(null).commit();

    }

}

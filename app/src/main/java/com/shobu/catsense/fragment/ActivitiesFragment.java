package com.shobu.catsense.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shobu.catsense.R;
import com.shobu.catsense.adapter.ActivityRecyclerAdapter;
import com.shobu.catsense.helper.Constants;
import com.shobu.catsense.model.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment
{
    SharedPreferences prefs;

    FloatingActionButton addActivityButton;

    ArrayList<Activity> allActivityList;

    RecyclerView activityRecyclerView;
    ActivityRecyclerAdapter activityRecyclerAdapter;
    ProgressDialog progressDialog;

    public ActivitiesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        getActivity().setTitle("Activities");
        activityRecyclerView = view.findViewById(R.id.recycler_view_activities);
        addActivityButton = view.findViewById(R.id.fab_add_activity);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddActivityDialog();
            }
        });

        prefs = getActivity().getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetAllActivity();
    }

    private void GetAllActivity() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Activities...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    GetActivityFromServer();
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.v("LOGIN", e.getMessage().toString());
                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void GetActivityFromServer()
    {
        String userId = prefs.getString("user_id","");


        String url = Constants.BASE_URL + Constants.GET_ACTIVITY_BY_SUPERVISOR + userId;
        StringRequest activityStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try
                {
                    allActivityList = new ArrayList<>();
                    JSONArray responseArray = new JSONArray(response);

                    for (int i = 0; i < responseArray.length(); i++)
                    {
                        JSONObject activityJsonObject = responseArray.getJSONObject(i);
                        Activity activity = new Activity();
                        activity.setId(activityJsonObject.getString("idActivity"));
                        activity.setName(activityJsonObject.getString("ActivityName"));

                        allActivityList.add(activity);
                    }
                    Log.v("LOGIN", Integer.toString(allActivityList.size()));

                    activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), allActivityList, new ActivityRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Activity activity) {
                            Toast.makeText(getActivity(), "On Click", Toast.LENGTH_SHORT).show();
                        }
                    },
                            new ActivityRecyclerAdapter.OnLongClickListener() {
                                @Override
                                public void onLongClick(Activity activity) {
                                    Toast.makeText(getActivity(), "On Long click", Toast.LENGTH_SHORT).show();
                                }
                            });

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    activityRecyclerView.setLayoutManager(layoutManager);
                    activityRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    activityRecyclerView.setAdapter(activityRecyclerAdapter);

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Internet Issue. Please try again later.", Toast.LENGTH_SHORT).show();
                        Log.v("LOGIN", error.getMessage());
                    }
                });

        RequestQueue activityRequestQueue = Volley.newRequestQueue(getActivity());
        activityRequestQueue.add(activityStringRequest);

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

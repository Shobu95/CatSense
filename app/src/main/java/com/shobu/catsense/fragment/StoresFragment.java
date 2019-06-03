package com.shobu.catsense.fragment;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.shobu.catsense.adapter.StoreExpandableRecyclerAdapter;
import com.shobu.catsense.helper.Constants;
import com.shobu.catsense.model.Store;
import com.shobu.catsense.model.StoreBranch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoresFragment extends Fragment
{
    RecyclerView storesRecyclerView;
    StoreExpandableRecyclerAdapter storeAdapter;
    ProgressDialog progressDialog;

    public StoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        getActivity().setTitle("Stores");

        storesRecyclerView = view.findViewById(R.id.list_stores);
        storesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        GetAllStores();




        return view;
    }

    private void GetAllStores()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading stores...");
        progressDialog.show();

        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                try
                {
                    GetStoresFromServer();
                }
                catch(Exception e)
                {
                    progressDialog.dismiss();
                    Log.v("LOGIN",e.getMessage().toString());
                }
                return null;
            }
        }.execute(null,null,null);

    }

    private void GetStoresFromServer()
    {
        String url = Constants.BASE_URL + Constants.GET_STORES;
        StringRequest storesStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                progressDialog.dismiss();

                try
                {
                    JSONArray responseArray = new JSONArray(response);
                    List<Store> storeList = new ArrayList<>();
                    for(int i = 0; i < responseArray.length(); i++)
                    {
                        JSONObject storeObject = responseArray.getJSONObject(i);
                        String storeID = storeObject.getString("idStore");
                        String storeName = storeObject.getString("StoreName");
                        List<StoreBranch> storeBranches = new ArrayList<>();

                        JSONArray branchArray = storeObject.getJSONArray("branches");
                        for(int j = 0; j < branchArray.length(); j++)
                        {
                            JSONObject branchObject = branchArray.getJSONObject(j);

                            StoreBranch storeBranch = new StoreBranch();
                            storeBranch.setStoreBranchID(branchObject.getString("idStoreBranch"));
                            storeBranch.setStoreId(storeID);
                            storeBranch.setBranchName(branchObject.getString("BranchName"));
                            storeBranch.setCityID(branchObject.getString("City"));
                            storeBranch.setCityName(branchObject.getString("cityName"));

                            storeBranches.add(storeBranch);
                        }

                        Store store = new Store(storeID, storeName, storeBranches);
                        storeList.add(store);

                    }

                    StoreExpandableRecyclerAdapter storeAdapter = new StoreExpandableRecyclerAdapter(storeList);
                    storesRecyclerView.setAdapter(storeAdapter);
                }
                catch(Exception e)
                {
                    Toast.makeText(getActivity(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN",e.getMessage());
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Internet Issue. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN",error.getMessage());
                }
            })
        {

        };

        RequestQueue storesRequestQueue = Volley.newRequestQueue(getActivity());
        storesRequestQueue.add(storesStringRequest);
    }

}

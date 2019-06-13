package com.shobu.catsense.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoresFragment extends Fragment
{
    RecyclerView storesRecyclerView;
    StoreExpandableRecyclerAdapter storeAdapter;
    ProgressDialog progressDialog;
    FloatingActionButton addStoreButton;

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

        addStoreButton = view.findViewById(R.id.fab_add_store);
        addStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddStoreDialog();
            }
        });

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
                        if (branchArray.length() > 0)
                        {
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
                        }
                        else
                        {
                            StoreBranch storeBranch = new StoreBranch();
                            storeBranch.setStoreBranchID("null");
                            storeBranch.setStoreId("null");
                            storeBranch.setBranchName("No Branches");
                            storeBranch.setCityID("null");
                            storeBranch.setCityName("null");
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

    private void ShowAddStoreDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_add_store, null);
        builder.setView(dialogView);
        final AlertDialog addStoreDialog = builder.create();
        addStoreDialog.setTitle("Add Store");

        final EditText StoreNameText = dialogView.findViewById(R.id.edt_store_name);
        final Button CancelStoreButton = dialogView.findViewById(R.id.btn_cancel);
        final Button AddStoreButton = dialogView.findViewById(R.id.btn_add_store);
        addStoreDialog.show();

        CancelStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStoreDialog.dismiss();
            }
        });

        AddStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String storeName = StoreNameText.getText().toString();
                AddStoreName(storeName);
                addStoreDialog.dismiss();
            }
        });
    }


    private void AddStoreName(String storeName)
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final HashMap<String, String> params = new HashMap<>();
        params.put("StoreName", storeName);


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try
                {
                    AddStoreNameToServer(params);
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.v("ADDSTORE", e.getMessage());

                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void AddStoreNameToServer(final HashMap<String, String> param)
    {
        String url = Constants.BASE_URL + Constants.ADD_STORE;
        StringRequest addStoreStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progressDialog.dismiss();
                try
                {
                    JSONObject responseObject = new JSONObject(response);
                    String status = responseObject.getString("status");
                    if(status.equals("success"))
                    {
                        Toast.makeText(getActivity(), "Store Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else if (status.equals("error"))
                    {
                        Toast.makeText(getActivity(), "Store name already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getActivity(), "An error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                    Log.v("STORE", e.getMessage());
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Internet Issue. Please try again later.", Toast.LENGTH_SHORT).show();
                Log.v("STORE", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = param;
                return params;
            }
        };

        RequestQueue addStoreRequestQueue = Volley.newRequestQueue(getActivity());
        addStoreRequestQueue.add(addStoreStringRequest);
    }

}

package com.shobu.catsense.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.shobu.catsense.adapter.BrandsRecyclerAdapter;
import com.shobu.catsense.helper.Constants;
import com.shobu.catsense.model.Brand;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrandsFragment extends Fragment {
    FloatingActionButton addBrand;
    ArrayList<Brand> allBrandsList;

    RecyclerView brandsRecyclerView;
    BrandsRecyclerAdapter brandsRecyclerAdapter;
    ProgressDialog progressDialog;


    public BrandsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brands, container, false);
        getActivity().setTitle("Brands");
        brandsRecyclerView = view.findViewById(R.id.recycler_view_brands);
        addBrand = view.findViewById(R.id.fab_add_brand);

        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddBrandDialog();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetAllBrands();
    }


    private void GetAllBrands() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Brands...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    GetBrandsFromServer();
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.v("LOGIN", e.getMessage().toString());
                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void GetBrandsFromServer() {
        String url = Constants.BASE_URL + Constants.GET_BRANDS;
        StringRequest brandStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    allBrandsList = new ArrayList<>();
                    JSONArray responseArray = new JSONArray(response);

                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject brandJsonObject = responseArray.getJSONObject(i);
                        Brand brand = new Brand();
                        brand.setId(brandJsonObject.getString("idBrand"));
                        brand.setName(brandJsonObject.getString("brandName"));

                        allBrandsList.add(brand);
                    }
                    Log.v("LOGIN", Integer.toString(allBrandsList.size()));

                    brandsRecyclerAdapter = new BrandsRecyclerAdapter(getActivity(), allBrandsList, new BrandsRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Brand brand) {
                            Toast.makeText(getActivity(), "On Click", Toast.LENGTH_SHORT).show();
                        }
                    },
                            new BrandsRecyclerAdapter.OnLongClickListener() {
                                @Override
                                public void onLongClick(Brand brand) {
                                    Toast.makeText(getActivity(), "On Long click", Toast.LENGTH_SHORT).show();
                                }
                            });

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    brandsRecyclerView.setLayoutManager(layoutManager);
                    brandsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    brandsRecyclerView.setAdapter(brandsRecyclerAdapter);

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

        RequestQueue brandRequestQueue = Volley.newRequestQueue(getActivity());
        brandRequestQueue.add(brandStringRequest);

    }

    private void ShowAddBrandDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_add_brand, null);
        builder.setView(dialogView);
        final AlertDialog addBrandDialog = builder.create();
        addBrandDialog.setTitle("Add Brand");

        final EditText BrandNameText = dialogView.findViewById(R.id.edt_brand_name);
        final Button CancelBrandButton = dialogView.findViewById(R.id.btn_cancel);
        final Button AddBrandButton = dialogView.findViewById(R.id.btn_add_brand_name);
        addBrandDialog.show();

        CancelBrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBrandDialog.dismiss();
            }
        });

        AddBrandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String brandName = BrandNameText.getText().toString();
                AddBrandName(brandName);
                addBrandDialog.dismiss();
            }
        });

    }

    private void AddBrandName(String brandName) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final HashMap<String, String> params = new HashMap<>();
        params.put("brandName", brandName);


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    AddBrandNameToServer(params);
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.v("ADDBRAND", e.getMessage());

                }
                return null;
            }
        }.execute(null, null, null);
    }

    private void AddBrandNameToServer(final HashMap<String, String> param) {
        String url = Constants.BASE_URL + Constants.ADD_BRAND;
        StringRequest addBrandStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject responseObject = new JSONObject(response);
                    String status = responseObject.getString("status");

                    if (status.equals("success")) {
                        Toast.makeText(getActivity(), "Brand added successfully", Toast.LENGTH_SHORT).show();
                        GetAllBrands();
                    } else if (status.equals("error")) {
                        Toast.makeText(getActivity(), "Brand name already exists", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "An error occurred. Please try again later", Toast.LENGTH_SHORT).show();
                    Log.v("brand", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Internet Issue. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = param;
                return params;
            }
        };

        RequestQueue addBrandRequestQueue = Volley.newRequestQueue(getActivity());
        addBrandRequestQueue.add(addBrandStringRequest);


    }


}

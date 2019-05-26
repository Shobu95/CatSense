package com.shobu.catsense.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.shobu.catsense.helper.Constants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{

    Button LoginButton;
    EditText emailText, passwordText;

    ProgressDialog pDialog;
    HashMap<String, String> param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        emailText = findViewById(R.id.edt_email);
        passwordText = findViewById(R.id.edt_password);
        LoginButton = findViewById(R.id.btn_login);

        LoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateFields();
            }
        });
    }


    private void ValidateFields()
    {
        if(emailText.getText().toString().equals(""))
        {
            emailText.setError("Please enter email");
        }
        else if (passwordText.getText().toString().equals(""))
        {
            passwordText.setError("Please enter password");
        }
        else
        {
            LoginUser();
        }
    }


    private void LoginUser()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        param = new HashMap<>();
        param.put("username",emailText.getText().toString());
        param.put("password",passwordText.getText().toString());

        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                try
                {
                    SendLoginDataToServer(param);
                }
                catch(Exception e)
                {
                    pDialog.dismiss();
                    Log.v("LOGIN",e.getMessage().toString());
                }
                return null;
            }
        }.execute(null,null,null);
    }

    private void SendLoginDataToServer(final HashMap<String,String> param)
    {
        String url = Constants.BASE_URL + Constants.LOGIN;
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                pDialog.dismiss();
                try
                {
                    JSONObject responseObject = new JSONObject(response);
                    String status = responseObject.getString("status");

                    if(status.equals("success"))
                    {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else if (status.equals("error"))
                    {
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(LoginActivity.this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN",e.getMessage());
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Internet Issue. Please try again later.", Toast.LENGTH_SHORT).show();
                Log.v("LOGIN",error.getMessage());
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

        RequestQueue loginRequestQueue = Volley.newRequestQueue(this);
        loginRequestQueue.add(loginStringRequest);
    }
}

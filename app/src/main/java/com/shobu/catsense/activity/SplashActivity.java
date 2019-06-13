package com.shobu.catsense.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shobu.catsense.R;
import com.shobu.catsense.helper.Constants;

public class SplashActivity extends AppCompatActivity
{
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        prefs = getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);
        final String loginStatus = prefs.getString("login_status","");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if(loginStatus.equals("true"))
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            }
        },3000);

    }
}

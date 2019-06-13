package com.shobu.catsense.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shobu.catsense.R;
import com.shobu.catsense.fragment.ActivitiesFragment;
import com.shobu.catsense.fragment.BrandsFragment;
import com.shobu.catsense.fragment.DashboardFragment;
import com.shobu.catsense.fragment.GroupsFragment;
import com.shobu.catsense.fragment.StoresFragment;
import com.shobu.catsense.fragment.SupervisorsFragment;
import com.shobu.catsense.fragment.UsersFragment;
import com.shobu.catsense.helper.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);

        ImageView userImageView = navHeader.findViewById(R.id.img_user_image);
        TextView userNameText = navHeader.findViewById(R.id.txt_user_name);
        TextView userEmailText = navHeader.findViewById(R.id.txt_user_email);

        prefs = getSharedPreferences(Constants.USER_PREFS, Context.MODE_PRIVATE);

        String userImage = prefs.getString("user_image","");
        String userName = prefs.getString("user_name","");
        String userEmail = prefs.getString("user_email","");

        userNameText.setText(userName);
        userEmailText.setText(userEmail);


        DashboardFragment df = new DashboardFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, df);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_dashboard)
        {
            DashboardFragment df = new DashboardFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, df);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_activities)
        {
            ActivitiesFragment af = new ActivitiesFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, af);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_supervisors)
        {
            SupervisorsFragment sf = new SupervisorsFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, sf);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_brands)
        {
            BrandsFragment bf = new BrandsFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, bf);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_stores)
        {
            StoresFragment sf = new StoresFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, sf);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_users)
        {
            UsersFragment uf = new UsersFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, uf);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_groups)
        {
            GroupsFragment gf = new GroupsFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, gf);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_chat)
        {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_drafts)
        {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

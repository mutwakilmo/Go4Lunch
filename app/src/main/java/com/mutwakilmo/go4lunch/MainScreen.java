package com.mutwakilmo.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mutwakilmo.go4lunch.base.BaseActivity;
import com.mutwakilmo.go4lunch.workmates_chat.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    // Class name for Log tag
    public static final String TAG_LOG_MAIN = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        this.configureNavigationView();




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MapFragment()).commit();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main_screen;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    // --------------------
    // BottomNavigationView
    // --------------------
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_map:
                            selectedFragment = new MapFragment();
                            break;
                        case R.id.nav_list:
                            selectedFragment = new ListFragment();
                            break;
                        case R.id.nav_workmates:
                            selectedFragment = new WorkmatesFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    // ----------------------------
    // CONFIGURATION ProfileActivity
    // ----------------------------
    private void openProfileActivity() {
        Intent profileActivity = new Intent(MainScreen.this, ProfileActivity.class);
        startActivity(profileActivity);
    }

    // ----------------------------
    // CONFIGURATION ChatActivity
    // ----------------------------
    private void openChatActivity() {
        Intent profileActivity = new Intent(MainScreen.this, ChatActivity.class);
        startActivity(profileActivity);
    }




    private void configureNavigationView() {

     navigationView.setNavigationItemSelectedListener(this);

        if (this.getCurrentUser() != null) {
            // Customize image profile
            View headView = navigationView.getHeaderView(0);
            ImageView imgProfile = headView.findViewById(R.id.image_user);
            //Get picture URL from Firebase
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .override(200, 200)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgProfile);
                //Customize user data username and name
                String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ?
                        getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();
                String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ?
                        getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();

                TextView textUsername = headView.findViewById(R.id.name_user);
                textUsername.setText(username);
                TextView textEmail = headView.findViewById(R.id.email_user);
                textEmail.setText(email);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity_drawer_chat:
                drawer.closeDrawer(GravityCompat.START);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected:  your CHAT");
                openChatActivity();
                return true;
            case R.id.main_activity_drawer_lunch:
                drawer.closeDrawer(GravityCompat.START);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected:  your lunch");
                return true;
            case R.id.main_activity_drawer_settings:
                drawer.closeDrawer(GravityCompat.START);
                openProfileActivity();
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected: Settings");
                return true;
            case R.id.main_activity_drawer_logout:
                drawer.closeDrawer(GravityCompat.START);
                Log.d(TAG_LOG_MAIN, "onNavigationItemSelected: logout");
                return true;
            default:
                return false;
    }}
}


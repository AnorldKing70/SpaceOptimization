package com.example.spaceoptima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.spaceoptima.Activities.LoginActivity;
import com.example.spaceoptima.AdminBase.MainboardFragment;
import com.example.spaceoptima.AdminBase.ManageFragment;
import com.example.spaceoptima.AdminBase.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Dashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    ImageView logout,venues, modules, logs, allocating, usage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_dashboard, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainboardFragment()).commit();
        bottomMenu();

        logout = findViewById(R.id.ivLogout);
        venues = findViewById(R.id.ivVenue);
        modules = findViewById(R.id.ivModules);
        logs = findViewById(R.id.ivUserLog);
        allocating = findViewById(R.id.ivBooking);
        usage = findViewById(R.id.ivStats);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popAddPost.show();
            }
        });

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_nav_dashboard:
                        fragment = new MainboardFragment();
                        break;
                    case R.id.bottom_nav_manage:
                        fragment = new ManageFragment();
                        break;
                    case R.id.bottom_nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    public void venue(View view)
    {
        Intent venueActivity = new Intent (getApplicationContext() , VenueBuildings.class);
        startActivity(venueActivity);
    }

    public void modules(View view)
    {
        Intent modulesActivity = new Intent (getApplicationContext() , ModulesActivity.class);
        startActivity(modulesActivity);
    }

    public void logs(View view)
    {
        Intent logsActivity = new Intent (getApplicationContext() , Venue_Security_Logs.class);
        startActivity(logsActivity);
    }

    public void allocating(View view)
    {
        Intent allocatingActivity = new Intent (getApplicationContext() , SpaceValueActivity.class);
        startActivity(allocatingActivity);
    }

    public void usageStatistics(View view)
    {
        Intent usageActivity = new Intent (getApplicationContext() , VenueUsageActivity.class);
        startActivity(usageActivity);
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent loginActivity = new Intent (getApplicationContext() , LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }
}

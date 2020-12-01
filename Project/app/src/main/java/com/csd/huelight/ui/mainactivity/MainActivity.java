package com.csd.huelight.ui.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.csd.huelight.R;
import com.csd.huelight.ui.mainactivity.lightbulblist.LightBulbListFragment;
import com.csd.huelight.ui.mainactivity.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGTAG = MainActivity.class.getName();

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //navigation drawer

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        LightBulbViewModel lightBulbViewModel = ViewModelProviders.of(this).get(LightBulbViewModel.class);
        lightBulbViewModel.init();


        lightBulbViewModel.getExceptionMessage().observe(this, (exceptionMessage) -> {
//            Log.d("temp", "exeption observer called " + exceptionMessage);
            if (exceptionMessage != null && !exceptionMessage.equals("")) {
                Toast.makeText(this, exceptionMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(LOGTAG, "onNavigationItemSelected called " + item.getItemId());
        switch (item.getItemId()){
            case R.id.nav_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new LightBulbListFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new SettingsFragment()).commit();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


}
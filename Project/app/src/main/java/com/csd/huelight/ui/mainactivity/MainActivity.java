package com.csd.huelight.ui.mainactivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.csd.huelight.R;
import com.csd.huelight.data.APIManager;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGTAG = MainActivity.class.getName();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //navigation drawer
        this.navigationView = findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        //Viewmodel
        LightBulbViewModel lightBulbViewModel = ViewModelProviders.of(this).get(LightBulbViewModel.class);
        lightBulbViewModel.init(APIManager.getInstance());

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        lightBulbViewModel.getCalls().observe(this, (calls) -> {
            Log.d(LOGTAG, "calles set to " + calls);
            if (calls < 0) {
                Log.e(LOGTAG, "negative amount of calls is impossible", new IllegalArgumentException(calls + ""));
            }
            if (calls == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        lightBulbViewModel.getExceptionMessage().observe(this, (exceptionMessage) -> {
//            Log.d("temp", "exeption observer called " + exceptionMessage);
            if (exceptionMessage != null && !exceptionMessage.equals("")) {
                Toast.makeText(this, "Error: " + exceptionMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(LOGTAG, "onNavigationItemSelected called " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_list:
                if (isValidDestination(R.id.lightBulbListFragment)) {
                    // nav options to clear backstack
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.navigation_graph, true)
                            .build();
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.as_above);
                    Navigation.findNavController(this, R.id.nav_host_fragment_container).navigate(R.id.lightBulbListFragment, null, navOptions);
                       }
                break;
            case R.id.nav_settings:
                if (isValidDestination(R.id.settingsFragment)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_container).navigate(R.id.settingsFragment);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Set this to true if selecting "home" returns up by a single level in your UI rather than back to the top level or front page.
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                break;
        }

        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public boolean isValidDestination(int destination) {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment_container).getCurrentDestination().getId();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (Navigation.findNavController(this, R.id.nav_host_fragment_container).getCurrentDestination().getId() == R.id.settingsFragment) {
                if (navigationView.getCheckedItem() != null) {
                    navigationView.getCheckedItem().setChecked(false);
                }
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment_container), drawerLayout);
    }
}
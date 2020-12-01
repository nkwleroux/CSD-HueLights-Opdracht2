package com.csd.huelight.ui.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.csd.huelight.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGTAG = MainActivity.class.getName();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //navigation drawer
        navigationView = findViewById(R.id.nav_view);
        this.drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        init();

//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

/*        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();*/

//        navigationView.setCheckedItem(R.id.nav_list);

        //Viewmodel
        LightBulbViewModel lightBulbViewModel = ViewModelProviders.of(this).get(LightBulbViewModel.class);
        lightBulbViewModel.init();

        lightBulbViewModel.getExceptionMessage().observe(this, (exceptionMessage) -> {
//            Log.d("temp", "exeption observer called " + exceptionMessage);
            if (exceptionMessage != null && !exceptionMessage.equals("")) {
                Toast.makeText(this, exceptionMessage, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void init(){
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(LOGTAG, "onNavigationItemSelected called " + item.getItemId());
        switch (item.getItemId()){
            case R.id.nav_list:
                Navigation.findNavController(this,R.id.nav_host_fragment_container).navigate(R.id.lightBulbListFragment);
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new LightBulbListFragment()).commit();
                break;
            case R.id.nav_settings:
                Navigation.findNavController(this,R.id.nav_host_fragment_container).navigate(R.id.settingsFragment);
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_container, new SettingsFragment()).commit();
                break;
        }

        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

/*    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment_container), drawerLayout);
    }
}
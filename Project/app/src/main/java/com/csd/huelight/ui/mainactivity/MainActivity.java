package com.csd.huelight.ui.mainactivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.csd.huelight.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOGTAG = MainActivity.class.getName();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //navigation drawer
        this.navigationView = findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();

        //Viewmodel
        LightBulbViewModel lightBulbViewModel = ViewModelProviders.of(this).get(LightBulbViewModel.class);
        lightBulbViewModel.init();

        lightBulbViewModel.getExceptionMessage().observe(this, (exceptionMessage) -> {
//            Log.d("temp", "exeption observer called " + exceptionMessage);
            if (exceptionMessage != null && !exceptionMessage.equals("")) {
                Toast.makeText(this, exceptionMessage, Toast.LENGTH_LONG).show();
            }
        });

        lightBulbViewModel.getCalls().observe(this, (calls) ->{
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            if (calls == 0){
                progressBar.setVisibility(View.INVISIBLE);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }else {
                    return false;
                }
            }
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    //Can be used to make a refresh button or something. Need to replace the inflater.
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawer_menu,menu);
        return true;
    }*/

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        init();
    }

    private void init(){
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(LOGTAG, "onNavigationItemSelected called " + item.getItemId());
        switch (item.getItemId()){
            case R.id.nav_list:

                // nav options to clear backstack
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.navigation_graph, true)
                        .build();

                Navigation.findNavController(this,R.id.nav_host_fragment_container).navigate(R.id.lightBulbListFragment,null,navOptions);
                break;
            case R.id.nav_settings:
                if(isValidDestination(R.id.settingsFragment)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_container).navigate(R.id.settingsFragment);
                }
                break;
        }

        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment_container).getCurrentDestination().getId();
    }

    //TODO Clear backstack from. When in huelight then click on settings. Screen has to change to list not huelight.
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if(Navigation.findNavController(this, R.id.nav_host_fragment_container).getCurrentDestination().getId() == R.id.settingsFragment){
                if(navigationView.getCheckedItem() != null) {
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
package com.csd.huelight.ui.mainactivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.csd.huelight.R;
import com.csd.huelight.ui.mainactivity.lightbulblist.LightBulbListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FrameLayout, LightBulbListFragment.newInstance())
                .commit();
    }
}
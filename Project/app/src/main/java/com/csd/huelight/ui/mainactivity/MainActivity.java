package com.csd.huelight.ui.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.csd.huelight.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LightBulbViewModel lightBulbViewModel = ViewModelProviders.of(this).get(LightBulbViewModel.class);
        lightBulbViewModel.init();


        lightBulbViewModel.getExceptionMessage().observe(this, (exceptionMessage) -> {
//            Log.d("temp", "exeption observer called " + exceptionMessage);
            if (exceptionMessage != null && !exceptionMessage.equals("")) {
                Toast.makeText(this, exceptionMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
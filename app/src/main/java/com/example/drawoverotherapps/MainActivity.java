package com.example.drawoverotherapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = (Button)findViewById(R.id.btnStart);

        checkOverlayPermission();

        startService();



        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Window w = new Window(MainActivity.this);
                w.open();

            }
        });

    }

    // method for starting the service

    public void startService(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // check if the user has already granted

            // the Draw over other apps permission

            if(Settings.canDrawOverlays(this)) {

                // start the service based on the android version

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    startForegroundService(new Intent(this, ForegroundService.class));

                } else {

                    startService(new Intent(this, ForegroundService.class));

                }

            }

        }else{

            startService(new Intent(this, ForegroundService.class));

        }

    }



    // method to ask user to grant the Overlay permission

    public void checkOverlayPermission(){



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(this)) {

                // send user to the device settings

                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);

                startActivity(myIntent);

            }

        }

    }



    // check for permission again when user grants it from

    // the device settings, and start the service

    @Override

    protected void onResume() {

        super.onResume();

        startService();

    }

}
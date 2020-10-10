package com.example.test3app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //constants for SharedPreferences
    public static final String PREFS_NAME = "myPrefs";
    public static final String FIRST_LAUNCH = "firstLaunch";
    public static final String LAST_ACTIVITY = "lastActivity";

    //Button for check connection
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean firstLaunch = preferences.getBoolean(FIRST_LAUNCH, true);
        String lastActivity = preferences.getString(LAST_ACTIVITY, "last");

        if (firstLaunch) {
            setContentView(R.layout.activity_main);
            button = findViewById(R.id.checkBtn);
            button.setOnClickListener(v -> {
            if (isConnected()) {
                changeToOnlineActivity();
                preferences.edit().putString(LAST_ACTIVITY, "online").apply();
            } else {
                changeToOfflineActivity();
                preferences.edit().putString(LAST_ACTIVITY, "offline").apply();
            }
        });
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_LAUNCH, false);
            editor.apply();
        } else {
            assert lastActivity != null;
            if (lastActivity.equals("online")) {
                changeToOnlineActivity();
            } else if (lastActivity.equals("offline")) {
                changeToOfflineActivity();
            }
        }
    }

    private void changeToOfflineActivity() {
        Intent intent = new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    private void changeToOnlineActivity() {
        Intent intent = new Intent(this, OnlineActivity.class);
        startActivity(intent);
    }

    private boolean isConnected() {
        boolean isWifi = false;
        boolean isMobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (networkInfo.isConnected()) {
                    isWifi = true;
                }
            }

            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (networkInfo.isConnected()) {
                    isMobileData = true;
                }
            }
        }
        return isWifi || isMobileData;
    }
}
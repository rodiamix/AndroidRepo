package com.example.test3app;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class OfflineActivity extends Activity {
    //buttons
    Button buttonShot, buttonDrum;

    //sounds
    MediaPlayer mediaPlayerShot, mediaPlayerShotFalse, mediaPlayerDrum;

    //images
    private ImageView bloodImage;

    //Texts
    private TextView loose;

    // if this - real shot
    private int onShot = 3;
    private int shopCapacity = 6;
    private int random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        init();
        createMediaPlayers();
    }

    private void init() {
        buttonShot = findViewById(R.id.real_btn);
        buttonDrum = findViewById(R.id.drum_btn);
        bloodImage = findViewById(R.id.image_blood);
        loose = findViewById(R.id.looseText);
    }

    protected void createMediaPlayers() {
        mediaPlayerShot = MediaPlayer.create(this, R.raw.revolver_shot);
        mediaPlayerShotFalse = MediaPlayer.create(this, R.raw.gun_false);
        mediaPlayerDrum = MediaPlayer.create(this, R.raw.revolver_baraban);
    }

    public void onShot(View view) {
        if (random == onShot) {
            mediaPlayerShot.start();
            bloodImage.setVisibility(View.VISIBLE);
            loose.setVisibility(View.VISIBLE);
        } else {
            mediaPlayerShotFalse.start();
        }
    }

    public void onDrum(View view) {
        mediaPlayerDrum.start();
        bloodImage.setVisibility(View.GONE);
        loose.setVisibility(View.GONE);
        random = new Random().nextInt(shopCapacity);
    }
}

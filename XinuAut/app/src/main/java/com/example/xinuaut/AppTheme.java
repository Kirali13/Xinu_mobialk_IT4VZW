package com.example.xinuaut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AppTheme extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 101;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_theme);

        permissionCheck();
    }

    private void permissionCheck() {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            // Permission is granted, start playing the music
            moveImg();
            playMusic();
        }
    }

    private void moveImg() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ImageView myImage = findViewById(R.id.logo);
            ImageView myImage1 = findViewById(R.id.logo1);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bot_to_top_land);
            Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bot_to_top_land1);
            myImage.startAnimation(animation);
            myImage1.startAnimation(animation1);
        } else {
            ImageView myImage = findViewById(R.id.logo);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.bot_to_top);
            myImage.startAnimation(animation);
        }

    }

    private void playMusic() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.omg);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SECRET_KEY", 13);

        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }
}
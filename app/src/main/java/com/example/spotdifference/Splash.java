package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView splash;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        start();

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void start() { //로고 올라가고, start 띄우기
        splash = findViewById(R.id.splash_logo);
        startButton = findViewById(R.id.startButton);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.3f,
                Animation.RELATIVE_TO_SELF, -0.1f);
        animation.setFillAfter(true); // 이동 이후 자리에 고정
        animation.setDuration(3000); // 3초 간 이동

        splash.startAnimation(animation);

        Animation buttonani = new AlphaAnimation(0,1);
        buttonani.setStartOffset(3000);
        buttonani.setDuration(3000);
        startButton.startAnimation(buttonani);

    }
}
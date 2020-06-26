package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//타임안에 게임을 끝내지못하면 처음 화면으로 돌아간다.
public class timeout extends AppCompatActivity  {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);

        //Back Button Event
        button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class); //MainActivity로
                startActivity(intent);
                finish();
            }
        });
    }
}

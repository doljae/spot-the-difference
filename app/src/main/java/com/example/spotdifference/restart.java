package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class restart extends AppCompatActivity {
    Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_restart);

        //restart Button Event
        restartButton = findViewById(R.id.restartButton);

    }

    public void restart(View v){
        finish();
        Intent intent = new Intent(getApplicationContext(),Game.class);
        startActivity(intent);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        //바깐 레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }
}

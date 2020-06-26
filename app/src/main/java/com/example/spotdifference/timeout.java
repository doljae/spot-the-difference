package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

//타임안에 게임을 끝내지못하면 처음 화면으로 돌아간다.
public class timeout extends AppCompatActivity  {

   Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_timeout);

        //Back Button Event
        backBtn = findViewById(R.id.backButton);
    }

    public void mOk(View v){
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

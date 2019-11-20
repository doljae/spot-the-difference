package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 애셋매니저
        AssetManager am = getResources().getAssets();
        InputStream leftImage = null;
        InputStream rightImage = null;

        try {
            // 애셋 폴더에 저장된 0_fake_B.png 열기.
            rightImage = am.open("0_fake_B.png");
            leftImage = am.open("0_real_B.png");


            // 입력스트림 rightImage와 leftImage를 통해 이미지를 Bitmap 객체로 변환.
            Bitmap bm1 = BitmapFactory.decodeStream(rightImage);
            Bitmap bm2 = BitmapFactory.decodeStream(leftImage);


            // 만들어진 Bitmap 객체를 이미지뷰에 표시.
            ImageView imageView1 = findViewById(R.id.rightImage);
            imageView1.setImageBitmap(bm1);

            ImageView imageView2 = findViewById(R.id.leftImage);
            imageView2.setImageBitmap(bm2);

            rightImage.close();
            leftImage.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if ((rightImage != null )) {
            try {
                rightImage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }



}


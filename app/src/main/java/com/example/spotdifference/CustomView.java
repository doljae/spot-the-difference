package com.example.spotdifference;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CustomView extends View {
    private static final float radius = 30;
    private Paint paint = new Paint();

    int cnt=0;

    private ArrayList<Integer> x = new ArrayList<Integer>();
    private ArrayList<Integer> y = new ArrayList<Integer>();

    int count = 0;
    boolean check = false;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0;i<x.size();i++) {
            canvas.drawCircle(x.get(i), y.get(i), radius, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        float ty = event.getY();
        //경로의 텍스트 파일읽기
        String line = null; // 한줄씩 읽기
        String[] tmp;
        File saveFile = new File("/sdcard"); // 저장 경로폴더
        try {
            BufferedReader buf = new BufferedReader(new FileReader(saveFile + "/crop_location.txt"));
            while ((line = buf.readLine()) != null) {
                tmp = line.split(" ");
                // 변환된 좌표만큼에 속해있는지 판별
                if(count==0) {
                    tx = (tx/getWidth())*2240;
                    ty = (ty/getHeight())*1792;
                }
                float tx1 = Float.parseFloat(tmp[2]) - 100;
                float ty1 = Float.parseFloat(tmp[0]) - 100;
                float tx2 = Float.parseFloat(tmp[2]) + 100;
                float ty2 = Float.parseFloat(tmp[0]) + 100;
                if(ty1 <= ty && ty <= ty2)
                    if(tx1 <= tx && tx <= tx2)
                        check = true;
                count += 1;
            }
            count = 0;
            buf.close();
            //좌표에있어서 OK 불린값 초기

            if(check) { // 옮은 좌표에 선택시 동그라미 생성
                Log.d("kkk", "OK" );
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x.add((int)event.getX());
                        y.add((int)event.getY());
                        invalidate();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        // nothing to do
                        break;
                    default:
                        return false;
                    //화면을 갱신한다.
                }

                //Toast.makeText(c
               // Toast.makeText(context, "X : " + x + "\nY : " + y + "\nOK", Toast.LENGTH_SHORT).show();
                check = false;
            }
            else
                Log.d("kkk", "NO" );
            //Toast.makeText(context, "X : " + x + "\nY : " + y + "\nNO", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}

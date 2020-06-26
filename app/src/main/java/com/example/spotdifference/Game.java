package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {

    Context context = this;
    ImageView imageView1;
    ImageView imageView2;
    TextView textView;
    TimerTask timerTask = null;
    Timer timer = null;
    ProgressBar prog = null;
    FrameLayout frameLayout;
    Boolean check = false;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        frameLayout = findViewById(R.id.frame);
        imageView1 = findViewById(R.id.rightImage);
        imageView2 = findViewById(R.id.leftImage);

        int img[] = {R.drawable.original1, R.drawable.original2, R.drawable.original3};
        String str[] = {"testfile.jpg", "testfile2.jpg", "testfile3.jpg"};
        imageView2.setImageResource(img[0]);


        File imgFile = new File("/sdcard/" + str[0]);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView1.setImageBitmap(myBitmap);
        }

        prog = findViewById(R.id.progressBar1);
        initProg();
        startTimerThread();

        textView = findViewById(R.id.timer);
        startTimerTask();


        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //MainActivity로
                startActivity(intent);
                finish();
            }
        });

        //pause Button
        Button pauseButton = findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     timer.cancel();
                Intent intent = new Intent(getApplicationContext(), restart.class); //MainActivity로
                startActivity(intent);
            }
        });


    }

    public void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel(); //타이머task를 timer 큐에서 지워버린다
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel(); //스케쥴task과 타이머를 취소한다.
            timer.purge(); //task큐의 모든 task를 제거한다.
            timer = null;
        }
    }


    public void initProg() {
        prog.setMax(60);//최대값 60 지정
        prog.setProgress(60); //현재값 60 지정
    }

    public void startTimerThread() {
        timerTask = new TimerTask() { //timerTask는 timer가 일할 내용을 기록하는 객체

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //이곳에 timer가 동작할 task를 작성
                decreaseBar(); //timer가 동작할 내용을 갖는 함수 호출
            }

        };

        timer = new Timer(); //timer생성
        timer.schedule(timerTask, 0, 1000); //timerTask라는 일을 갖는 timer를 0초딜레이로 1000ms마다 실행

    }


    public void decreaseBar() {
        runOnUiThread( //progressBar는 ui에 해당하므로 runOnUiThread로 컨트롤해야한다
                new Runnable() { //thread구동과 마찬가지로 Runnable을 써주고

                    @Override
                    public void run() { //run을 해준다. 그러나 일반 thread처럼 .start()를 해줄 필요는 없다
                        // TODO Auto-generated method stub
                        int currprog = prog.getProgress();

                        if (currprog > 0) {
                            currprog = currprog - 1;
                        } else if (currprog == 0) {
                            // currprog=6;
                            stopTimer();

                            initProg();
                            startTimerThread();

                        }
                        prog.setProgress(currprog);
                    }
                }
        );
    }


    private void startTimerTask() {

        timerTask = new TimerTask() {
            long count = 60; //Set Timer(sec)

            @Override
            public void run() {
                count--;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(count + "sec");

                        if ((count) <= 0) {
                            timer.cancel();
                            timerTask.cancel();
                            timerTask = null;
                            //If Timer <= 0, Change to timeout Layout
                            Intent intent = new Intent(context.getApplicationContext(), timeout.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }


}


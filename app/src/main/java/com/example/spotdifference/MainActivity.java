package com.example.spotdifference;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
    import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author 가천대 소프트웨어학과 졸업작품_김광륜_이석재_최진경
 * @version 0.0.1
 * @brief 틀린그림찾기 게임
 * @details GAN을 이용하여 이미지를 처리, 안드로이드로 앱 제작
 * @date 2019-11-
 */

public class MainActivity extends AppCompatActivity {
    Context context = this;
    Button startButton,helpButton;

    public void sendTextMsg(DataOutputStream out, String msg) throws IOException {
        byte[] bytes = msg.getBytes();
        long len = bytes.length;
        //콘텐츠를 보내기전에 길이를 보내줌
        out.writeLong(len);
        out.write(bytes);
    }

    public void sendImgMsg(DataOutputStream out) throws IOException {
        //전송 된 그림은 비트 맵을 바이트 배열로 변환하는 Android 로봇 아이콘입니다.
        Log.i("sendImgMsg", "len: " + "1");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.original1, options);

        Log.i("sendImgMsg", "len: " + "2");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, bout);
        //바이트의 길이를 쓴 다음 그림의 바이트를 쓴다
        long len = bout.size();
        //송신 길이를 여기에 인쇄하십시오
        Log.i("sendImgMsg", "len: " + len);
        out.write(bout.toByteArray());
    }

    class FileClient {
        private Socket s;

        public FileClient(String host, int port) {
            try {
                s = new Socket(host, port);
                s.setReceiveBufferSize(Integer.MAX_VALUE);
                saveFile(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void saveFile(Socket clientSock) throws IOException {
            InputStream in = clientSock.getInputStream();
            // 바이트 단위로 데이터를 읽는다, 외부로 부터 읽어들이는 역할을 담당
            BufferedInputStream bis = new BufferedInputStream(in);
            Log.i(this.getClass().getName(), "3333333333");
            // 파일을 읽는 경우라면,BufferedReader보다 BufferedInputStream이 더 적절하다.

            File file = new File(Environment.getExternalStorageDirectory(), "testfile.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            // 파일을 열어서 어떤식으로 저장할지 알려준다. FileOutputStream을 쓰면 들어오는 파일과 일치하게 파일을 작성해줄 수 있는 장점이
            // 있다.
            int ch;
            while ((ch = bis.read()) != -1) {
                fos.write(ch);
                // 열린 파일시스템에 BufferedInputStream으로 외부로 부터 읽어들여온 파일을 FileOutputStream에 바로 써준다.
            }
            fos.close();
            in.close();
            //소켓 종료
        }
    }

    class FileClient2 {
        private Socket s;

        public FileClient2(String host, int port) {
            try {
                s = new Socket(host, port);
                s.setReceiveBufferSize(Integer.MAX_VALUE);
                saveFile(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void saveFile(Socket clientSock) throws IOException {
            InputStream in = clientSock.getInputStream();
            // 바이트 단위로 데이터를 읽는다, 외부로 부터 읽어들이는 역할을 담당
            BufferedInputStream bis = new BufferedInputStream(in);
            // 파일을 읽는 경우라면,BufferedReader보다 BufferedInputStream이 더 적절하다.

            File file = new File(Environment.getExternalStorageDirectory(), "crop_location.txt");
            FileOutputStream fos = new FileOutputStream(file);
            // 파일을 열어서 어떤식으로 저장할지 알려준다. FileOutputStream을 쓰면 들어오는 파일과 일치하게 파일을 작성해줄 수 있는 장점이
            // 있다.
            int ch;
            while ((ch = bis.read()) != -1) {
                fos.write(ch);
                // 열린 파일시스템에 BufferedInputStream으로 외부로 부터 읽어들여온 파일을 FileOutputStream에 바로 써준다.
            }
            fos.close();
            in.close();
            //소켓 종료
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    Socket socket;
                    String host = "192.168.219.104";
                    int post = 7777;
                    int post2 = 7778;
                    int post3 = 7779;

                    public void run() {
                        try {
                            socket = new Socket(host, post);
                            //출력 스트림을 받고이 스트림을 통해 메시지를 보냅니다.
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            //메세지전송
//                          sendTextMsg(out,"dd");
                            sendImgMsg(out);
                            out.close();
                            socket.close();
                            // 송신이 끝나고 수신을 위한 소켓을 바로 설정하고 염
                            sleep(10000);
                            MainActivity.FileClient fc = new MainActivity.FileClient(host, post2);
                            sleep(3000);
                            MainActivity.FileClient2 fc2 = new MainActivity.FileClient2(host, post3);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                Intent intent = new Intent(getApplicationContext(),Game.class); //gameActivity로
                startActivity(intent);
                finish();
            }
        });

        helpButton = findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),popupActivity.class); //popupActivity로
                startActivity(intent);
            }
        });

    }

}


package com.example.spotdifference;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class draw extends Activity {
   // ImageView imageView1 = (ImageView) findViewById(R.id.leftImage);
    private ImageView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      mView =(ImageView)findViewById(R.id.rightImage);
      setContentView(mView);
    }

    public class ImageView extends View {

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private float initX, initY, radius;
        private boolean drawing = false;

        public ImageView(Context context) {
            super(context);
            init();
        }

        public ImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public ImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        private void init() {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                    MeasureSpec.getSize(heightMeasureSpec));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (drawing) {
                canvas.drawCircle(initX, initY, radius, paint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            int action = event.getAction();
            if (action == MotionEvent.ACTION_MOVE) {
                float x = event.getX();
                float y = event.getY();

                radius = (float) Math.sqrt(Math.pow(x - initX, 2) + Math.pow(y - initY, 2));

            } else if (action == MotionEvent.ACTION_DOWN) {
                initX = event.getX();
                initY = event.getY();
                radius = 1;
                drawing = true;
            } else if (action == MotionEvent.ACTION_UP) {
                drawing = false;
                performClick();
            }
            invalidate();
            return true;
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }
    }

}

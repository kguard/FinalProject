package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageButton ibZoomIn, ibZoomOut, ibRotate, ibBright, ibDark, ibBlur, ibEmboss;
    MyGraphicView graphicView;

    static float scaleX = 1, scaleY = 1;
    static float angle = 0;
    static float satur = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("미니 포토샵");

        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView);
        clickIcons();
    }

    private void clickIcons() {
        ibZoomIn = (ImageButton) findViewById(R.id.ibZoomIn);
        ibZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                graphicView.invalidate(); //onDraw 다시 호출하는 코드
            }
        });
        ibZoomOut = (ImageButton) findViewById(R.id.ibZoomOut);
        ibZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate();
            }
        });
        ibRotate = (ImageButton) findViewById(R.id.ibRotate);
        ibRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                angle = angle + 20;
                graphicView.invalidate();
            }
        });
        ibBright = (ImageButton) findViewById(R.id.ibBright);
        ibBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satur = satur +0.2f;
//                if(satur > 1)
//                    satur = 1;
                graphicView.invalidate();
            }
        });
        ibDark = (ImageButton) findViewById(R.id.ibDark);
        ibDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satur = satur - 0.2f;
//                if(satur < 0)
//                    satur = 0;
                graphicView.invalidate();
            }
        });
        ibBlur = (ImageButton) findViewById(R.id.ibBlur);
        ibBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                graphicView.invalidate();
            }
        });
        ibEmboss = (ImageButton) findViewById(R.id.ibEmboss);
        ibEmboss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private static class MyGraphicView extends View { // 그래픽을 표현하기 위한 View 클래스 재정의
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) { // 그래픽을 출력하는 코드
            super.onDraw(canvas);
            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.lena256);
            int picX = (this.getWidth() - picture.getWidth()) / 2;
            int picY = (this.getHeight() - picture.getHeight()) / 2;

            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;

            canvas.scale(scaleX, scaleY, cenX, cenY);

            canvas.rotate(angle, cenX, cenY);

            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();

            cm.setSaturation(satur);

            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(picture,picX,picY,paint);
            picture.recycle();

        }
    }

}
package com.Dgio.flapybird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v4.content.ContextCompat.startActivity;

public class GameView extends View {

    //Canvas
    private int canvasWidth;
    private int canvasHeight;
    //bird
    //private Bitmap bird;
    private Bitmap bird[] = new Bitmap[2];
    private int birdX = 10;
    private int birdY;
    private int birdSpeed;

    //coin
    private int coinX;
    private int coinY;
    private int coinSpeed = 15;
    private Bitmap coinPaint;
    //private Paint coinPaint = new Paint();

    //Alcon
    private int alconX;
    private int alconY;
    private int alconSpeed = 20;
    private Bitmap alconPaint;

    //Background
    private Bitmap bgImage;
    //score
    private Paint scorePaint = new Paint();
    private int score;
    //Level
    private Paint levelPaint = new Paint();
    //Life
    private Bitmap life[] = new Bitmap[2];
    private int life_conut;

    //Status Check
    private boolean touch_flag = false;

    public GameView(Context context) {
        super(context);

        bird[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.pigeon);
        bird[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.bird2);

        bgImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.bgimage);

        //coinPaint.setColor(Color.YELLOW);
        coinPaint = BitmapFactory.decodeResource(getResources(),
                R.drawable.coin);

        alconPaint = BitmapFactory.decodeResource(getResources(),
                R.drawable.falcon);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        levelPaint.setColor(Color.DKGRAY);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        levelPaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),
                R.drawable.like);
        life[1] = BitmapFactory.decodeResource(getResources(),
                R.drawable.heart);

        //First position
        birdY = 500;
        score = 0;
        life_conut = 4;






    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth =  canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bgImage,0,0,null);
        //canvas.drawBitmap(bird,0,0,null);
        int minBirdY = bird[0].getHeight();
        int maxBirdY = canvasHeight - bird[0].getHeight()*3;

        birdY += birdSpeed;
        if (birdY < minBirdY) birdY = minBirdY;
        if (birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed +=2;

        if(touch_flag)
        {
            canvas.drawBitmap(bird[1],birdX,birdY,null);
            touch_flag = false;
        }else
            {
            canvas.drawBitmap(bird[0],birdX,birdY,null);
            }

         //Coin

        coinX -= coinSpeed;

        if (hitCheck(coinX,coinY))
        {
            score += 10;
            coinX = -100;
        }

        if (coinX < 0)
        {
            coinX = canvasWidth + 20;
            coinY = (int) Math.floor(Math.random()* (maxBirdY - minBirdY))+ minBirdY;
        }


        //Alcon
        alconX -= alconSpeed;
        if (hitCheck(alconX,alconY))
        {
            alconX = -100;
            life_conut --;
            if (life_conut==0)
            {

                changeScreen();
            }
        }
        if ( alconX< 0)
        {
            alconX = canvasWidth + 200;
            alconY = (int) Math.floor(Math.random()* (maxBirdY - minBirdY))+ minBirdY;
        }


        canvas.drawBitmap(coinPaint,coinX,coinY,null);
        canvas.drawBitmap(alconPaint,alconX,alconY,null);
        //score
        canvas.drawText("Score : " +score,20,60,scorePaint);
        //leve
        canvas.drawText("LV.1",canvasWidth/3,60,levelPaint);

        //life
        for (int i = 0; i < 4 ;i++)
        {
            int x = (int) (560 + life[0].getWidth()*1.5 *i);
            int y = 30;

            if (i < life_conut)
            {
                canvas.drawBitmap(life[0],x,y,null);
            }else {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

        /*
        canvas.drawBitmap(life[0],560,30,null);
        canvas.drawBitmap(life[0],620,30,null);
        canvas.drawBitmap(life[1],680,30,null);
        */

    }

    private void changeScreen() {

            Intent intent = new Intent(getContext(),gameOver.class);
            startActivity(getContext(),intent,null);

    }


    public boolean hitCheck(int x, int y)
    {
        if (birdX < x && x < (birdX + bird[0].getWidth()) &&
         birdY < y && y < (birdY + bird[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch_flag = true;
            birdSpeed = -20;

        }
        return true;
    }
}

package com.example.a88481.showtimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import  android.os.Message;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 88481 on 2017/3/11 0011.
 */

public class TimerView extends View {

    /*private Thread thread = new Thread(){
        public void run(){
            super.run();
        }
    };**/

    private Handler handler = new Handler(){
        public void handleMessage(Message message){

            switch (message.what){
                case 1:
                    invalidate();
                    break;
            }
        }
    };

    private TimerThread thread = new TimerThread();

    class TimerThread extends Thread{
        public void run(){
            super.run();
            while (true){
                try {
                    sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public TimerView(Context context) {
        super(context);

        this.thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        //paint.setStrokeWidth(1);
        //paint.setColor(Color.RED);
        //paint.setTextSize(200f);
        PointF centerPoint = new PointF();
        centerPoint.set((this.getLeft() + this.getRight())/2,
                        (this.getTop() + this.getBottom())/2);
        float radius = (this.getWidth()<this.getHeight())?
                this.getWidth()/2:this.getHeight()/2;
        radius-=20;
        canvas.drawCircle(centerPoint.x,centerPoint.y,radius,paint);

        float centerPointRadius = radius * 0.05f;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(centerPoint.x,centerPoint.y,centerPointRadius,paint);

        double perAngle = 2*Math.PI / 12;
        for (int index = 0;index<12;index++)
        {
            PointF a = new PointF((float)(centerPoint.x + radius*Math.cos(perAngle * index)),
                    (float)(centerPoint.y + radius * Math.sin(perAngle * index)));

            PointF b = new PointF((float)(centerPoint.x +0.9 * radius*Math.cos(perAngle * index)),
                (float)(centerPoint.y + 0.9 * radius * Math.sin(perAngle * index)));

            canvas.drawLine(a.x,a.y,b.x,b.y,paint);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date(System.currentTimeMillis());
        String time = formatter.format(currentTime);

        int hour = currentTime.getHours();
        int minute = currentTime.getMinutes();
        int second = currentTime.getSeconds();

        double angleSecond = Math.PI * 2/60*second;
        angleSecond += Math.PI/2;

        double angleMinute = Math.PI * 2/60*minute + Math.PI*2/60/60*second;
        angleMinute += Math.PI/2;

        double angleHour = Math.PI*2/12*hour + Math.PI * 2/12/60*minute + Math.PI*2/60/60*second;
        angleHour += Math.PI/2;

        paint.setColor(Color.RED);
        paint.setStrokeWidth(2f);
        canvas.drawLine(centerPoint.x,centerPoint.y,
                (float)(centerPoint.x - radius*0.85*Math.cos(angleSecond)),
                (float)(centerPoint.y - radius*0.85*Math.sin(angleSecond)),paint);

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(6f);
        canvas.drawLine(centerPoint.x,centerPoint.y,
                (float)(centerPoint.x - radius*0.7*Math.cos(angleMinute)),
                (float)(centerPoint.y - radius*0.7*Math.sin(angleMinute)),paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f);
        canvas.drawLine(centerPoint.x,centerPoint.y,
                (float)(centerPoint.x - radius*0.55*Math.cos(angleHour)),
                (float)(centerPoint.y - radius*0.55*Math.sin(angleHour)),paint);

        paint.setTextSize(100f);
        canvas.drawText(time,10f,100f,paint);



    }
}


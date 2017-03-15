package com.example.a88481.showtimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TimerView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.setContentView(R.layout.activity_main);
        //this.setContentView(new TimerView(MainActivity.this));
        //this.setContentView(new TimerView(MainActivity.this));

        this.timerView = new TimerView(this);
        this.setContentView(this.timerView);


    }
}

package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_alarm,btn_stopwatch,btn_timing; //切換頁面按鈕宣告


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_alarm =findViewById(R.id.btn_alarm);
        btn_stopwatch =findViewById(R.id.btn_stopwatch);
        btn_timing =findViewById(R.id.btn_timing);

        //切換鬧鐘頁面
        btn_alarm.setOnClickListener( v -> {
            Intent intent =new Intent(MainActivity.this,AlarmActivity.class);
            startActivity(intent);
        });
        //切換碼表頁面
        btn_stopwatch.setOnClickListener( v -> {
            Intent intent =new Intent(MainActivity.this,StopwatchActivity.class);
            startActivity(intent);
        });
        //切換計時頁面
        btn_timing.setOnClickListener( v -> {
            Intent intent =new Intent(MainActivity.this,TimingActivity.class);
            startActivity(intent);
        });
    }
}
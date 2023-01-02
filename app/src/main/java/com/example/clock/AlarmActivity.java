package com.example.clock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_main;

    private TimePicker tp;
    private Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //回首頁功能
        btn_main =findViewById(R.id.btn_main2);
        btn_main.setOnClickListener( v -> {
            Intent intent =new Intent(AlarmActivity.this,MainActivity.class);
            startActivity(intent);
        });

        //鬧鐘功能
        btn_set = findViewById(R.id.set_alarm);
        tp = findViewById(R.id.time);
        btn_set.setOnClickListener(this);

        //Exit按鈕
        Button ext = (Button) findViewById(R.id.exit);
        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //System.exit(0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                tp.getHour(),
                tp.getMinute(),0);
        Alarm_set(cal.getTimeInMillis());
    }


    private void Alarm_set(long timeInMillis)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "已設定鬧鐘", Toast.LENGTH_LONG).show();
    }


}
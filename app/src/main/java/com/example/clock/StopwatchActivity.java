package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StopwatchActivity extends AppCompatActivity {
    private TextView tv_clock;
    private Button btn_start;
    private Button btn_zero;
    private TextView tv_one;

    private Button btn_main;

    private Boolean flag =false;
    private Bundle b;
    private Integer i=0;
    private Integer ms=0,s=0,m=0;
    private Integer pms=0,ps=0,pm=0;//上一個時間暫存
    private String black="  ";
    private BroadcastReceiver receiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            b =intent.getExtras();
            tv_clock.setText(String.format("%02d:%02d:%02d",
                    b.getInt("M"),b.getInt("S"),b.getInt("MS")));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        tv_clock =findViewById(R.id.tv_clock);
        btn_start =findViewById(R.id.btn_start);
        btn_zero =findViewById(R.id.btn_zero);
        tv_one =findViewById(R.id.tv_one);

        btn_main =findViewById(R.id.btn_main3);

        registerReceiver(receiver,new IntentFilter("MyMessage"));
        tv_one.setMovementMethod(new ScrollingMovementMethod());

        flag = MyService.flag;
        if(flag)
            btn_start.setText("暫停");
        else
            btn_start.setText("開始");
        btn_start.setOnClickListener(v -> {

            flag = !flag;
            //按下開始
            if(flag){
                btn_zero.setVisibility(View.VISIBLE);
                btn_start.setText("暫停");
                btn_zero.setText("單圈計時");
                Toast.makeText(StopwatchActivity.this,"計時開始",Toast.LENGTH_SHORT).show();
            }else {//按下暫停
                btn_start.setText("開始");
                btn_zero.setText("重設");
                Toast.makeText(StopwatchActivity.this,"計時暫停",Toast.LENGTH_SHORT).show();
            }
            startService(new Intent(StopwatchActivity.this, MyService.class).putExtra("flag",flag));

        });

        btn_zero.setOnClickListener(v ->{
            //按下單圈計時
            if(flag){
                i++;
                if(i>9)
                    black="";
                ms = b.getInt("MS");
                s = b.getInt("S");
                m = b.getInt("M");
                if(ms-pms<0){
                    ps++;
                    pms-=100;
                }
                if(s-ps<0){
                    pm++;
                    ps-=100;
                }
                tv_one.setText(String.format("第%d圈       %s%02d:%02d:%02d         %02d:%02d:%02d\r\n"+tv_one.getText()
                        ,i,black,m,s,ms,m-pm,s-ps,ms-pms));
                pms=ms;
                ps =s;
                pm=m;
            }else {//按下重設
                btn_zero.setVisibility(View.INVISIBLE);
                MyService.m=0;
                MyService.s=0;
                MyService.ms=0;
                pms=0;
                ps=0;
                pm=0;
                i=0;
                black="  ";
                tv_clock.setText(String.format("00:00:00"));
                tv_one.setText(String.format(""));
                Toast.makeText(StopwatchActivity.this,"重設",Toast.LENGTH_SHORT).show();
            }
        });

        btn_main.setOnClickListener( v -> {
            Intent intent =new Intent(StopwatchActivity.this,MainActivity.class);
            startActivity(intent);
            if(flag)
                btn_start.callOnClick();
            btn_zero.callOnClick();
        });

    }

    public void onDestory(){
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
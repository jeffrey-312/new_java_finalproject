package com.example.clock;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class MyService extends Service {
    static Boolean flag =false;
    static int m=0,s=0,ms=0;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        flag = intent.getBooleanExtra("flag",false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    ms++;
                    if(ms>=100){
                        ms=0;
                        s++;
                        if(s>=60){
                            s=0;
                            m++;
                        }
                    }
                    Intent i =new Intent("MyMessage");

                    Bundle bundle =new Bundle();
                    bundle.putInt("M",m);
                    bundle.putInt("S",s);
                    bundle.putInt("MS",ms);

                    i.putExtras(bundle);

                    sendBroadcast(i);
                }
            }
        }).start();
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
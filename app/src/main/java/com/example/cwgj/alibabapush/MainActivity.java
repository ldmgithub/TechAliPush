package com.example.cwgj.alibabapush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.cwgj.pushlib.bean.NotificationBean;
import com.example.cwgj.pushlib.util.PushUtils;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;
import rxbus.ecaray.com.rxbuslib.rxbus.RxBusReact;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
    }


    @RxBusReact(clazz = {NotificationBean.class}, tag = PushUtils.TAG_NOTIFICATION_REC)
    public void notificationRec(final NotificationBean notificationBean){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "接收" + notificationBean.summary, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RxBusReact(clazz = {NotificationBean.class}, tag = PushUtils.TAG_NOTIFICATION_OPENED)
    public void notificationOpened(final NotificationBean notificationBean){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "打开" + notificationBean.summary, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RxBusReact(clazz = {String.class}, tag = PushUtils.TAG_NOTIFICATION_REMOVED)
    public void notificationRemove(final String messageId){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "移除"+messageId, Toast.LENGTH_SHORT).show();
            }
        });

    }


}

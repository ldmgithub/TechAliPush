package com.example.cwgj.pushlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：
 * +----------------------------------------------------------------------
 * | 创建者   :  ldw
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/21 18:53
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class NotificationService extends Service {
    public static final String TAG = "NotificationService" ;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals("com.cwgj.build")) {
            //添加您的通知点击处理逻辑
            CPushMessage message = intent.getParcelableExtra("message key");//获取message
            PushServiceFactory.getCloudPushService().clickMessage(message);//上报通知点击事件，点击事件相关信息可以在推送控制台查看到
        } else if(action.equals("com.cwgj.delete")) {
            //添加您的通知删除处理逻辑
            CPushMessage message = intent.getParcelableExtra("message key");//获取message
            PushServiceFactory.getCloudPushService().dismissMessage(message);//上报通知删除事件，点击事件相关信息可以在推送控制台查看到
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

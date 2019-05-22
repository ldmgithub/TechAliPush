package com.example.cwgj.alibabapush;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.example.cwgj.pushlib.receiver.MyMessageIntentService;
import com.example.cwgj.pushlib.util.PushUtils;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：
 * +----------------------------------------------------------------------
 * | 创建者   :
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/21 17:39
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class MainAppliaction extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PushUtils.initCloudChannel(RxBus.getDefault(), this, "25337463", "bf96edb28704dc5f98959da0d7b7590e");
//        initCloudChannel(this);
        PushUtils.initGcmCloudChannel(  this, "24829542", "1e8b48512cc143eca816f43eccb71715");
    }

    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setPushIntentService(MyMessageIntentService.class);
        pushService.register(applicationContext, "25337463","bf96edb28704dc5f98959da0d7b7590e",new CommonCallback() {
            @Override
            public void onSuccess(final String response) {
                Log.i("connectPushId", pushService.getDeviceId());
                RxBus.getDefault().post("connectPushId");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d("TccApp", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

}

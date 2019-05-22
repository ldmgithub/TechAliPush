package com.example.cwgj.pushlib.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.example.cwgj.pushlib.receiver.MyMessageIntentService;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：
 * +----------------------------------------------------------------------
 * | 创建者   :  ldw
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/23 14:51
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class PushUtils {

    private static final String TAG = "PushUtils";
    //接收通知
    public static final String TAG_NOTIFICATION_REC = "notification_rec";
    //通知打开
    public static final String TAG_NOTIFICATION_OPENED = "notification_opened";
    //通知移除
    public static final String TAG_NOTIFICATION_REMOVED = "notification_removed";
    //接收消息
    public static final String TAG_MESSAGE_REC = "tag_message_rec";
    //rxbus 事件bus
//    public static RxBus sRxBus;

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    public static void initCloudChannel(RxBus rxBus, Context applicationContext, String appKey, String appSecret) {
//        sRxBus = rxBus;
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        //设置接受通知通道为： AliyunMessageIntentService(官方建议)
        pushService.setPushIntentService(MyMessageIntentService.class);
        //注册channel
        pushService.register(applicationContext, appKey, appSecret, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success" + pushService.getDeviceId());
                RxBus.getDefault().post(pushService.getDeviceId(), "connectPushId");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
//        MiPushRegister.register(applicationContext, "小米AppID", "小米AppKey");
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
//        HuaWeiRegister.register(applicationContext);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext ,isInitThirdChannel 是否集成辅助通道
     */
    public static void initCloudChannel(Context applicationContext, boolean isInitThirdChannel) {
        initCloudChannel(applicationContext, isInitThirdChannel, null, null);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    public static void initCloudChannel(final Context applicationContext, final boolean isInitThirdChannel, final String xiamoId, final String xiaomiKey) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        //设置接受通知通道为： AliyunMessageIntentService(官方建议)
        pushService.setPushIntentService(MyMessageIntentService.class);
        //注册channel
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
                if (isInitThirdChannel) {
                    initThirdCloudChannel(applicationContext, xiamoId, xiaomiKey);
                }
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }


    //必须在推送初始化之后 小米，华为推送
    public static void initThirdCloudChannel(Context application, String XIAOMI_ID, String XIAOMI_KEY) {
        MiPushRegister.register(application, XIAOMI_ID, XIAOMI_KEY);
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        HuaWeiRegister.register(application);
    }

    ;

    //必须在推送初始化之后，gcm服务
    public static void initGcmCloudChannel(Application application, String sendId, String applicationId) {
        GcmRegister.register(application, sendId, applicationId); //sendId/applicationId为步骤获得的参数
    }

}

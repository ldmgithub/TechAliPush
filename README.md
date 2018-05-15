# TechAliPush
集成阿里移动推送

# 集成说明

1.在工程build.gradle：

  allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    
2.在module的gradle里依赖

  dependencies {
     compile 'com.github.TopTech666:TechAliPush:1.1.5'
   }
   
3.创建自己的接受类MyMessageReceiver 

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }

4.在清单文件里注册MyMessageReceiver 
 <!-- 消息接收监听器 （用户可自主扩展） -->
 
    <meta-data android:name="com.alibaba.app.appkey" android:value="appKey"/> <!-- 请填写你自己的- appKey -->
        <meta-data android:name="com.alibaba.app.appsecret" android:value="appSecret"/> <!-- 请填写你自己的appSecret -->

        <receiver
            android:name="xxx.MyMessageReceiver"//包名+接受者的类名
            android:exported="false" > <!-- 为保证receiver安全，建议设置不可导出，如需对其他应用开放可通过android：permission进行限制 -->
            <intent-filter>
                <action android:name="com.alibaba.push2.action.NOTIFICATION_OPENED" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.alibaba.push2.action.NOTIFICATION_REMOVED" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.alibaba.sdk.android.push.RECEIVE" />
            </intent-filter>
        </receiver>
        
5.Proguard配置

    -keepclasseswithmembernames class ** {
        native <methods>;
    }
    -keepattributes Signature
    -keep class sun.misc.Unsafe { *; }
    -keep class com.taobao.** {*;}
    -keep class com.alibaba.** {*;}
    -keep class com.alipay.** {*;}
    -keep class com.ut.** {*;}
    -keep class com.ta.** {*;}
    -keep class anet.**{*;}
    -keep class anetwork.**{*;}
    -keep class org.android.spdy.**{*;}
    -keep class org.android.agoo.**{*;}
    -keep class android.os.**{*;}
    -dontwarn com.taobao.**
    -dontwarn com.alibaba.**
    -dontwarn com.alipay.**
    -dontwarn anet.**
    -dontwarn org.android.spdy.**
    -dontwarn org.android.agoo.**
    -dontwarn anetwork.**
    -dontwarn com.ut.**
    -dontwarn com.ta.**

6.在应用中注册和启动移动推送

    首先通过PushServiceFactory获取到CloudPushService，然后调用register()初始化并注册云推送通道，并确保Application上下文中进行初始化工作。
    请参照以下代码段进行初始化：
    
    public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);

        initThirdCloudChannel(this);//必须在推送初始化之后，第三方推送：华为和小米以及谷歌
    }

    private void initThirdCloudChannel(MyApplication applicationContext) {
        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
        MiPushRegister.register(applicationContext, "2882303761517757826", "5761775751826");
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        HuaWeiRegister.register(applicationContext);
//        //GCM/FCM辅助通道注册
//        GcmRegister.register(this, sendId, applicationId); //sendId/applicationId为步骤获得的参数
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        Log.d(TAG, "initCloudChannel:pushService= "+pushService.toString());
        pushService.bindAccount("1234", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "bindAccount:onSuccess= "+s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d(TAG, "bindAccount:onFailed,s= "+s+",s1="+s1);
            }
        });

        pushService.bindTag(CloudPushService.ALIAS_TARGET, new String[]{"parkid","收费员","",""}, "别名userid", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "bindTag:onFailed,s= "+s );
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d(TAG, "bindTag:onFailed,s= "+s+",s1="+s1);
            }
        });

        pushService.listAliases(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "bindTag:onFailed,s= "+s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Log.d(TAG, "bindTag:onFailed,s= "+s+",s1="+s1);
            }
        });
        Log.d(TAG, "initCloudChannel:pushService2= "+PushServiceFactory.getCloudPushService());
        Log.d(TAG, "initCloudChannel:pushServic3e= "+PushServiceFactory.getCloudPushService());
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }
}

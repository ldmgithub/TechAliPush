package com.example.cwgj.alibabapush;

import android.app.Application;

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
        PushUtils.initCloudChannel(RxBus.getDefault(), this, "24829542", "1e8b48512cc143eca816f43eccb71715");
    }


}

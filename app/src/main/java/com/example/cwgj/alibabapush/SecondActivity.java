package com.example.cwgj.alibabapush;

import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;

import java.util.Map;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：辅助弹框
 * +----------------------------------------------------------------------
 * | 创建者   :
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/23 16:38
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class SecondActivity extends AndroidPopupActivity {

    private static final String TAG = "AndroidPopupActivity";
    @Override
    protected void onSysNoticeOpened(String s, String content, Map<String, String> extraMap) {
        Log.d(TAG, "onSysNoticeOpened: ");
    }
}

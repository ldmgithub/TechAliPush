package com.example.cwgj.pushlib.bean;

import java.util.Map;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：
 * +----------------------------------------------------------------------
 * | 创建者   :  ldw
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/23 15:53
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class NotificationBean {

    //标题
    public String title;
    //内容
    public String summary;
    //额外参数（接收）
    public Map<String, String> extraMap;
    // //额外参数
    public String extraStr;

    public NotificationBean(String title, String summary, Map<String, String> extraMap, String extraStr) {
        this.title = title;
        this.summary = summary;
        this.extraMap = extraMap;
        this.extraStr = extraStr;
    }

}

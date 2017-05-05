package com.atguigu.speechdemo2;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * 作者：杨光福 on 2016/4/30 09:34
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 将“12345678”替换成您申请的 APPID，申请地址： http://www.xfyun.cn
// 请勿在“ =” 与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility(MyApplication.this, "appid=56f4c1dd" );
    }
}

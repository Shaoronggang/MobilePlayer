package com.atguigu.mobileplayer;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

import org.xutils.x;

/**
 * 作者：邵荣刚 on 2016/4/23 15:29
 * 微信：yangguangfu520
 * QQ号：541433511
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SpeechUtility.createUtility(MyApplication.this, "appid=56f4c1dd");
    }
}

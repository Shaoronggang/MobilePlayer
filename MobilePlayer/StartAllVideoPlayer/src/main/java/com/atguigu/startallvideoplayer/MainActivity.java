package com.atguigu.startallvideoplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startAllVideoPlayer(View view){
        Intent intent = new Intent();
//       intent.setDataAndType(Uri.parse("http://10.0.2.2:8080/oppo.mp4"),"video/*");
       intent.setDataAndType(Uri.parse("/mnt/sdcard/afinloop.mp43"),"video/*");
//        intent.setDataAndType(Uri.parse("http://192.168.10.168:8080/rmvb.rmvb"),"video/*");
//        intent.setDataAndType(Uri.parse("http://192.168.10.168:8080/yellow.mp4"),"video/*");
//        intent.setDataAndType(Uri.parse("http://vfx.mtime.cn/Video/2016/04/14/mp4/160414103101304893_480.mp4"),"video/*");
//        intent.setDataAndType(Uri.parse("http://vfx.mtime.cn/Video/2016/04/14/mp4/160414103101304893_480.mp4"),"video/*");
        startActivity(intent);
    }
}

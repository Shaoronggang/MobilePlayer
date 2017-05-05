package com.harvic.tryeventbus2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.harvic.other.FirstEvent;
import com.harvic.other.SecondEvent;
import com.harvic.other.ThirdEvent;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {

    Button btn;
    TextView tv;
    EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        btn = (Button) findViewById(R.id.btn_try);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(),
                        SecondActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onEventMainThread(FirstEvent event) {

        Log.d("harvic", "onEventMainThread:" + event.getMsg());
    }

    //SecondEvent接收函数一
    public void onEventMainThread(SecondEvent event) {

        Log.d("harvic", "onEventMainThread收到了消息：" + event.getMsg());
    }

    //SecondEvent接收函数二
    public void onEventBackgroundThread(SecondEvent event) {
        Log.d("harvic", "onEventBackground收到了消息：" + event.getMsg());
    }

    //SecondEvent接收函数三
    public void onEventAsync(SecondEvent event) {
        Log.d("harvic", "onEventAsync收到了消息：" + event.getMsg());
    }

    public void onEvent(ThirdEvent event) {
        Log.d("harvic", "OnEvent:" + event.getMsg());
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

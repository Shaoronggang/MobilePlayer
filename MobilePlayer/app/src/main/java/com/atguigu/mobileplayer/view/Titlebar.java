package com.atguigu.mobileplayer.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.SearchActivity;

/**
 * 作者：杨光福 on 2016/4/27 14:23
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class Titlebar extends LinearLayout {
    private TextView tv_serach;
    private RelativeLayout rl_game;
    private ImageView play_history;
    private Context context;

    public Titlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 当布局加载完成的时候回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tv_serach = (TextView) getChildAt(1);
        rl_game = (RelativeLayout) getChildAt(2);
        play_history = (ImageView) getChildAt(3);

        MyOnClickListener myOnClickListener= new MyOnClickListener();
//设置点击事件
        tv_serach.setOnClickListener(myOnClickListener);
        rl_game.setOnClickListener(myOnClickListener);
        play_history.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_serach:
//                    Toast.makeText(getContext(), "搜索", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,SearchActivity.class);
                    context.startActivity(intent);
                    break;
                case R.id.rl_game:
                    Toast.makeText(getContext(), "游戏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_play_history:
                    Toast.makeText(getContext(), "播放历史", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


}

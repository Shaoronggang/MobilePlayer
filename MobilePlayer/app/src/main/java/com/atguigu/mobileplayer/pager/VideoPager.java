package com.atguigu.mobileplayer.pager;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.Utils.Utils;
import com.atguigu.mobileplayer.adapter.MediaAdapter;
import com.atguigu.mobileplayer.base.BasePager;
import com.atguigu.mobileplayer.domain.MediaItem;
import com.atguigu.mobileplayer.video.SystemVideoPlayer;

import java.util.ArrayList;

/**
 * 作者：杨光福 on 2016/4/20 10:45
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class VideoPager extends BasePager {

    private ListView listview;

    private TextView tv_nodata;
    private ProgressBar pb_loading;

    private ArrayList<MediaItem> mediaItems;
    private Utils utils;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mediaItems != null && mediaItems.size() >0){
                //设置适配器
                listview.setAdapter(new MediaAdapter(context,mediaItems,true));
                tv_nodata.setVisibility(View.GONE);
                pb_loading.setVisibility(View.GONE);
            }else{
                tv_nodata.setVisibility(View.VISIBLE);
                pb_loading.setVisibility(View.GONE);
            }


        }
    };

    public VideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.video_pager,null);
        listview = (ListView) view.findViewById(R.id.listview);
        tv_nodata = (TextView) view.findViewById(R.id.tv_nodata);
        pb_loading = (ProgressBar) view.findViewById(R.id.pb_loading);
        //设置点击某一条的监听
        listview.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        utils = new Utils();
        System.out.println("视频页面的数据被初始化了...");
        //使用ListView
        //1.在布局中定义或者在代码中创建
        //2.实例化ListView
        //3.准备数据
        getData();
        //4.设置适配器
        //5.写适配器item布局
    }

    /**
     * 得到手机里面视频信息
     */
    private void getData() {

        new Thread(){
            @Override
            public void run() {
                super.run();

                SystemClock.sleep(1000);
                mediaItems = new ArrayList<>();
                ContentResolver resolver = context.getContentResolver();
                Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] table = {
                        MediaStore.Video.Media.DISPLAY_NAME,//在Sdcard上的名称
                        MediaStore.Video.Media.DURATION,//视频的时长
                        MediaStore.Video.Media.SIZE,//视频的大小
                        MediaStore.Video.Media.DATA//视频存储的绝对地址
                };
                Cursor cursor =  resolver.query(videoUri, table, null, null, null);
                if(cursor != null){

                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();

                        String name = cursor.getString(0);
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);
//                        String data = "http://vfx.mtime.cn/Video/2016/04/14/mp4/160414103101304893_480.mp4";
//                        String data = "http://cctv13.vtime.cntv.cloudcdn.net:8500/live/no/23_/seg0/index.m3u8?uid=default&AUTH=FU12TwMPtlErIkWAU6HuKXNc9BzhF4+TyDTS17c+Ef9oZtDUzK3QHsxZ8sdw6RdMCwYClrYe0ST8LeH7Os8X5Q==";
                        mediaItem.setData(data);


                        mediaItems.add(mediaItem);

                    }

                    cursor.close();
                }


                handler.sendEmptyMessage(0);

            }
        }.start();




    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            MediaItem mediaItem = mediaItems.get(position);
//            Toast.makeText(context,mediaItem.toString(),Toast.LENGTH_SHORT).show();
            //吊起系统自带的播放器播放视频
//            Intent intent = new Intent();
//            intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
//            context.startActivity(intent);

            //传一个播放地址给视频播放器播放
//            Intent intent = new Intent(context, SystemVideoPlayer.class);
//            intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
//            context.startActivity(intent);

            //传递视频列表给播放器
            Intent intent = new Intent(context, SystemVideoPlayer.class);
            Bundle bundle = new Bundle();
//            intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
            bundle.putSerializable("videolist",mediaItems);
            intent.putExtras(bundle);

            intent.putExtra("position",position);

            context.startActivity(intent);

        }
    }
}

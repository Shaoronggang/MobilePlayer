package com.atguigu.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.Utils.Utils;
import com.atguigu.mobileplayer.domain.MediaItem;

import java.util.ArrayList;

/**
 * 作者：杨光福 on 2016/4/27 14:27
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class MediaAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<MediaItem> mediaItems;
    /**
     * 是否是视频
     */
    private final boolean isvideo;
    private Utils utils;

    public MediaAdapter(Context context, ArrayList<MediaItem> mediaItems,boolean isvideo) {
        this.context = context;
        this.mediaItems = mediaItems;
        utils = new Utils();
        this.isvideo = isvideo;

    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_videopager, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        if(!isvideo){
            viewHolder.iv_icon.setImageResource(R.drawable.music_default_bg);
        }

        //根据位置得到对应数据
        MediaItem mediaItem = mediaItems.get(position);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_size.setText(Formatter.formatFileSize(context, mediaItem.getSize()));
        viewHolder.tv_time.setText(utils.stringForTime((int) mediaItem.getDuration()));

        return convertView;
    }


    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}

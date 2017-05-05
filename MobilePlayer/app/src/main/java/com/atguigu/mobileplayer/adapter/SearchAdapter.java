package com.atguigu.mobileplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.mobileplayer.R;
import com.atguigu.mobileplayer.domain.SearchBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * 作者：杨光福 on 2016/4/30 11:12
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class SearchAdapter extends BaseAdapter {

    private final Context context;
    private final List<SearchBean.ItemsEntity> items;

    public SearchAdapter(Context context,List<SearchBean.ItemsEntity> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_netvideo2,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.iv_video_icon = (ImageView) convertView.findViewById(R.id.iv_video_icon);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //根据位置得到对应数据
        SearchBean.ItemsEntity itemsEntity = items.get(position);
        viewHolder.tv_name.setText(itemsEntity.getItemTitle());
        viewHolder.tv_desc.setText(itemsEntity.getPubTime());

        //加载图片
//            x.image().bind(viewHolder.iv_video_icon,mediaItem.getCoverImg(),imageOptions);
        Glide.with(context).load(itemsEntity.getItemImage().getImgUrl1()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.iv_video_icon);


        return convertView;
    }

    static class ViewHolder{
        ImageView iv_video_icon;
        TextView tv_name;
        TextView tv_desc;
    }
}

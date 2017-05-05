package com.atguigu.mobileplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.atguigu.mobileplayer.domain.Lyric;

import java.util.ArrayList;

/**
 * 作者：杨光福 on 2016/4/26 09:09
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：显示歌词
 */
public class ShowLyricView extends TextView {

    private int width;
    private int height;

    private Paint currentPaint;
    private Paint noCurrentPaint;

    /**
     * 设置歌词列表
     * @param lyrics
     */
    public void setLyrics(ArrayList<Lyric> lyrics) {
        this.lyrics = lyrics;
    }

    private ArrayList<Lyric> lyrics;
    /**
     * 歌词的索引
     */
    private int index;
    /**
     * 每个文本的高
     */
    private float textHeight = 20;
    /**
     * 高亮显示时间
     */
    private float sleepTime;
    /**
     * 时间戳
     */
    private float timePoint;
    /**
     * 当前音频的播放位置
     */
    private int position;

    public ShowLyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 创建画笔和设置假设的歌词
     */
    private void initView() {
        currentPaint = new Paint();
        currentPaint.setColor(Color.GREEN);//设置画笔的颜色
        currentPaint.setAntiAlias(true);//设置抗锯齿
        currentPaint.setTextSize(16);//设置文字大小
        currentPaint.setTextAlign(Paint.Align.CENTER);//设置居中

        noCurrentPaint = new Paint();
        noCurrentPaint.setColor(Color.WHITE);//设置画笔的颜色
        noCurrentPaint.setAntiAlias(true);//设置抗锯齿
        noCurrentPaint.setTextSize(16);//设置文字大小
        noCurrentPaint.setTextAlign(Paint.Align.CENTER);//设置居中
        //添加假设的歌词
//        lyrics = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            Lyric lyric = new Lyric();
//            lyric.setContent(i + "aaaaaaaaaaa" + i);
//            lyric.setSleepTime(2000);//高亮显示
//            lyric.setTimePoint(i * 1000);
//
//            //添加到集合中
//            lyrics.add(lyric);
//
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * 绘制歌词
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lyrics != null && lyrics.size() > 0) {

            //缓缓的往上平移
            float push = 0;
            if (sleepTime == 0) {
                push = 0;
            } else {
                //平移
                //公式：这一句花的时间： 这一句休眠时间  =  这一句要移动的距离：总距离(行高)

                //这一句要移动的距离 = （这一句花的时间/这一句休眠时间） * 总距离(行高)
//                push = ((position - timePoint)/sleepTime)*textHeight;

                //在屏幕上的坐标 =  总距离(行高) + 这一句要移动的距离

                push = textHeight + ((position - timePoint) / sleepTime) * textHeight;

            }


            canvas.translate(0, -push);


            //1.绘制当前播放的歌词-设置绿色
            //得到当前句的内容
            String content = lyrics.get(index).getContent();
            canvas.drawText(content, width / 2, height / 2, currentPaint);

            float tempY = height / 2;//Y轴的中心
            //2.绘制前面部分的歌词-白色
            for (int i = index - 1; i >= 0; i--) {
                String preContent = lyrics.get(i).getContent();
                tempY = tempY - textHeight;
                if (tempY < 0) {
                    break;
                }
                canvas.drawText(preContent, width / 2, tempY, noCurrentPaint);
            }

            //3.绘制后面部分的歌词 -白色

            tempY = height / 2;//Y轴的中心
            //2.绘制前面部分的歌词-白色
            for (int i = index + 1; i < lyrics.size(); i++) {
                String nextContent = lyrics.get(i).getContent();
                tempY = tempY + textHeight;
                if (tempY > height) {
                    break;
                }
                canvas.drawText(nextContent, width / 2, tempY, noCurrentPaint);
            }
        } else {
            //如果没有歌词呢
            canvas.drawText("没有找到歌词", width / 2, height / 2, currentPaint);
        }


    }

    /**
     * 根据当前歌曲播放的进度，计算该高亮显示哪句歌词
     * index sleepTime,timePoint
     *
     * @param position
     */
    public void setNextLyric(int position) {
        this.position = position;
        if (lyrics == null)
            return;

        for (int i = 1; i < lyrics.size(); i++) {

            if (position < lyrics.get(i).getTimePoint()) {

                int tempIndex = i - 1;//0-1

                //这一句就是我们要找的歌词
                if (position >= lyrics.get(tempIndex).getTimePoint()) {
                    index = tempIndex;//当前的索引
                    sleepTime = lyrics.get(tempIndex).getSleepTime();//当前句的高亮显示时间
                    timePoint = lyrics.get(tempIndex).getTimePoint();//时间戳
                }


            }


        }

        //重新绘制
//        postInvalidate();;//子线程绘制
        invalidate();//会导致onDraw()方法执行

    }
}

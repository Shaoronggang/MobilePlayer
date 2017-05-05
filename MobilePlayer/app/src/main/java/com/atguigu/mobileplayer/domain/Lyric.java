package com.atguigu.mobileplayer.domain;

/**
 * 作者：杨光福 on 2016/4/26 09:19
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：代表的是一句歌词
 * 例如：[00:31.13]当我走在这里的每一条街道
 */
public class Lyric {

    /**
     * 歌词内容
     * 例如：当我走在这里的每一条街道
     */
    private String content;

    /**
     * 时间戳
     * 例如：00:31.13
     * 但是解析的时候把它变成是long类型
     */
    private long timePoint;

    /**
     * 歌词的高亮时间
     */
    private long sleepTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}

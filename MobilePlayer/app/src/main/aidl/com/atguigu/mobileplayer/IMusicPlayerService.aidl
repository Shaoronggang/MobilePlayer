// IMusicPlayerService.aidl
package com.atguigu.mobileplayer;

// Declare any non-default types here with import statements

interface IMusicPlayerService {

 /**
     * 根据位置打开音频
     * @param position
     */
     void openAudio(int position);

    /**
     * 音频的播放
     */
     void play();

    /**
     * 音频的暂停
     */
     void pause();


    /**
    播放下一个音频
     */
     void  next();
    /**
     播放上一个音频
     */
     void  pre();
    /**
     * 音频的拖动
     * @param position
     */
     void seekTo(int position);
    /**
     * 得到当前的播放位置
     * @return
     */
     int getCurrentPosition();
    /**
     * 得到总时长
     * @return
     */
     int getDuration();

    /**
     * 判断是否在播放音频
     * @return
     */
     boolean isPlaying();

    /**
     * 设置播放模式
     * @param playmode
     */
     void setPlayMode(int playmode);
    /**
     * 得到播放模式
     * @return
     */
     int getPlayMode();

      /**
          * 得到歌曲的名称
          * @return
          */
          String getAudioName();

         /**
          * 得到艺术家
          * @return
          */
          String getArtist();


          void notifyChange(String action);

          String getAudioPath();
          int getAudioSessionId();



}

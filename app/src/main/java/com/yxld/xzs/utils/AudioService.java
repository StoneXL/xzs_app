package com.yxld.xzs.utils;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yishangfei on 2017/4/5 0005.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */
public class AudioService extends Service {

    private MediaPlayer mediaPlayer;
    private String word;

    @Override
    public void onCreate() {
        Log.d("...", "初始化音乐资源");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (word != null && !word.equals(intent.getStringExtra("query")) && mediaPlayer != null) {
            mediaPlayer.start();
        } else {
            String word = intent.getStringExtra("word");
            Uri location = Uri.parse("http://img0.hnchxwl.com/" + word);

            mediaPlayer = MediaPlayer.create(this, location);
            //Log.d("...", "音乐开始播放");
            mediaPlayer.start();
            // 音乐播放完毕的事件处理
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    //不循环播放
                    try {
                        // mp.start();
                        System.out.println("stopped");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 播放音乐时发生错误的事件处理
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    //释放资源
                    try {
                        mp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 服务停止时停止播放音乐并释放资源
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

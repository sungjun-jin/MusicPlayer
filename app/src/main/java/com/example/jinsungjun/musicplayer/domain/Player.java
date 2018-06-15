package com.example.jinsungjun.musicplayer.domain;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static MediaPlayer mediaPlayer;

    //플레이 리스트의 현 상태 정의
    public static final int STOP = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int EMPTY = 99;

    public static int status = EMPTY;

    public static SeekBarThread seekBarThread = null;

    private static SeekbarCallback seekbarCallback;


    public static void set(Context context, Uri uri) {
        if (status != EMPTY) {
            //미디어 플레이어에 음원이 들어가 있다
            mediaPlayer.stop();
            //메모리를 해제
            mediaPlayer.release();

        }
        if (seekBarThread != null) {
            seekBarThread.stopThread();
        }
        seekBarThread = new SeekBarThread();
        mediaPlayer = MediaPlayer.create(context, uri);

    }

    public static void play() {
        //음원이 없는 경우를 대비해 null처리를 해준다

        if (mediaPlayer != null) {

            mediaPlayer.start();
            if (!seekBarThread.running)
                seekBarThread.start();
                status = PLAY;
        }

    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            status = PAUSE;
        }
    }

    public static long getCurrent() {

        if (mediaPlayer != null) {
            //구간을 넘겨줄 수 있다.
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }


    public interface SeekbarCallback {
        public void setSeekbar(int position);
    }

    public static void setSeekbar(SeekbarCallback seekbar) {
        Player.seekbarCallback = seekbar;
    }

    public static void removeSeekbar() {
        Player.seekbarCallback = null;
    }


    public static class SeekBarThread extends Thread {

        boolean runFlag = true;
        public boolean running = false;

        @Override
        public void run() {

            while (runFlag) {

                try {
                    if (mediaPlayer == null)
                        break;
                    if (seekbarCallback != null) {
                        seekbarCallback.setSeekbar(mediaPlayer.getCurrentPosition());
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            running = false;
        }

        public void stopThread() {
            runFlag = false;
        }
    }

}

package com.example.jinsungjun.musicplayer.domain;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class Player {

    private static MediaPlayer mediaPlayer;

    //플레이 리스트의 현 상태 정의
    private static final int STOP = 0;
    private static final int PLAY = 1;
    private static final int PAUSE = 2;
    private static final int EMPTY = 99;

    private static int status = EMPTY;

    public static void set(Context context, Uri uri) {
        if(mediaPlayer!=null && status != EMPTY) {
            //미디어 플레이어에 음원이 들어가 있다
            mediaPlayer.stop();
            //메모리를 해제
            mediaPlayer.release();
            status = EMPTY;
        }
        mediaPlayer = MediaPlayer.create(context,uri);
        status = STOP;
    }

    public static void play() {
        //음원이 없는 경우를 대비해 null처리를 해준다
        if(mediaPlayer != null) {
            mediaPlayer.start();
            status = PLAY;
        }
    }

    public static void pause() {
        if(mediaPlayer != null && status == PLAY) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            status = PAUSE;
        }
    }

}

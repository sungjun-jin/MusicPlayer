package com.example.jinsungjun.musicplayer.domain;

import android.net.Uri;
import android.provider.MediaStore;

public class Music {

    public String id;
    public String title;
    public String artist;
    public String albumart_id;
    public long duration;


    public Uri albumart_uri;
    public Uri music_uri;

    public void setMusicUri() {

        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //Uri와 문자열,숫자를 합쳐 새로운 Uri를 만들어준다
        music_uri = Uri.withAppendedPath(contentUri,id);
    }

    public void setAlbumArtUri() {

//        Uri uri = MediaStore.Audio.Albums.ALBUM_ART
        String contentUri = "content://media/external/audio/albumart/";
        albumart_uri = Uri.parse(contentUri+albumart_id);

    }
}

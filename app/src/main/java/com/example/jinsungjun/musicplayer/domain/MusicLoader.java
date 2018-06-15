package com.example.jinsungjun.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MusicLoader {

    //Singleton 사용
    //모든 프로그램에서 하나의 인스턴스만 사용할 때
    private static List<Music> musicList = null;

    public static List<Music> getMusic(Context context) {


        if (musicList == null) {

            musicList = new ArrayList<>();
        }

        //음원 데이터가 존재하지 않을 경우

        if (musicList.size() < 1) {

            ContentResolver resolver = context.getContentResolver();

            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            String projections[] = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ALBUM_ID,
                    MediaStore.Audio.Media.DURATION
            };

            Cursor cursor = resolver.query(uri, projections, null, null, MediaStore.Audio.Media.TITLE + " asc"); //" asc" 주의

            if (cursor != null) {

                while (cursor.moveToNext()) {

                    Music music = new Music();

                    music.id = getColumnString(cursor, projections[0]);
                    music.title = getColumnString(cursor, projections[1]);
                    music.artist = getColumnString(cursor, projections[2]);
                    music.albumart_id = getColumnString(cursor, projections[3]);
                    music.duration = getColumnLong(cursor, projections[4]);
                    //노래와 앨범 커버이미지가 세팅
                    music.setAlbumArtUri();
                    music.setMusicUri();
                    musicList.add(music);
                }
            }
        }
        return musicList;
    }

    private static String getColumnString(Cursor cursor, String column) {

        int index = cursor.getColumnIndex(column);
        return cursor.getString(index);

    }

    private static long getColumnLong(Cursor cursor, String column) {

        int index = cursor.getColumnIndex(column);
        return cursor.getLong(index);

    }

    private static int getColumnInt(Cursor cursor, String column) {

        int index = cursor.getColumnIndex(column);
        return cursor.getInt(index);

    }
}

package com.example.jinsungjun.musicplayer.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinsungjun.musicplayer.R;
import com.example.jinsungjun.musicplayer.domain.Music;

import java.util.List;

public class PlayerAdapter extends PagerAdapter {

    List<Music> musicList;

    public PlayerAdapter(List<Music> musicList) {
        //데이터 로딩
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }


    //View를 inflate하는 함수
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //1. 뷰 생성
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item,container,false);
        //2. 위젯 연결
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textArtist = view.findViewById(R.id.textArtist);
        TextView textTitle = view.findViewById(R.id.textTitle);
        //3. 데이터 세팅
        Music music = musicList.get(position);
        //4. 위젯, 텍스트뷰 세팅
        imageView.setImageURI(music.albumart_uri);
        textTitle.setText(music.title);
        textArtist.setText(music.artist);

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//      super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

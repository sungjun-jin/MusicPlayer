package com.example.jinsungjun.musicplayer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.jinsungjun.musicplayer.adapter.PlayerAdapter;
import com.example.jinsungjun.musicplayer.domain.Music;
import com.example.jinsungjun.musicplayer.domain.MusicLoader;
import com.example.jinsungjun.musicplayer.domain.Player;

import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {



    //ViewPager 1개의 page 번호
    private int position = 0;
    ViewPager viewPager;
    PlayerAdapter adapter;
    List<Music> musicList;
    Music music;

    SeekBar seekBar;
    TextView textDuration, textCurrent;
    ImageButton btnPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        //data를 가져온다
        musicList = MusicLoader.getMusic(this);
        setView();
        //Adapter 세팅
        adapter = new PlayerAdapter(musicList);
        //ViewPager 세팅
        viewPager.setAdapter(adapter);
        Intent intent = getIntent();
        position = intent.getIntExtra(Const.POSITION,0);
        viewPager.setCurrentItem(position);


        setPlayer();
        setViewPagerListener();


    }

    private void setViewPagerListener() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //페이지를 변경하면 동작
                PlayerActivity.this.position = position;
                // 버튼의 이미지 리소스 변경
                btnPlay.setImageResource(android.R.drawable.ic_media_play);
                setPlayer();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPlayer() {

        //Player Status 상태의 따라 재생상태를 변경
        music = musicList.get(position);
        setCurrent(0);
        Player.set(this,music.music_uri);
        setDuration(music.duration);
        //seekbar 세팅
        seekBar.setMax((int)music.duration);
        Player.setSeekbar(new Player.SeekbarCallback() {
            @Override
            public void setSeekbar(final int position) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(position);
                        setCurrent(position);
                    }
                });
            }
        });
        if(Player.status == Player.PLAY) {
            Player.play();
        } else if(Player.status == Player.EMPTY || Player.status == Player.PAUSE) {
            Player.status = Player.STOP;
        }

    }

    private void setDuration(long duration) {
        //나중에 sdf로 고치기

        //음원 재생 시간 구하기
        String seconds = String.valueOf((duration % 60000) / 1000); //분
        String minutes = String.valueOf(duration / 60000); //초
        textDuration.setText(minutes + ":" + seconds);
    }

    private void setCurrent(long current) {

        String seconds = String.valueOf((current % 60000) / 1000); //분
        String minutes = String.valueOf(current / 60000); //초
        textCurrent.setText(minutes + ":" + seconds);
    }

    private void setView() {
        viewPager = findViewById(R.id.viewPager);
        seekBar = findViewById(R.id.seekBar);
        textCurrent = findViewById(R.id.textCurrent);
        textDuration = findViewById(R.id.textDuration);

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);
        findViewById(R.id.btnFF).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.btnRew).setOnClickListener(this);
        findViewById(R.id.btnPreview).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnPlay :
                if(Player.status == Player.PAUSE || Player.status == Player.STOP) {
                    Player.play();
                    // 버튼의 이미지 리소스 변경
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause);
                } else if(Player.status == Player.PLAY) {
                    btnPlay.setImageResource(android.R.drawable.ic_media_play);
                    Player.pause();
                }

                break;
            case R.id.btnFF :
                break;
            case R.id.btnNext :
                if(position < musicList.size())
                position++;
                viewPager.setCurrentItem(position);
                break;
            case R.id.btnRew :
                break;
            case R.id.btnPreview :
                if(position > 0)
                position--;
                viewPager.setCurrentItem(position);
                break;
        }

    }
}

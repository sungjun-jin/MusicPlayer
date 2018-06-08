package com.example.jinsungjun.musicplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinsungjun.musicplayer.R;
import com.example.jinsungjun.musicplayer.domain.Music;
import com.example.jinsungjun.musicplayer.domain.Player;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {

    List<Music> musicList;

    public MusicAdapter(List<Music> musicList) {
        this.musicList = musicList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Music music = musicList.get(position);
        holder.setMusic(music);
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private ImageView albumArt;
        private TextView textTitle, textArtist, textDuration;
        private ImageButton btnPlay;
        //사용하는 domain 클래스를 홀더에 담아주면 편하다
        private Music music;

        public Holder(View itemView) {
            super(itemView);
            albumArt = itemView.findViewById(R.id.albumArt);
            textTitle = itemView.findViewById(R.id.textTitle);
            textArtist = itemView.findViewById(R.id.textArtist);
            textDuration = itemView.findViewById(R.id.textDuration);
            btnPlay = itemView.findViewById(R.id.btnPlay);

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    play(view.getContext());

                }
            });
        }

        private void setMusic(Music music) {
            this.music = music;
            setAlbumart();
            setTitle();
            setArtist();
            setDuration();
        }

        private void setAlbumart() {
            albumArt.setImageURI(music.albumart_uri);
        }

        private void setTitle() {
            textTitle.setText(music.title);
        }

        private void setArtist() {
            textArtist.setText(music.artist);
        }

        private void setDuration() {

            String m = "";
            String s = "";
            textDuration.setText(music.duration + "");
        }

        private void play(Context context) {
            Player.set(context,music.music_uri);
            Player.play();

        }
    }


}
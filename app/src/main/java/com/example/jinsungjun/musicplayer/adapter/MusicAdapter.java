package com.example.jinsungjun.musicplayer.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinsungjun.musicplayer.Const;
import com.example.jinsungjun.musicplayer.PlayerActivity;
import com.example.jinsungjun.musicplayer.R;
import com.example.jinsungjun.musicplayer.domain.Music;

import java.text.SimpleDateFormat;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {

    private List<Music> musicList;
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    public MusicAdapter(List<Music> musicList) {
        //MainActivity에서 인스턴스를 생성하여 MusicLoader 클래스에서 생성된 데이터 리스트를 가져온다
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
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView albumArt;
        TextView textTitle, textArtist, textDuration;
        //사용하는 domain 클래스를 홀더에 담아주면 편하다
        Music music;
        int position;


        public Holder(View itemView) {
            super(itemView);
            albumArt = itemView.findViewById(R.id.albumArt);
            textTitle = itemView.findViewById(R.id.textTitle);
            textArtist = itemView.findViewById(R.id.textArtist);
            textDuration = itemView.findViewById(R.id.textDuration);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                    intent.putExtra(Const.POSITION,position);
                    view.getContext().startActivity(intent);
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
            textDuration.setText(sdf.format(music.duration));
        }
    }


}

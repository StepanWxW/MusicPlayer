package com.example.musicplayer.state;

import org.videolan.libvlc.MediaPlayer;

public class PlayMusic implements Music {

    @Override
    public void action(MediaPlayer mediaPlayer) {
        mediaPlayer.play();
    }
}

package com.example.musicplayer.state;

import org.videolan.libvlc.MediaPlayer;

public class PauseMusic implements Music {


    @Override
    public void action(MediaPlayer mediaPlayer) {
        mediaPlayer.pause();
    }
}

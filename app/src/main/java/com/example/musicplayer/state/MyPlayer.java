package com.example.musicplayer.state;

import org.videolan.libvlc.MediaPlayer;

public class MyPlayer {
    Music music;
    MediaPlayer mediaPlayer;
    public MyPlayer(Music music, MediaPlayer mediaPlayer) {
        this.music = music;
        this.mediaPlayer = mediaPlayer;
    }
    public void setActionMusic(Music music) {
        this.music = music;
    }

    public void changeAction() {
        if (mediaPlayer.getPlayerState() == 3) {
            setActionMusic(new PauseMusic());
        } else if (mediaPlayer.getPlayerState() == 4) {
            setActionMusic(new PlayMusic());
        } else if(mediaPlayer.getPlayerState() == 5) {
            setActionMusic(new PlayMusic());
        }
    }
    public void action (MediaPlayer mediaPlayer) {
        music.action(mediaPlayer);
    }
}

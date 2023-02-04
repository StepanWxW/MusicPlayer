package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicplayer.state.Music;
import com.example.musicplayer.state.MyPlayer;
import com.example.musicplayer.state.PlayMusic;
import com.example.musicplayer.download.DownloadFileFromURL;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    private final static String FILE_NAME = "sound.mp3";
    private final static String LINK = "https://drive.google.com/uc?export=download&id=1hg67SQSsxWdXk19J2ney5n6s3HN5pLd6";

    LibVLC libVLC;
    MediaPlayer mediaPlayer;
    Handler handler;
    Button button;
    Music music;
    MyPlayer myPlayer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();
        String dir = String.valueOf(context.getFilesDir());
        String dirFileName = dir + "/" + FILE_NAME;

        button = findViewById(R.id.button);
        button.setEnabled(false);
        Toast.makeText(context, "Загрузка файла", Toast.LENGTH_SHORT).show();
        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) {
                    button.setEnabled(true);
                    Toast.makeText(context, "Файл скачан, можно слушать)", Toast.LENGTH_SHORT).show();
                }
            }
        };
        new DownloadFileFromURL(LINK, FILE_NAME, dir, handler).start();

        libVLC = new LibVLC(this);
        mediaPlayer = new MediaPlayer(libVLC);
        final Media media = new Media(libVLC, dirFileName);
        mediaPlayer.setMedia(media);
        media.release();
        music = new PlayMusic();
        myPlayer = new MyPlayer(music, mediaPlayer);
    }
    public void clickButton(View view) {
        myPlayer.changeAction();
        myPlayer.action(mediaPlayer);
    }
}

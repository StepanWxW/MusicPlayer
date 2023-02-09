package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.musicplayer.download.DownloadFileFromURL;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;


public class MainActivity extends AppCompatActivity
{
    private final static String FILE_NAME = "sound.mp3";
    private final static String LINK = "https://drive.google.com/uc?export=download&id=1hg67SQSsxWdXk19J2ney5n6s3HN5pLd6";
    private static final int PERMISSION_REQUEST_CODE = 100;
    LibVLC mLibVLC;
    MediaPlayer mMediaPlayer;
    Handler mHandler;
    Context mContext;
    String mDir;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();
        mDir = String.valueOf(mContext.getFilesDir());
        String dirFileName = mDir + "/" + FILE_NAME;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            Toast.makeText(mContext, "Загрузка файла", Toast.LENGTH_SHORT).show();
            new DownloadFileFromURL(LINK, FILE_NAME, mDir, mHandler).start();
        }

        Button button = findViewById(R.id.button);
        button.setEnabled(false);

        mHandler = new Handler(Looper.getMainLooper())
        {
            public void handleMessage(android.os.Message msg)
            {
                if (msg.what == 1)
                {
                    button.setEnabled(true);
                    Toast.makeText(mContext, "Файл скачан, можно слушать)", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mLibVLC = new LibVLC(this);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        final Media media = new Media(mLibVLC, dirFileName);
        mMediaPlayer.setMedia(media);
        media.release();
    }

    public void clickButton(View view)
    {
        if (mMediaPlayer.getPlayerState() == 3)
        {
            mMediaPlayer.pause();
        }
        else
        {
            mMediaPlayer.play();
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                     Toast.makeText(mContext, "Загрузка файла", Toast.LENGTH_SHORT).show();
                    new DownloadFileFromURL(LINK, FILE_NAME, mDir, mHandler).start();
                }
                else
                {
                    Toast.makeText(mContext, "Для скачивания файла нужно разрешение", Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "Его можно получить в найстройках телефона", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
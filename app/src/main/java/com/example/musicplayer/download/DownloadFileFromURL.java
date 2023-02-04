package com.example.musicplayer.download;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadFileFromURL extends  Thread {
    private final String src;
    private final String file;
    private final String dir;
    private final Handler handler;

    public DownloadFileFromURL(String src, String file, String dir, Handler handler) {
        this.src = src;
        this.file = file;
        this.dir = dir;
        this.handler = handler;
    }

    @Override
    public void run() {
        File dest = new File(dir, file);
        try {
            FileUtils.copyURLToFile(new URL(src), dest);
            handler.sendEmptyMessage(1);
        } catch (IOException e) {
            System.out.println("Не получилось сохранить файл по ссылке");
        }
    }
}

package com.example.musicplayer.download;

import android.os.Handler;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadFileFromURL extends  Thread
{
    private final String mSrc;
    private final String mFile;
    private final String mDir;
    private final Handler mHandler;

    public DownloadFileFromURL(String src, String file, String dir, Handler handler)
    {
        this.mSrc = src;
        this.mFile = file;
        this.mDir = dir;
        this.mHandler = handler;
    }

    @Override
    public void run()
    {
        File dest = new File(mDir, mFile);
        try
        {
            FileUtils.copyURLToFile(new URL(mSrc), dest);
            mHandler.sendEmptyMessage(1);
        }
        catch (IOException e)
        {
            System.out.println("Не получилось сохранить файл по ссылке");
        }
    }
}

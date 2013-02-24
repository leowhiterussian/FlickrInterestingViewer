package com.leo.flickrinterestingviewer;

import android.app.Application;

import com.util.GlobalUtil;
import com.util.NetworkUtil;
import com.util.JSONUtil;

public class FlickrInterestingViewer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalUtil.getSingletonObject(this);
        NetworkUtil.getSingletonObject(this);
        JSONUtil.getSingletonObject(this);

    }
}

package com.leo.flickrinterestingviewer;

import android.app.Activity;
import android.os.Bundle;

import com.tomdignan.remoteimage.RemoteImageView;
import com.util.GlobalUtil;

public class DetailView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        RemoteImageView image = (RemoteImageView) findViewById(R.id.image);
        image.setImageUrl(GlobalUtil.buildImageUrl("b"));
        image.loadImage();
        
    }
}

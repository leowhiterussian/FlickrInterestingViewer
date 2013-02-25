package com.leo.flickrinterestingviewer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;

import com.tomdignan.remoteimage.RemoteImageView;
import com.tomdignan.remoteimage.RemoteImageView.RemoteImageViewListener;
import com.util.GlobalUtil;

public class DetailView extends Activity {

    private RemoteImageView image;
    private GestureDetector gestureDetector;
    private OnTouchListener gestureListener;
    private AlphaAnimation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(500);
        
        image = (RemoteImageView) findViewById(R.id.image);
        
        gestureDetector = new GestureDetector(this, new GestureListener(this));
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        (findViewById(R.id.layout)).setOnTouchListener(gestureListener);
        
        loadImage();
    }
    
    private void loadImage() {
        image.setImageUrl(GlobalUtil.buildImageUrl("b"));
        image.setRemoteImageViewListener(new RemoteImageViewListener() {
            
            @Override
            public void onImageLoaded(Bitmap b) {
                image.startAnimation(fadeIn);
                image.setVisibility(View.VISIBLE);
                //TODO: Add preloading of images for the next and previous
            }

        });
        image.loadImage();
    }

    public void loadNext() {
        int index = GlobalUtil.photos.indexOf(GlobalUtil.detailImage);
        if (index < GlobalUtil.photos.size()) {
            GlobalUtil.detailImage = GlobalUtil.photos.get(index + 1);
            loadImage();
        }  
        //TODO: Add ability to keep going further than list has loaded
    }

    public void loadPrevious() {
        int index = GlobalUtil.photos.indexOf(GlobalUtil.detailImage);
        if (index > 0) {
            GlobalUtil.detailImage = GlobalUtil.photos.get(index - 1);
            loadImage();
        }  
    }
}

package com.leo.flickrinterestingviewer;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureListener extends SimpleOnGestureListener {

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MIN = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    private DetailView detailViewActivity;

    public GestureListener(DetailView activity) {
        this.detailViewActivity = activity;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && Math.abs(e1.getX() - e2.getX()) < SWIPE_MIN) {
                detailViewActivity.finish();
            } else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY && Math.abs(e1.getX() - e2.getX()) < SWIPE_MIN) {
                detailViewActivity.finish();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && Math.abs(e1.getY() - e2.getY()) < SWIPE_MIN) {
                detailViewActivity.loadPrevious();
            } else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && Math.abs(e1.getY() - e2.getY()) < SWIPE_MIN) {
                detailViewActivity.loadNext();
            }
        } catch (Exception e) {
        }
        return false;
    }
}
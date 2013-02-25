package com.util;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.leo.flickrinterestingviewer.FlickrInterestingViewer;
import com.leo.flickrinterestingviewer.FlickrPhoto;
public class GlobalUtil {

    public static FlickrInterestingViewer mApplication;
    private static GlobalUtil GlobalUtilObject;

    public static FlickrPhoto detailImage;
    private SharedPreferences defaultSharedPreferences;
    
    public static ArrayList<FlickrPhoto> photos = new ArrayList<FlickrPhoto>();

    /** A private Constructor prevents any other class from instantiating. */
    private GlobalUtil(final FlickrInterestingViewer application) {
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);

        mApplication = application;

//        ril = new RemoteImageLoader(mApplication, true);
//        ril.setThreadPoolSize(5);
//        ril.setMaxDownloadAttempts(4);
//        ril.setDefaultBufferSize(30000);
//        ril.setDownloadFailedDrawable(null);
//        imageCache = new ImageCache(400, 20, 4);
//        ril.setImageCache(imageCache);
//        RemoteImageView.setSharedImageLoader(ril);

    }

    public static synchronized GlobalUtil getSingletonObject(final FlickrInterestingViewer application) {
        if (GlobalUtilObject == null)
            GlobalUtilObject = new GlobalUtil(application);
        return GlobalUtilObject;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    /*
     * Logging and tracking
     */

    private static boolean debugging = true;
    public static void log(String tag, String message) {
        try {
            if (debugging) {
                Log.e(tag, message);
            } 
        } catch (Exception e) {
        }
    }

    public static void log(String tag, Exception e) {
        try {
            if (debugging) {
                if (e != null) {
                    try {
                        Log.e(tag, e.getLocalizedMessage());
                    } catch (Exception ee) {
                        log(tag, "no message");
                    }
                } else
                    log(tag, "no message");
            }
        } catch (Exception ee) {
        }
    }

    public static String buildImageUrl(FlickrPhoto item, String size) {
        String url = "http://farmfarm_id.staticflickr.com/server_id/photo_id_secret_size.jpg";
        
        url = url.replace("farm_id", item.getFarm() + "");
        url = url.replace("server_id", item.getServer());
        url = url.replace("photo_id", item.getId());
        url = url.replace("secret", item.getSecret());
        url = url.replace("size", size);
        
        return url;
    }

    public static String buildImageUrl(String size) {
        String url = buildImageUrl(detailImage, size);
        return url;
    }
} 
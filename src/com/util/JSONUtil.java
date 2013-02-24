package com.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.leo.flickrinterestingviewer.FlickrInterestingViewer;
import com.leo.flickrinterestingviewer.FlickrPhoto;
import com.leo.flickrinterestingviewer.FlickrResult;

public class JSONUtil {
    
    private static JSONUtil JSONUtilObject;
    
    public static synchronized JSONUtil getSingletonObject(final FlickrInterestingViewer application) {
        if (JSONUtilObject == null)
            JSONUtilObject = new JSONUtil();
        return JSONUtilObject;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static boolean hasElements(final JSONArray a) {
        if (a == null || a.length() == 0)
            return false;
        else
            return true;
    }
    
    public static FlickrResult parseList(String json) throws Exception {
        FlickrResult result = new FlickrResult();
        
        final JSONObject object = (JSONObject) new JSONTokener(json).nextValue();
        
        JSONObject photos = object.getJSONObject("photos");
        result.setPage(photos.getInt("page"));
        result.setPages(photos.getInt("pages"));
        result.setPerPage(photos.getInt("perpage"));
        result.setTotal(photos.getInt("total"));
        
        JSONArray imageArray = photos.getJSONArray("photo");
        if (hasElements(imageArray)) {
            final int len = imageArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject photo = imageArray.getJSONObject(i);
                FlickrPhoto flickrPhoto = new FlickrPhoto(photo);
                result.addPhoto(flickrPhoto);
            }
        }
        
        return result;
    }
    
}
package com.leo.flickrinterestingviewer;

import com.tomdignan.remoteimage.RemoteImageView;
import com.util.GlobalUtil;

public class ThumbnailHolder {

    public RemoteImageView image;
    
    public void setValues(FlickrPhoto item) {
        image.setImageUrl(GlobalUtil.buildImageUrl(item, "s"));
        image.loadImage();
    }

}

package com.leo.flickrinterestingviewer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tomdignan.remoteimage.RemoteImageView;
import com.util.GlobalUtil;

public class ImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        inflater = LayoutInflater.from(c);
    }

    public void addData(ArrayList<FlickrPhoto> photos) {
        GlobalUtil.photos.addAll(photos);
        notifyDataSetChanged();
    }
    
    public int getCount() {
        return GlobalUtil.photos.size();
    }

    public FlickrPhoto getItem(int position) {
        if (GlobalUtil.photos.size() > position)
            return GlobalUtil.photos.get(position);
        return null;
    }

    public long getItemId(int position) {
        try {
            return getItem(position).getId().hashCode();
        } catch (Exception e) {
            return 0L;
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ThumbnailHolder th = null;
        if (convertView == null) {
            th = new ThumbnailHolder();
            convertView = inflater.inflate(R.layout.thumbnail_view, parent, false);
            th.image = (RemoteImageView) convertView.findViewById(R.id.image);
            
            convertView.setTag(th);
        } else
            th = (ThumbnailHolder) convertView.getTag();

        th.setValues(getItem(position));
        return convertView;
    }

    
}
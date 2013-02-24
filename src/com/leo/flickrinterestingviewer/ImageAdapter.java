package com.leo.flickrinterestingviewer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tomdignan.remoteimage.RemoteImageView;
import com.util.GlobalUtil;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FlickrPhoto> mData;
    private LayoutInflater inflater;

    public ImageAdapter(Context c) {
        mContext = c;
        inflater = LayoutInflater.from(c);
        mData = new ArrayList<FlickrPhoto>();
    }

    public void addData(ArrayList<FlickrPhoto> photos) {
        mData.addAll(photos);
        notifyDataSetChanged();
    }
    
    public int getCount() {
        return mData.size();
    }

    public FlickrPhoto getItem(int position) {
        return mData.get(position);
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
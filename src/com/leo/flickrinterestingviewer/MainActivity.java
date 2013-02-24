package com.leo.flickrinterestingviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.util.GlobalUtil;
import com.util.JSONUtil;
import com.util.NetworkUtil;

public class MainActivity extends Activity {

    public int page = 0;
    public int pages;
    public int perPage = 50;
    public int total;
    private ImageAdapter adapter;
    private AsyncTask<Void, Integer, FlickrResult> loadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);

        loadTask = new LoadImageList().execute();
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent detail = new Intent(MainActivity.this, DetailView.class);
                GlobalUtil.detailImage = adapter.getItem(position);
                startActivity(detail);
            }
        });
        
        gridview.setOnScrollListener(new OnScrollListener() {

            private int priorFirst;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    if ((firstVisibleItem + visibleItemCount) > totalItemCount - 5) {
                        if (firstVisibleItem + 9 <= priorFirst || firstVisibleItem - 9 >= priorFirst || totalItemCount - visibleItemCount <= 9) {
                            priorFirst = firstVisibleItem;
                            if (shouldLoadMore()) {
                                if ((loadTask == null) || (loadTask.getStatus() != AsyncTask.Status.RUNNING))
                                    loadTask = new LoadImageList().execute();
                            }
                            GlobalUtil.log("MainActivity", "should load more");
                        }
                    }
                } catch (Exception e) {
                    GlobalUtil.log("MainActivity: onScrollListener", e);
                }
            }

            
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

        });
    }
    private boolean shouldLoadMore() {
        return (total > 0 && total > page * perPage);
    }

    
    private class LoadImageList extends AsyncTask<Void, Integer, FlickrResult> {

        @Override
        protected FlickrResult doInBackground(Void... params) {
            try {
                String result = NetworkUtil.getInterestingList(++page, perPage);
                FlickrResult flickrResult = JSONUtil.parseList(result);
                return flickrResult;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(FlickrResult flickrResult) {
            if (flickrResult != null) {
                page = flickrResult.getPage();
                pages = flickrResult.getPages();
                perPage = flickrResult.getPerPage();
                total = flickrResult.getTotal();
                adapter.addData(flickrResult.getPhotos());
            }
        }
    }
    
    
}

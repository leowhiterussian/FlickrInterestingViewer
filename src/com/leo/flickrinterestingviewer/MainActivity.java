package com.leo.flickrinterestingviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.util.GlobalUtil;
import com.util.JSONUtil;
import com.util.NetworkUtil;

public class MainActivity extends SherlockActivity {

    public int page = 0;
    public int pages;
    public int perPage = 51;
    public int total;
    private ImageAdapter adapter;
    private AsyncTask<Void, Integer, FlickrResult> loadTask;
    private GridView gridView;
    private ProgressBar empty;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Refresh")
            .setIcon(R.drawable.ic_refresh)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        refresh();
        return true;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        empty = (ProgressBar) findViewById(R.id.Empty);
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        loadTask = new LoadImageList().execute();
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent detail = new Intent(MainActivity.this, DetailView.class);
                GlobalUtil.detailImage = adapter.getItem(position);
                startActivity(detail);
            }
        });
        
        gridView.setOnScrollListener(new OnScrollListener() {

            private int priorFirst;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    if (totalItemCount - firstVisibleItem - visibleItemCount <= 9) {
                        if (firstVisibleItem >= priorFirst) { //TODO: Finetune this
                            priorFirst = firstVisibleItem;
                            if (shouldLoadMore()) {
                                if ((loadTask == null) || (loadTask.getStatus() != AsyncTask.Status.RUNNING))
                                    loadTask = new LoadImageList().execute();
                            }
                            
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
        protected void onPreExecute() {
            if (GlobalUtil.photos.size() == 0)
                empty.setVisibility(View.VISIBLE);
        }
        
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
            
            empty.setVisibility(View.GONE);
        }
    }
    
    private void refresh() {
        GlobalUtil.photos = new ArrayList<FlickrPhoto>();
        page = 0;
        perPage = 51;
        new LoadImageList().execute();
    }   
}
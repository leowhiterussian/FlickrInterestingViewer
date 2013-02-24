package com.leo.flickrinterestingviewer;

import java.util.ArrayList;

public class FlickrResult {
    
    private int page;
    private int pages;
    private int perPage;
    private int total;
    private ArrayList<FlickrPhoto> photos;
    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }
    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }
    /**
     * @return the pages
     */
    public int getPages() {
        return pages;
    }
    /**
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }
    /**
     * @return the perPage
     */
    public int getPerPage() {
        return perPage;
    }
    /**
     * @param perPage the perPage to set
     */
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
    /**
     * @return the photos
     */
    public ArrayList<FlickrPhoto> getPhotos() {
        return photos;
    }
    
    public void addPhoto(FlickrPhoto flickrPhoto) {
        if (this.photos == null)
            this.photos = new ArrayList<FlickrPhoto>();
        
        this.photos.add(flickrPhoto);
    }

}

package com.leo.flickrinterestingviewer;

import org.json.JSONObject;

public class FlickrPhoto {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private boolean ispublic;
    private boolean isfriend;
    private boolean isfamily;
    
    public FlickrPhoto(JSONObject photo) {
        try {
            setId(photo.getString("id"));
            setOwner(photo.getString("owner"));
            setSecret(photo.getString("secret"));
            setServer(photo.getString("server"));
            setFarm(photo.getInt("farm"));
            setTitle(photo.getString("title"));
            setIsPublic(photo.getInt("ispublic"));
            setIsFriend(photo.getInt("isfriend"));
            setIsFamily(photo.getInt("isfamily"));
        } catch (Exception e) {
            
        }
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }
    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }
    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }
    /**
     * @return the farm
     */
    public int getFarm() {
        return farm;
    }
    /**
     * @param farm the farm to set
     */
    public void setFarm(int farm) {
        this.farm = farm;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the ispublic
     */
    public boolean isIspublic() {
        return ispublic;
    }
    /**
     * @param ispublic the ispublic to set
     */
    public void setIspublic(boolean ispublic) {
        this.ispublic = ispublic;
    }
    

    private void setIsPublic(int bool) {
        if (bool == 0)
            this.ispublic = false;
        else
            this.ispublic = true;
    }
    /**
     * @return the isfriend
     */
    public boolean isIsfriend() {
        return isfriend;
    }
    /**
     * @param isfriend the isfriend to set
     */
    public void setIsfriend(boolean isfriend) {
        this.isfriend = isfriend;
    }
    
    private void setIsFriend(int bool) {
        if (bool == 0)
            this.isfriend = false;
        else
            this.isfriend = true;
    }
    /**
     * @return the isfamily
     */
    public boolean isIsfamily() {
        return isfamily;
    }
    /**
     * @param isfamily the isfamily to set
     */
    public void setIsfamily(boolean isfamily) {
        this.isfamily = isfamily;
    }
    
    private void setIsFamily(int bool) {
        if (bool == 0)
            this.isfamily = false;
        else
            this.isfamily = true;
    }
}

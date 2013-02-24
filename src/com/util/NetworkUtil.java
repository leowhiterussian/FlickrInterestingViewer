package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.leo.flickrinterestingviewer.FlickrInterestingViewer;

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";
    private static final int NUM_RETRIES_EXCEPTION = 3;
    
    private static NetworkUtil NetworkUtilObject;
    private static FlickrInterestingViewer mApplication;
    
    private static HashMap<String, String> urls;
    private static SharedPreferences defaultSharedPreferences;

    private NetworkUtil(final FlickrInterestingViewer application) {
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        
        urls = new HashMap<String, String>();
    }

    public static synchronized NetworkUtil getSingletonObject(final FlickrInterestingViewer application) {
        if (NetworkUtilObject == null)
            NetworkUtilObject = new NetworkUtil(application);
        return NetworkUtilObject;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    private static String getUrl(String key) throws Exception {
        if (urls == null)
            throw new Exception();
        
        return urls.get(key);
    }
    
    private static String get(String url) {
        return get(url, 0);
    }
    
    private static String get(String currentURL, int attempt) {
        attempt++;
        if (attempt > NUM_RETRIES_EXCEPTION)
            return null;
        
        URL url;
        try {
            url = new URL(currentURL);
        } catch (MalformedURLException e1) {
            return null;
        }
        
        HttpURLConnection httpUrlConnection;
        try {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e1) {
            return null;
        }
        
        GlobalUtil.log("NetworkUtil", currentURL);
        
        try {
            InputStream in = httpUrlConnection.getInputStream();
            
            String result = convertStreamToString(in);
            
            return result;
        } catch (final IOException ioe) {
            return get(currentURL, attempt);
        } catch (final Exception e) {
            GlobalUtil.log("GET", e);
        } finally {
            try {
                httpUrlConnection.disconnect();
            } catch (Exception e) {
            }
        }
        return null;
    }
    
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }
    
    public static String executePost(String targetURL, HashMap<String, String> params) throws Exception {
        
        URL url;
        HttpURLConnection connection = null;  
        try {
            //Create connection
            url = new URL(targetURL);
            String urlParameters = makePostParams(params);
            
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");  

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            //Get Response    
            if (connection.getResponseCode() == 200) {  
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer(); 
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                return response.toString();
            } else {  
                 /* error from server */  
                InputStream error = connection.getErrorStream();  
                
                BufferedReader rd = new BufferedReader(new InputStreamReader(error));
                String line;
                StringBuffer errorResponse = new StringBuffer(); 
                while((line = rd.readLine()) != null) {
                    errorResponse.append(line);
                    errorResponse.append('\r');
                }
                rd.close();
                GlobalUtil.log("NetworkUtil", errorResponse.toString());
                return errorResponse.toString();
            }  
            

        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                if(connection != null)
                    connection.disconnect(); 
            } catch (Exception e) {
            }
        }
    }
    
    private static String makePostParams(HashMap<String, String> params) {
        String urlParameters = "";
        Iterator<Entry<String, String>> it = params.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            String key = pairs.getKey();
            String value = pairs.getValue();
            if (i != 0)
                urlParameters += "&";
            
            try {
                urlParameters += key + "=" + URLEncoder.encode(value, "UTF-8");
                i++;
            } catch (Exception e) {
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return urlParameters;
    }
    
    public static String getInterestingList(int page, int perPage) {
        String url = "http://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=afc29032a5f69a721578dd2f3e5c7e68&per_page=per_page_arg&page=pagearg&format=json&nojsoncallback=1";
        
        url = url.replace("pagearg", page + "");
        url = url.replace("per_page_arg", perPage + "");
        
        return get(url);
    }
}
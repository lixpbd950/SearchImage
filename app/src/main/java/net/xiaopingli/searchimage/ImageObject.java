package net.xiaopingli.searchimage;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiaopingli on 10/16/16.
 */

public class ImageObject implements Serializable {
    private String link;
    private String thumbnailLink;
    public static final String LINK = "link";
    public static final String THUMBNAIL_LINK = "thumbnailLink";
    public static final String IMAGE_KEY = "image";


    public ImageObject(JSONObject json) {
        try {
            link = json.getString(LINK);
            JSONObject imageJson = json.getJSONObject(IMAGE_KEY);
            thumbnailLink = imageJson.getString(THUMBNAIL_LINK);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageObject> fromJSONArray(JSONArray array) {
        ArrayList<ImageObject> results = new ArrayList<ImageObject>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageObject(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    public String getLink() {
        return link;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    @Override
    public String toString() {
        return "ImageObject{" +
                "fullUrl='" + link + '\'' +
                '}';
    }
}

package kz.sking.followers;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import static kz.sking.followers.HttpClient.getHTMLData;
import static kz.sking.followers.HttpClient.getHTMLImage;

public class FollowersBuilder {


    private static String getFollowersData(String name) {
        String url = "https://api.github.com/users/"+ name +"/followers";
        return getHTMLData(url);
    }

    private static Bitmap getFollowersImage() {
        String url = "http://www.stickpng.com/assets/images/5847f98fcef1014c0b5e48c0.png";
        return getHTMLImage(url);
    }

    private static followers dataParsing(String json) {
        followers followers = new followers();
        try {
            JSONArray _obj = new JSONArray(json);
            for(int i = 0;i < _obj.length();i++) {
                JSONObject _person = _obj.getJSONObject(i);
                followers.setLogName(_person.getString("login"),i);
                followers.setLogUrl(_person.getString("html_url"),i);
            }
        } catch (Exception ignore) {
        }
        return followers;
    }

    public static followers buildFollowers (String name) {
        followers followers = dataParsing(getFollowersData(name));
        followers.setIconData(getFollowersImage());
        return followers;
    }
}
package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.graphics.Bitmap;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchedItem {

    UserProfile userProfile;
    String tag;
    String description;

    public SearchedItem(UserProfile userProfile, String tag, String description){

        this.userProfile = userProfile;
        this.tag = tag;
        this.description = description;
    }

    public String getTag(){
        return tag;
    }

    public String getDescription(){
        return description;
    }

    public String getUsername(){
        return userProfile.getUsername();
    }

    public String getLocation(){
        return userProfile.getLocation();
    }

    public String getLanguage(){
        return userProfile.getLanguage();
    }

    public Bitmap getProfilePic(){ return userProfile.getProfilePic(); }
}

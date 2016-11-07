package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.graphics.Bitmap;

/**
 * Created by Yannick on 29.10.2016.
 */

public class UserProfile {

    private String email = "";
    private String location = "";
    private String language = "";
    private Bitmap profilePic;
    //private ArrayList<String[]> feedback;

    public String getUsername(){
        return email;
    }

    public String getLocation(){
        return location;
    }

    public String getLanguage(){
        return language;
    }

    public Bitmap getProfilePic(){ return profilePic; }

    public void setEmail(String email){
        this.email = email;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setProfilePic(Bitmap profilePic){ this.profilePic = profilePic; }

}


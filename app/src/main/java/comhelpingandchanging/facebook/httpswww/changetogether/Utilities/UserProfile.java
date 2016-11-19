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
    private double lat;
    private double lng;
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

    public double getLat(){ return lat;}

    public double getLng() { return lng;}

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

    public void setLat(double lat){ this.lat = lat;}

    public void setLng(double lng) { this.lng = lng;}
}


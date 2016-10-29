package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import java.util.ArrayList;

/**
 * Created by Yannick on 29.10.2016.
 */

public class UserProfile {

    private String username;
    private String location;
    private String language;
    //private ArrayList<String[]> feedback;

    public UserProfile(String username){

        this.username = username;
        this.location = "N/A";
        this.language = "N/A";
    }

    public UserProfile(String username, String location){

        this.username = username;
        this.location = location;
        this.language = "N/A";
    }

    public UserProfile(String username, String location, String language){

        this.username = username;
        this.location = location;
        this.language = language;
    }

    public String getUsername(){
        return username;
    }

    public String getLocation(){
        return location;
    }

    public String getLanguage(){
        return language;
    }
}

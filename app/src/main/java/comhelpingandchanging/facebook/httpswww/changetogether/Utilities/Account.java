package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;
import android.app.Activity;
import android.app.Application;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private boolean onlineStatus = false;
    private UserProfile self = null;
    private UserProfile searchedUserInfo = null;

    public void login(String email) {

        onlineStatus = true;
        self = new UserProfile(email);
    }

    public void login(String email, String location){

        onlineStatus = true;
        self = new UserProfile(email, location);
    }

    public void login(String email, String location, String language){

        onlineStatus = true;
        self = new UserProfile(email, location, language);
    }

    public void logout(){

        onlineStatus = false;
        self = null;
    }

    public void searchUser(UserProfile user){

        searchedUserInfo = user;
    }

    public boolean getOnlineStatus(){return onlineStatus;}

    public String getEmail(){
        return self.getUsername();
    }

    public String getLocation(){
        return self.getLocation();
    }

    public String getLanguage(){
        return self.getLanguage();
    }
}
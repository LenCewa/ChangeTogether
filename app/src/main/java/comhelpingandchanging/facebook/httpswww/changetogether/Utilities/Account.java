package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.LoginActivity;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private UserProfile self = null;
    //private UserProfile searchedUserInfo = null;

    public void login(Activity callingActivity, String email, String password) {

        Login login = new Login(callingActivity, email, password);
        login.execute();
    }

    public void logout(){

        self = null;
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("stayLoggedIn", false);
        editor.remove("onlineStatus");
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

    public void loadBids(Activity callingActivity){

        LoadBids l = new LoadBids(callingActivity, getEmail());
        l.execute();
    }

    public void searchBid(Activity callingActivity, String tag){

        SearchBid s = new SearchBid(callingActivity, tag);
        s.execute();
    }

    public void addBid(String tag, String description){

        AddBid a = new AddBid(getEmail(), tag, description);
        a.execute();
    }

    public void addUserInfo(Activity callingActivity, String password, String location, String language){

        setSelfInfo(getEmail(), password, location, language);
        AddUserInfo a = new AddUserInfo(callingActivity, password, location, language);
        a.execute();
    }

    public String getEmail(){
        return self.getUsername();
    }

    public String getPassword(){
        return self.getPassword();
    }

    public String getLocation(){
        return self.getLocation();
    }

    public String getLanguage(){
        return self.getLanguage();
    }

    public void setLocation(String location){
        self.setLocation(location);
    }

    public void setLanguage(String language){
        self.setLanguage(language);
    }

    public void setSelfInfo(String email, String password, String location, String language){
        self = new UserProfile(email, password, location, language);
    }
}
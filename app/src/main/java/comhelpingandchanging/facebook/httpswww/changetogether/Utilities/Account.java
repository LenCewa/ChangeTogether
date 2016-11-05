package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private UserProfile self = null;
    public FragmentManager fm;
    //private UserProfile searchedUserInfo = null;

    public void login(Activity callingActivity, String email, String password) {

        self = new UserProfile();
        Login login = new Login(callingActivity, email, password);
        login.execute();
    }

    public void logout(){

        self = null;
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("stayLoggedIn");
        editor.remove("email");
        editor.remove("password");
        editor.apply();
    }

    public void loadBids(Fragment callingFragment){

        LoadBids l = new LoadBids(callingFragment, getEmail());
        l.execute();
    }

    public void searchBid(Fragment callingFragment, String tag){

        SearchBid s = new SearchBid(callingFragment, tag);
        s.execute();
    }

    public void addBid(String tag, String description){

        AddBid a = new AddBid(getEmail(), tag, description);
        a.execute();
    }

    public void deleteBid(String tag, String description){

        DeleteBid d = new DeleteBid(getEmail(), tag, description);
        d.execute();
    }

    public void uploadProfilePic(Activity callingActivity, Bitmap pic){

        UploadImage u = new UploadImage(callingActivity, getEmail(), pic);
        u.execute();
    }

    public void showPic(Activity callingActivity){

        ShowPic s = new ShowPic(callingActivity, getEmail());
        s.execute();
    }

    public void editPassword(Activity callingActivity, String password){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "password", password);
        a.execute();
    }

    public void editLocation(Activity callingActivity, String location){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "location", location);
        a.execute();
    }

    public void editLanguage(Activity callingActivity, String language){

        AddInfo a = new AddInfo(callingActivity, getEmail(), "language", language);
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

    public Bitmap getProfilePic(){ return self.getProfilePic(); }

    public FragmentManager getFragmentManager(){
        return fm;
    }

    public void setEmail(String email){
        self.setEmail(email);
    }

    public void setPassword(String password){
        self.setPassword(password);
    }

    public void setLocation(String location){
        self.setLocation(location);
    }

    public void setLanguage(String language){
        self.setLanguage(language);
    }

    public void setProfilePic(Bitmap profilePic){
        self.setProfilePic(profilePic);
    }

    public void setFragmentManager(FragmentManager fm){
        this.fm = fm;
    }
}
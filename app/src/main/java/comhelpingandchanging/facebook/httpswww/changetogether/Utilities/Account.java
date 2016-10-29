package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;
import android.app.Activity;
import android.app.Application;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Account extends Application {

    private boolean onlineStatus = false;
    private String email = "";
    private String location = "";
    private String language = "";

    public void login(String email) {

        onlineStatus = true;
        this.email = email;
    }

    public void login(String email, String location){

        onlineStatus = true;
        this.email = email;
        this.location = location;
    }

    public void login(String email, String location, String language){

        onlineStatus = true;
        this.email = email;
        this.location = location;
        this.language = language;
    }

    public void logout(){

        onlineStatus = false;
        email = "";
        location = "";
        language = "";
    }

    public boolean getOnlineStatus(){return onlineStatus;}

    public String getEmail(){
        return email;
    }

    public String getLocation(){
        return location;
    }

    public String getLanguage(){
        return language;
    }
}
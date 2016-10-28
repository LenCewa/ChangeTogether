package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

/**
 * Created by Yannick on 28.10.2016.
 */

public class ConnectionManager {

    private static boolean onlineStatus = false;
    private static String email = "";
    private static String location = "";
    private static String language = "";

    public static String getEmail(){
        return email;
    };

    public static String getLocation(){
        return location;
    };

    public static String getLanguage(){
        return language;
    };

    public static void login(String mail){

        onlineStatus = true;
        ConnectionManager.email = email;
    }

    public static void login(String email, String location){

        onlineStatus = true;
        ConnectionManager.email = email;
        ConnectionManager.location = location;
    }

    public static void login(String email, String location, String language){

        onlineStatus = true;
        ConnectionManager.email = email;
        ConnectionManager.location = location;
        ConnectionManager.language = language;
    }

    public static void logout(){

        onlineStatus = false;
        ConnectionManager.email = "";
        ConnectionManager.location = "";
        ConnectionManager.language = "";
    }
}

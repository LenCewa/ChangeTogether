package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.OwnProfileFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 30.10.2016.
 */

public class AddUserInfo extends AsyncTask<Void, Void, String> {

    private Activity callingActivity;
    private String password;
    private String location;
    private String language;
    RequestHandler rh = new RequestHandler();
    Account account;


    public AddUserInfo(Activity callingActivity, String password, String location, String language){

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.password = password;
        this.location = location;
        this.language = language;
    }
    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("email", account.getEmail());
        data.put("password", password);
        data.put("location", location);
        data.put("language", language);
        String result = rh.sendPostRequest(Constants.DBADDUSERINFO,data);

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Success")) {
            ((OwnProfileFragment)account.getFragmentManager().findFragmentByTag("ownprofile")).setElements();
            callingActivity.finish();
        }
        if(s.equals("Error"))
            Toast.makeText(callingActivity, s, Toast.LENGTH_SHORT).show();
    }
}

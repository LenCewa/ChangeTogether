package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
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

/**
 * Created by Yannick on 26.10.2016.
 */

public class Register extends AsyncTask<Void, Void, String>{

    Account account;
    private Activity callingActivity;
    private String email;
    private String password;
    RequestHandler rh = new RequestHandler();

    public Register(Activity callingActivity, String email, String password) {

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.email = email;
        this.password = password;
    }


    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        data.put("password", password);
        String result = rh.sendPostRequest(Constants.DBREGISTER,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("User added, logging in...") || result.equals("User already exists, logging in instead...")) {
            account.login(callingActivity, email, password);
        }
        Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();

    }
}

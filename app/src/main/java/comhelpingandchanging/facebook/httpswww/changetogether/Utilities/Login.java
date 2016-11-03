package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
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
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;

/**
 * Created by Yannick on 29.10.2016.
 */

public class Login extends AsyncTask<Void, Void, String> {

    private Account account;
    private Activity callingActivity;
    private String email;
    private String password;
   RequestHandler rh = new RequestHandler();

    public Login(Activity callingActivity, String email, String password) {

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
        String result = rh.sendPostRequest(Constants.DBLOGIN,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Password incorrect") || result.equals("User doesnt exist, please register"))
            Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
        else {
            String[] results = result.split(Pattern.quote("|"));
            String location = results[0];
            String language = results[1];

            byte[] decodedString = Base64.decode(results[2], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            account.setSelfInfo(email, password, location, language, decodedByte);
            Intent search = new Intent(callingActivity, MainAppActivity.class);
            callingActivity.startActivity(search);
            callingActivity.finishAffinity();
        }
    }
}
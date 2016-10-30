package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.content.Intent;
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

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.OwnProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SettingsActivity;

/**
 * Created by Yannick on 30.10.2016.
 */

public class AddUserInfo extends AsyncTask<Void, Void, String> {

    private Activity callingActivity;
    private String password;
    private String location;
    private String language;
    private boolean connectionEstablished = false;
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
        StringBuilder sb = new StringBuilder();

        try {
            String link = Constants.DBADDUSERINFO + "?email=" + account.getEmail() + "&password=" + password + "&location=" + location + "&language=" + language;
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            connectionEstablished = true;

        } catch (MalformedURLException e) {
            Log.e("stacktrace", "MalformedURLException", e);
            connectionEstablished = false;
        } catch (IOException e) {
            Log.e("stacktrace", "IOException", e);
            connectionEstablished = false;
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Success")) {
            Intent OwnprofileActivity = new Intent(callingActivity, OwnProfileActivity.class);
            callingActivity.startActivity(OwnprofileActivity);
            callingActivity.finish();
        }
        if(s.equals("Error"))
            Toast.makeText(callingActivity, s, Toast.LENGTH_SHORT).show();;
    }
}

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
import java.util.Arrays;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.LoginActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchActivity;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Login extends AsyncTask<Void, Void, String>{

    private Activity callingActivity;
    private String email;
    private String password;
    private boolean connectionEstablished = true;

    public Login(Activity callingActivity, String email, String password) {

        this.callingActivity = callingActivity;
        this.email = email;
        this.password = password;
    }


    @Override
    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();

        try{
            String link = Constants.DBLOGIN + "?email=" + email + "&username=" + email + "&password=" + password;
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
            Log.e("stacktrace","MalformedURLException",e);
            connectionEstablished = false;
        }
        catch (IOException e){
            Log.e("stacktrace","IOException",e);
            connectionEstablished = false;
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if(connectionEstablished) {
            if(result.equals("Password incorrect") ||result.equals("User doesnt exist, please register"))
                Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else{
                String[] results = result.split(Pattern.quote("|"));
                String location = results[0];
                String language = results[1];

                ConnectionManager.login(email, location, language);
                Intent search = new Intent(callingActivity, SearchActivity.class);
                callingActivity.startActivity(search);
            }
        }
    }
}

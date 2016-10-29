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

/**
 * Created by Yannick on 26.10.2016.
 */

public class Register extends AsyncTask<Void, Void, String>{

    Account account;
    private Activity callingActivity;
    private String email;
    private String password;
    private boolean connectionEstablished = false;

    public Register(Activity callingActivity, String email, String password) {

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.email = email;
        this.password = password;
    }


    @Override
    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();

        try{
            String link = Constants.DBREGISTER + "?email=" + email + "&username=" + email + "&password=" + password;
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
            if(result.equals("User added, logging in...") || result.equals("User already exists, logging in instead...")) {
                account.login(callingActivity, email, password);
            }
            Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
        }
    }
}

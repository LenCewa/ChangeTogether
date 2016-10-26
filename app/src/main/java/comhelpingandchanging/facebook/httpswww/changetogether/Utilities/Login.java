package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

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
import java.net.URLEncoder;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainActivity;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Login extends AsyncTask{

    private String email, password;
    private boolean connectionEstablished = true;
    private boolean finished = false;

    public Login(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public boolean isFinished(){
        return finished;
    }

    public boolean isConnectionEstablished(){
        return connectionEstablished;
    }

    @Override
    protected Object doInBackground(Object... params) {
        try{
            String link = "http://192.168.178.54/app/login.php?email=" + email + "&username=" + email + "&password=" + password;
            Log.e("link", link);
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            InputStream in = conn.getInputStream();
            in.close();

        } catch (MalformedURLException e) {
            Log.e("stacktrace","MalformedURLException",e);
            connectionEstablished = false;
        }
        catch (IOException e){
            Log.e("stacktrace","IOException",e);
            connectionEstablished = false;
        }

        finished = connectionEstablished;
        return null;
    }
}

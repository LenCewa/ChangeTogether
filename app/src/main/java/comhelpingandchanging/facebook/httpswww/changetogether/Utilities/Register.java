package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Register extends AsyncTask{

    public String email;
    private String password;
    private boolean connectionEstablished = true;
    private boolean finished = false;

    public Register(String email, String password) {

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
            String link = Constants.DBLOGIN + "?email=" + email + "&username=" + email + "&password=" + password;
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

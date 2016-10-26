package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Yannick on 26.10.2016.
 */

public class Login {

    public Login(String email, String password) throws IOException, MalformedURLException{
        //String data = URLEncoder.encode("authkey", "UTF-8") + "=" + URLEncoder.encode(AUTHKEY, "UTF-8");
        String link = "http://192.168.178.54/app/login.php?email=" + email + "&username=" + email + "&password=" + password;
        Log.e("link", link);
        URL url = new URL(link);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
           sb.append(line);
        }
        Log.e("failure", sb.toString());
    }
}

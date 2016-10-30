package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Yannick on 30.10.2016.
 */

public class AddBid extends AsyncTask<Void, Void, String > {

    private String email;
    private String tag;
    private String description;
    private boolean connectionEstablished = false;

    public AddBid(String email, String tag, String description){

        this.email = email;
        this.tag = tag;
        this.description = description;
    }

    @Override
    protected String doInBackground(Void... params) {
        StringBuilder sb = new StringBuilder();

        try {
            String link = Constants.DBADDBID + "?email=" + email + "&tag=" + tag + "&description=" + description;
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
}

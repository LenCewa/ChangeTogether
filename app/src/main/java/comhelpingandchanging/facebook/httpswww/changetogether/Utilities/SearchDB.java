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
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchActivity;

/**
 * Created by Yannick on 29.10.2016.
 */

public class SearchDB extends AsyncTask<String, Void, String>{

    Activity callingActivity;
    private boolean connectionEstablished = true;

    public SearchDB(Activity callingActivity){

        this.callingActivity = callingActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuilder sb = new StringBuilder();

        try {
            String link = Constants.DBSEARCH + "?table=" + params[0] + "&where=" + params[1] + "6email=" + params[2];
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
    protected void onPostExecute(String result) {
        if (connectionEstablished) {
            if(result.equals("User not found"))
                Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else {
                Intent profile = new Intent(callingActivity, ProfileActivity.class);
                profile.putExtra("searchedUser", result);
                callingActivity.startActivity(profile);
            }
        }
    }
}

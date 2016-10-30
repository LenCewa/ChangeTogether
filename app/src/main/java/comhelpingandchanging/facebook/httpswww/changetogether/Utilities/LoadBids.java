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

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.BieteActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBids extends AsyncTask<Void, Void, String>{

    Activity callingActivity;
    private boolean connectionEstablished = true;
    private String email;

    public LoadBids(Activity callingActivity, String email){

        this.callingActivity = callingActivity;
        this.email = email;
    }

    @Override
    protected String doInBackground(Void... params) {

        StringBuilder sb = new StringBuilder();

        try {
            String link = Constants.DBLOADBID + "?email=" + email;
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
            if(result.equals("No entries"));
                //Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    ((BieteActivity)callingActivity).bieteItems.add(arr[1]);
                }
                ((BieteActivity)callingActivity).adapter.notifyDataSetChanged();
            }
        }
    }
}

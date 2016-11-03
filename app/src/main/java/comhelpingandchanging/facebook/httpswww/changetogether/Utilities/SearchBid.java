package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;

/**
 * Created by Yannick on 29.10.2016.
 */

public class SearchBid extends AsyncTask<Void, Void, String>{

    Fragment callingFragment;
    private boolean connectionEstablished = true;
    private String tag;

    public SearchBid(Fragment callingFragment, String tag){

        this.callingFragment = callingFragment;
        this.tag = tag;
    }

    @Override
    protected String doInBackground(Void... params) {

        StringBuilder sb = new StringBuilder();

        try {
            String link = Constants.DBSEARCHBID + "?tag=" + tag;
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
                //Toast.makeText(callingFragment, result, Toast.LENGTH_SHORT).show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if(!arr[0].equals(((Account) callingFragment.getActivity().getApplication()).getEmail())){
                        ((SearchFragment) callingFragment).listItems.add(0, new String[]{ arr[0], arr[1] });
                        ((SearchFragment) callingFragment).adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}

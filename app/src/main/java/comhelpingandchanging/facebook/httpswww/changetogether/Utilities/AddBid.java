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
import java.util.HashMap;

/**
 * Created by Yannick on 30.10.2016.
 */

public class AddBid extends AsyncTask<Void, Void, String > {

    private String email;
    private String tag;
    private String description;
    RequestHandler rh = new RequestHandler();

    public AddBid(String email, String tag, String description){

        this.email = email;
        this.tag = tag;
        this.description = description;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        data.put("tag", tag);
        data.put("description", description);
        String result = rh.sendPostRequest(Constants.DBADDBID,data);

        return result;
    }
}

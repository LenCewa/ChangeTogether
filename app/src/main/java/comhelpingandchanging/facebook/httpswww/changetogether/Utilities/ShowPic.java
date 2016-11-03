package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by Yannick on 03.11.2016.
 */

public class ShowPic extends AsyncTask<Void, Void, String>{

    Activity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String email;

    public ShowPic(Activity callingActivity, String email){

        this.callingActivity = callingActivity;
        this.email = email;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBUSHOWPIC,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(!result.equals("error")) {
            byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        else
            Toast.makeText(callingActivity, "Error retrieving picture", Toast.LENGTH_SHORT).show();
    }
}

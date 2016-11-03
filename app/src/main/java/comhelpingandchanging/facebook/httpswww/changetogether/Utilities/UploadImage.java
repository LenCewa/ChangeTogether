package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainActivity;

/**
 * Created by Yannick on 02.11.2016.
 */

public class UploadImage extends AsyncTask<Void,Void,String>{

    Activity callingActivity;
    ProgressDialog loading;
    Bitmap pic;
    String email;
    boolean connectionEstablished = false;
    RequestHandler rh = new RequestHandler();

    public UploadImage(Activity callingActivity, String email, Bitmap pic){

        this.callingActivity = callingActivity;
        this.email = email;
        this.pic = pic;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingActivity, "Uploading...", null,true,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        loading.dismiss();
    }

    @Override
    protected String doInBackground(Void... params) {
        String uploadImage = getStringImage(pic);

        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        data.put("pic", uploadImage);
        String result = rh.sendPostRequest(Constants.DBUPLOADPIC,data);

        return result;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
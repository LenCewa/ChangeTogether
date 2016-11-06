package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 02.11.2016.
 */

public class UploadImage extends AsyncTask<Void,Void,String>{

    Account account;
    Activity callingActivity;
    ProgressDialog loading;
    Bitmap pic;
    String email;
    RequestHandler rh = new RequestHandler();

    public UploadImage(Activity callingActivity, String email, Bitmap pic){

        account = (Account)callingActivity.getApplication();
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
        if(s.equals("connection error"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rh.retry();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
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
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
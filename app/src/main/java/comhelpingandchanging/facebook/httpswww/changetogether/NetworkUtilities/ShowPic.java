package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

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
        String result = rh.sendPostRequest(Constants.DBSHOWPIC,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("connection error"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rh.retry();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            if (!result.equals("error")) {
                byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } else
                Toast.makeText(callingActivity, "Error retrieving picture", Toast.LENGTH_SHORT).show();
        }
    }
}
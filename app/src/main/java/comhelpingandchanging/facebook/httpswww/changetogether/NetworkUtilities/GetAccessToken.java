package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class GetAccessToken extends AsyncTask<Void, Void, String> {

    ProgressDialog loading;
    private Activity callingActivity;
    private String emailAuth;
    private String sessionId;
    private String email;
    RequestHandler rh = new RequestHandler();

    public GetAccessToken(Activity callingActivity, String emailAuth, String sessionId, String email) {

        this.callingActivity = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingActivity, "Uploading...", null,true,true);
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("emailAuth", emailAuth);
        data.put("sessionId", sessionId);
        Log.e("sessionId", sessionId);
        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBGETACCESTOKEN,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        if(result.equals("connection error"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new GetAccessToken(callingActivity, emailAuth, sessionId, email).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray tokenArr = jsonObj.getJSONArray("token");
                JSONObject token = tokenArr.getJSONObject(0);

                String accessToken = token.getString("token");

                SharedPreferences sp = callingActivity.getSharedPreferences("login_state", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", email);
                editor.putString("accessToken", accessToken);
                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(callingActivity, "Couldnt get Access Token", Toast.LENGTH_LONG);
            }
        }
    }
}
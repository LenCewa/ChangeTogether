package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class Logout extends AsyncTask<Void, Void, String> {

    ProgressDialog loading;
    private Account account;
    private Activity callingActivity;
    private String emailAuth;
    private String sessionId;
    RequestHandler rh = new RequestHandler();

    public Logout(Activity callingActivity, String emailAuth, String sessionId) {

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
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
        data.put("email", emailAuth);
        String result = rh.sendPostRequest(Constants.DBLOGOUT,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        SharedPreferences sp = callingActivity.getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove("email");
        editor.remove("accessToken");
        editor.apply();
        setSelfInfo();

        Intent main = new Intent(callingActivity, MainActivity.class);
        callingActivity.startActivity(main);
        callingActivity.finishAffinity();
    }

    private void setSelfInfo(){

        account.setSessionId(null);
        account.deleteInfo();
    }
}
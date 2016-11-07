package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBidsActivity extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    ProfileActivity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String email;

    public LoadBidsActivity(ProfileActivity callingActivity, String emailAuth, String sessionId, String email){

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

        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBLOADBID,data);

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
                            new LoadBidsActivity(callingActivity, emailAuth, sessionId, email).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            if (result.equals("No entries"))
                Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if (!callingActivity.bieteItems.contains(arr[1]))
                        callingActivity.bieteItems.add(new String[]{arr[1], arr[2], arr[3]});
                }
                callingActivity.adapter.notifyDataSetChanged();
            }
        }
    }
}

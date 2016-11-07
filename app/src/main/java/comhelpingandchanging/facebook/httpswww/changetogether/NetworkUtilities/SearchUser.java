package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchItemActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.UserProfile;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchUser extends AsyncTask<Void, Void, String>{

    Account account;
    SearchItemActivity callingActivity;
    String emailAuth;
    String sessionId;
    String email;
    String tag;
    String description;
    ProgressDialog loading;
    RequestHandler rh = new RequestHandler();

    public SearchUser(SearchItemActivity callingActivity, String emailAuth, String sessionId, String email, String tag, String description){

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.tag = tag;
        this.description = description;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingActivity, "Loading profile...", null,true,true);
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("emailAuth", emailAuth);
        data.put("sessionId", sessionId);

        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBSEARCHUSER,data);

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
                            new SearchUser(callingActivity, emailAuth, sessionId, email, tag, description).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {

            String[] results = result.split(Pattern.quote("|"));
            String location = results[0];
            String language = results[1];

            byte[] decodedString = Base64.decode(results[2], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            account.setSearchedItem(getUserProfile(location, language, decodedByte), tag, description);
            callingActivity.setElements();
        }
    }

    private UserProfile getUserProfile(String location, String language, Bitmap bitmap){

        UserProfile u = new UserProfile();
        u.setEmail(email);
        u.setLocation(location);
        u.setLanguage(language);
        u.setProfilePic(bitmap);
        return u;
    }
}

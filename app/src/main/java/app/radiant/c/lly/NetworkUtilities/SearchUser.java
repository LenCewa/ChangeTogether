package app.radiant.c.lly.NetworkUtilities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.radiant.c.lly.Fragments.ProfileFragment;
import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchUser extends AsyncTask<Void, Void, String>{

    Account account;
    ProfileFragment callingFragment;
    String emailAuth;
    String sessionId;
    String email;
    ProgressDialog loading;
    RequestHandler rh = new RequestHandler();

    public SearchUser(ProfileFragment callingFragment, String emailAuth, String sessionId, String email){

        account = (Account) callingFragment.getActivity().getApplication();
        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingFragment.getActivity(), "Loading profile...", null,true,true);
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
            Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SearchUser(callingFragment, emailAuth, sessionId, email).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray user = jsonObj.getJSONArray("user");
                JSONObject userInfo = user.getJSONObject(0);

                String location = userInfo.getString("location");
                String language = userInfo.getString("language");

                account.setSearchedUser(location, language);
                callingFragment.setElements();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(callingFragment.getActivity(), "Couldnt get User Info", Toast.LENGTH_LONG);
            }
        }
    }
}

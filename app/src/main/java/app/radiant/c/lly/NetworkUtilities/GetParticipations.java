package app.radiant.c.lly.NetworkUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class GetParticipations extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    Account account;
    Activity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String email;
    private double lat;
    private double lng;

    public GetParticipations(Activity callingActivity, String emailAuth, String sessionId, String email, double lat, double lng){

        this.account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
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
        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBGETPARTICIPATIONS,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        if(result.equals("connection error"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new GetParticipations(callingActivity, emailAuth, sessionId, email, lat, lng).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            if (result.equals("No entries"))
                Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else {
                try {
                    ArrayList<String[]> l = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray bids = jsonObj.getJSONArray("bids");

                    for (int i = 0; i < bids.length(); i++) {
                        JSONObject bidsInfo = bids.getJSONObject(i);

                        String id = bidsInfo.getString("id");
                        String email = bidsInfo.getString("email");
                        String tag = bidsInfo.getString("tag");
                        String description = bidsInfo.getString("description");
                        String location = bidsInfo.getString("location");
                        String avgRating = bidsInfo.getString("averageRating");
                        String count = bidsInfo.getString("count");
                        String distance = String.valueOf(Math.round(bidsInfo.getDouble("distance")));
                        String date = bidsInfo.getString("date");
                        String time = bidsInfo.getString("time");
                        String part = bidsInfo.getString("part");
                        String maxPart = bidsInfo.getString("maxPart");
                        String encodedPic = bidsInfo.getString("profilePic");

                        String[] arr = new String[]{id, email, tag, description, location, avgRating, count, distance, date, time, part, maxPart, encodedPic};
                        l.add(arr);
                    }
                    account.setParticipations(l);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(callingActivity, "Couldnt load Participations", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

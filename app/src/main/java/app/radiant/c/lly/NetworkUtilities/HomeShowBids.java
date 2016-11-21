package app.radiant.c.lly.NetworkUtilities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;

import app.radiant.c.lly.Fragments.HomeFragment;
import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class HomeShowBids extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    Account account;
    HomeFragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private double lat;
    private double lng;
    private String lastId;

    public HomeShowBids(HomeFragment callingFragment, String emailAuth, String sessionId, double lat, double lng, String lastId){

        account = (Account) callingFragment.getActivity().getApplication();
        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.lat = lat;
        this.lng = lng;
        this.lastId = lastId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingFragment.getActivity(), "Uploading...", null,true,true);
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("emailAuth", emailAuth);
        data.put("sessionId", sessionId);

        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        data.put("lastId", lastId);
        String result = rh.sendPostRequest(Constants.DBHOMESHOWBIDS,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("home", result);
        loading.dismiss();
        if(result.equals("connection error"))
            Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new HomeShowBids(callingFragment, emailAuth, sessionId, lat, lng, lastId).execute();
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
        else {
            if (result.equals("No entries") || result.equals(""))
                Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                        .show();
            else {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray bids = jsonObj.getJSONArray("bids");
                    for (int i = 0; i < bids.length(); i++) {
                        JSONObject bidsInfo = bids.getJSONObject(i);

                        String id = bidsInfo.getString("id");
                        Constants.lastIdHome = id;
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
                        if (!email.equals(account.getEmail())) {
                            if(Integer.parseInt(distance) <= 75)
                                callingFragment.listItems.add(arr);
                        }
                    }
                    Collections.sort(callingFragment.listItems, callingFragment.cmp);
                    callingFragment.adapter.notifyDataSetChanged();

                    if (callingFragment.listItems.isEmpty())
                        Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                                .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(callingFragment.getActivity(), "Couldnt load bids", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

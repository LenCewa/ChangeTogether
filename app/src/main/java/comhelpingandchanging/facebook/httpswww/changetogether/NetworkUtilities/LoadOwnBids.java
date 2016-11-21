package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.OwnBidsFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadOwnBids extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    Account account;
    OwnBidsFragment ownBidsFragment;
    BieteFragment bieteFragment;
    Activity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String email;
    private double lat;
    private double lng;
    private String lastId;


    public LoadOwnBids(OwnBidsFragment callingActivity, String emailAuth, String sessionId, String email, double lat, double lng, String lastId){

        account = (Account)callingActivity.getActivity().getApplication();
        this.callingActivity = callingActivity.getActivity();
        this.ownBidsFragment = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.lastId = lastId;
    }

    public LoadOwnBids(BieteFragment callingActivity, String emailAuth, String sessionId, String email, double lat, double lng, String lastId){

        account = (Account)callingActivity.getActivity().getApplication();
        this.callingActivity = callingActivity.getActivity();
        this.bieteFragment = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.lastId = lastId;
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
        data.put("lastId", lastId);
        String result = rh.sendPostRequest(Constants.DBLOADOWNBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("ownbids", result);
        loading.dismiss();
        if(result.equals("connection error"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new LoadOwnBids(ownBidsFragment, emailAuth, sessionId, email, lat, lng, lastId).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            if (result.equals("No entries"))
                Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
            else {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray bids = jsonObj.getJSONArray("bids");
                    ArrayList<String[]> l = new ArrayList<>();
                    for (int i = 0; i < bids.length(); i++) {
                        JSONObject bidsInfo = bids.getJSONObject(i);

                        String id = bidsInfo.getString("id");
                        Constants.lastIdOwnBids = id;
                        String email = bidsInfo.getString("email");
                        String tag = bidsInfo.getString("tag");
                        String description = bidsInfo.getString("description");
                        String location = bidsInfo.getString("location");
                        String avgRating = bidsInfo.getString("averageRating");
                        String count = bidsInfo.getString("count");
                        String date = bidsInfo.getString("date");
                        String time = bidsInfo.getString("time");
                        String part = bidsInfo.getString("part");
                        String maxPart = bidsInfo.getString("maxPart");

                        String[] arr = new String[]{id, email, tag, description, location, avgRating, count, date, time, part, maxPart};
                        if(!account.getOwnBids().contains(arr))
                            account.getOwnBids().add(arr);
                    }
                    if(bieteFragment == null)
                        ownBidsFragment.adapter.notifyDataSetChanged();
                    else
                        bieteFragment.adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(callingActivity, "Couldnt load bids", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

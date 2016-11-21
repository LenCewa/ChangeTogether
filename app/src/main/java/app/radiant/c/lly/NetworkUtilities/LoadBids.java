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

import java.util.Collections;
import java.util.HashMap;

import app.radiant.c.lly.Fragments.SuperProfileFragment;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBids extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    SuperProfileFragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String email;
    private double lat;
    private double lng;

    public LoadBids(SuperProfileFragment callingActivity, String emailAuth, String sessionId, String email, double lat, double lng){

        this.callingFragment = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
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

        data.put("email", email);
        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBLOADBID,data);

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
                            new LoadBids(callingFragment, emailAuth, sessionId, email, lat, lng).execute();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            if (result.equals("No entries"))
                Toast.makeText(callingFragment.getActivity(), result, Toast.LENGTH_SHORT).show();
            else {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray bids = jsonObj.getJSONArray("bids");
                    callingFragment.bieteItems.clear();
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
                        //String encodedPic = bidsInfo.getString("profilePic");

                        String[] arr = new String[]{id, email, tag, description, location, avgRating, count, distance, date, time, part, maxPart};
                        if (!callingFragment.bieteItems.contains(arr))
                            callingFragment.bieteItems.add(arr);
                    }
                    if(callingFragment.cmp != null)
                        Collections.sort(callingFragment.bieteItems, callingFragment.cmp);
                    callingFragment.adapter.notifyDataSetChanged();

                    if (callingFragment.bieteItems.isEmpty())
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

package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

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

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SearchFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class SearchBid extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    Account account;
    Activity callingActivity;
    SearchFragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String tag;
    private double lat;
    private double lng;

    public SearchBid(SearchFragment callingFragment, String emailAuth, String sessionId, String tag, double lat, double lng){

        account = (Account) callingFragment.getActivity().getApplication();
        callingActivity = callingFragment.getActivity();
        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.tag = tag;
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

        data.put("tag", tag);
        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBSEARCHBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        if(result.equals("connection error"))Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SearchBid(callingFragment, emailAuth, sessionId, tag, lat, lng).execute();
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
        else {
            if (result.equals("No entries"))
                Snackbar.make(callingActivity.findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                        .show();
            else {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray bids = jsonObj.getJSONArray("bids");
                    callingFragment.listItems.clear();
                    for (int i = 0; i < bids.length(); i++) {
                        JSONObject bidsInfo = bids.getJSONObject(i);

                        String id = bidsInfo.getString("id");
                        String email = bidsInfo.getString("email");
                        String tag = bidsInfo.getString("tag");
                        String description = bidsInfo.getString("description");
                        String location = bidsInfo.getString("description");

                        String[] arr = new String[]{id, email, tag, description, location};
                        if (!email.equals(account.getEmail())) {
                            callingFragment.listItems.add(arr);
                        }
                    }
                    callingFragment.adapter.notifyDataSetChanged();

                    if (callingFragment.listItems.isEmpty())
                        Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                                .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(callingFragment.getActivity(), "Couldnt find bids", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

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

import java.util.HashMap;

import app.radiant.c.lly.Activities.ShowBidFeedbackActivity;
import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchFeedback extends AsyncTask <Void, Void, String>{

    ProgressDialog loading;
    Account account;
    ShowBidFeedbackActivity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String tag;
    private int id;

    public SearchFeedback(ShowBidFeedbackActivity callingActivity, String emailAuth, String sessionId, int id, String tag){

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.tag = tag;
        this.id = id;
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

        data.put("id", String.valueOf(id));
        data.put("tag", tag);
        String result = rh.sendPostRequest(Constants.DBSEARCHFEEDBACK,data);

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
                            new SearchFeedback(callingActivity, emailAuth, sessionId, id, tag).execute();
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
                    JSONArray feedback = jsonObj.getJSONArray("feedback");
                    double averageRating = feedback.getJSONObject(0).getDouble("averageRating");
                    callingActivity.setStars(averageRating);

                    JSONArray allFeedback = feedback.getJSONObject(0).getJSONArray("allFeedback");
                    callingActivity.feedbacks.clear();
                    for (int i = 0; i < allFeedback.length(); i++) {
                        JSONObject bidsInfo = allFeedback.getJSONObject(i);

                        String fromUser = bidsInfo.getString("fromUser");
                        String text = bidsInfo.getString("text");
                        String rating = bidsInfo.getString("rating");

                        String[] arr = new String[]{fromUser, text, rating};
                        callingActivity.feedbacks.add(arr);
                    }
                    callingActivity.adapter.notifyDataSetChanged();

                    if (callingActivity.feedbacks.isEmpty())
                        Snackbar.make(callingActivity.findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                                .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("test", "result " + result);
                    Toast.makeText(callingActivity, "Couldnt load feedback", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

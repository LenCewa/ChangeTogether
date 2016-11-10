package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

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

import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBids extends AsyncTask<Void, Void, String>{

    ProgressDialog loading;
    BieteFragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String emailAuth;
    private String sessionId;
    private String email;

    public LoadBids(BieteFragment callingFragment, String emailAuth, String sessionId, String email){

        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
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
                            new LoadBids(callingFragment, emailAuth, sessionId, email).execute();
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

                        String tag = bidsInfo.getString("tag");

                        if (!callingFragment.bieteItems.contains(tag))
                            callingFragment.bieteItems.add(tag);
                    }
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

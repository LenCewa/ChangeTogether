package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.BieteFragment;
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
            Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
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
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if (!callingFragment.bieteItems.contains(arr[1]))
                        callingFragment.bieteItems.add(arr[1]);
                }
                callingFragment.adapter.notifyDataSetChanged();
            }
        }
    }
}

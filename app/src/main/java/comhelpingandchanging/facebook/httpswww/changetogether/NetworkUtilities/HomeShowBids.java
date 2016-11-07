package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.HomeFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

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

    public HomeShowBids(HomeFragment callingFragment, String emailAuth, String sessionId, double lat, double lng){

        account = (Account) callingFragment.getActivity().getApplication();
        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
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

        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBHOMESHOWBIDS,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        if(result.equals("connection error"))Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new HomeShowBids(callingFragment, emailAuth, sessionId, lat, lng).execute();
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
        else {
            if (result.equals("No entries") || result.equals(""))
                Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                        .show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if (!arr[0].equals(account.getEmail())) {
                        callingFragment.listItems.add(0, new String[]{arr[0], arr[1], arr[2], arr[3], arr[4]});
                        callingFragment.adapter.notifyDataSetChanged();
                    }
                    if (callingFragment.listItems.isEmpty())
                        Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                                .show();
                }
            }
        }
    }
}

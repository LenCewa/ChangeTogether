package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;

/**
 * Created by Yannick on 29.10.2016.
 */

public class SearchBid extends AsyncTask<Void, Void, String>{

    Account account;
    Activity callingActivity;
    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String tag;
    private double lat;
    private double lng;

    public SearchBid(Fragment callingFragment, String tag, double lat, double lng){

        account = (Account) callingFragment.getActivity().getApplication();
        callingActivity = callingFragment.getActivity();
        this.callingFragment = callingFragment;
        this.tag = tag;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("tag", tag);
        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBSEARCHBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("No entries"))
            Snackbar.make(callingActivity.findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                    .show();
        else {
            String[] s = result.split(Pattern.quote(":"));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < s.length; i++) {
                String[] arr = s[i].split(Pattern.quote("|"));
                if(!arr[0].equals(account.getEmail())) {
                    ((SearchFragment) callingFragment).listItems.add(0, new String[]{arr[0], arr[1], arr[2], arr[3]});
                    ((SearchFragment) callingFragment).adapter.notifyDataSetChanged();
                }
                if(((SearchFragment) callingFragment).listItems.isEmpty())
                    Snackbar.make(callingActivity.findViewById(android.R.id.content), "No entries", Snackbar.LENGTH_SHORT)
                            .show();
            }
        }
    }
}

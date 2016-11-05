package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchFeedback extends AsyncTask <Void, Void, String>{

    Account account;
    Activity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String tag;
    private String email;

    public SearchFeedback(Activity callingActivity, String tag, String email){

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.tag = tag;
        this.email = email;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("tag", tag);
        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBSEARCHFEEDBACK,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("No entries"))
            Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
        else {
            String[] s = result.split(Pattern.quote(":"));
            ((ShowBidFeedback) callingActivity).setStars(Float.parseFloat(s[0]));
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < s.length; i++) {
                String[] arr = s[i].split(Pattern.quote("|"));
                ((ShowBidFeedback) callingActivity).feedbacks.add(0, new String[]{arr[0], arr[1], arr[2]});
                ((ShowBidFeedback) callingActivity).adapter.notifyDataSetChanged();
            }
        }
    }
}

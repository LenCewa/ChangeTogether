package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBidsActivity extends AsyncTask<Void, Void, String>{

    Activity callingActivity;
    RequestHandler rh = new RequestHandler();
    private String email;

    public LoadBidsActivity(Activity callingActivity, String email){

        this.callingActivity = callingActivity;
        this.email = email;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBLOADBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("No entries"));
            //Toast.makeText(callingFragment, result, Toast.LENGTH_SHORT).show();
        else {
            String[] s = result.split(Pattern.quote(":"));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < s.length; i++) {
                String[] arr = s[i].split(Pattern.quote("|"));
                if(!((ProfileActivity)callingActivity).bieteItems.contains(arr[1]))
                    ((ProfileActivity)callingActivity).bieteItems.add(new String[]{arr[1], arr[2]});
            }
            ((ProfileActivity)callingActivity).adapter.notifyDataSetChanged();
        }
    }
}

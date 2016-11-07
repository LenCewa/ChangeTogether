package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Fragment;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by len13 on 05.11.2016.
 */

public class LoadHelpingLocationsActivity extends AsyncTask<Void, Void, String> {

    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String email;

    public LoadHelpingLocationsActivity(Fragment callingActivity, String email){

        this.callingFragment = callingActivity;
        this.email = email;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        String result = rh.sendPostRequest(Constants.DBLOADHELPINGLOCATION,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("connection error"));
        else {
            if (result.equals("No entries")) ;
                //Toast.makeText(callingFragment., result, Toast.LENGTH_SHORT).show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if (!((ProfileFragment) callingFragment).helpingLocations.contains(arr[1]))
                        ((ProfileFragment) callingFragment).helpingLocations.add(new String[]{arr[1], arr[2]});
                }
                ((ProfileFragment) callingFragment).adapter.notifyDataSetChanged();
            }
        }
    }
}

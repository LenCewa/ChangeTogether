package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Fragment;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.HelpingLocationsFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by len13 on 04.11.2016.
 */

public class LoadHelpingLocations extends AsyncTask<Void, Void, String> {

    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String email;

    public LoadHelpingLocations(Fragment callingFragment, String email) {
        this.callingFragment = callingFragment;
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
            if (result.equals("No entries"))
                Toast.makeText(callingFragment.getActivity(), result, Toast.LENGTH_SHORT).show();
            else {
                String[] s = result.split(Pattern.quote(":"));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length; i++) {
                    String[] arr = s[i].split(Pattern.quote("|"));
                    if (!((HelpingLocationsFragment) callingFragment).usersHelpingLocations.contains(arr[1]))
                        ((HelpingLocationsFragment) callingFragment).usersHelpingLocations.add(arr[1]);
                }
                ((HelpingLocationsFragment) callingFragment).adapter.notifyDataSetChanged();
            }
        }
    }
}

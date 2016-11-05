package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;

/**
 * Created by len13 on 05.11.2016.
 */

public class SearchHelpingLocation extends AsyncTask<Void, Void, String> {

    Account account;
    Activity callingActivity;
    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String tag;

    public SearchHelpingLocation(Fragment callingFragment, String tag){

        account = (Account) callingFragment.getActivity().getApplication();
        callingActivity = callingFragment.getActivity();
        this.callingFragment = callingFragment;
        this.tag = tag;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("tag", tag);
        String result = rh.sendPostRequest(Constants.DBSEARCHHELPINGLOCATION,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("No entries"))
            Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT).show();
        else {
            String[] s = result.split(Pattern.quote(":"));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < s.length; i++) {
                String[] arr = s[i].split(Pattern.quote("|"));
                if(!arr[0].equals(account.getEmail())) {
                    ((SearchFragment) callingFragment).listItems.add(0, new String[]{arr[0], arr[1], arr[2]});
                    ((SearchFragment) callingFragment).adapter.notifyDataSetChanged();
                }
            }
        }
    }
}

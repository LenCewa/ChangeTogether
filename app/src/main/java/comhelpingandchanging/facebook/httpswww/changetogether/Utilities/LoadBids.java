package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Fragment;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.BieteFragment;

/**
 * Created by Yannick on 29.10.2016.
 */

public class LoadBids extends AsyncTask<Void, Void, String>{

    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String email;

    public LoadBids(Fragment callingFragment, String email){

        this.callingFragment = callingFragment;
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
                if(!((BieteFragment)callingFragment).bieteItems.contains(arr[1]))
                    ((BieteFragment)callingFragment).bieteItems.add(arr[1]);
            }
            ((BieteFragment)callingFragment).adapter.notifyDataSetChanged();
        }
    }
}

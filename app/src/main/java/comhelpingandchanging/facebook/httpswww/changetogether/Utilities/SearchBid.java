package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Fragment;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SearchFragment;

/**
 * Created by Yannick on 29.10.2016.
 */

public class SearchBid extends AsyncTask<Void, Void, String>{

    Fragment callingFragment;
    RequestHandler rh = new RequestHandler();
    private String tag;

    public SearchBid(Fragment callingFragment, String tag){

        this.callingFragment = callingFragment;
        this.tag = tag;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("tag", tag);
        String result = rh.sendPostRequest(Constants.DBSEARCHBID,data);

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
                if(!arr[0].equals(((Account) callingFragment.getActivity().getApplication()).getEmail())){
                    ((SearchFragment) callingFragment).listItems.add(0, new String[]{ arr[0], arr[1] });
                    ((SearchFragment) callingFragment).adapter.notifyDataSetChanged();
                }
            }
        }
    }
}

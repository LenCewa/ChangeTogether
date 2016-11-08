package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Fragment;
import android.os.AsyncTask;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.BieteFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by len13 on 05.11.2016.
 */

public class AddHelpingLocation extends AsyncTask<Void, Void, String > {

    private Fragment callingFragment;
    private String email;
    private String tag;
    private String description;
    RequestHandler rh = new RequestHandler();

    public AddHelpingLocation(Fragment callingFragment, String email, String tag, String description){

        this.callingFragment = callingFragment;
        this.email = email;
        this.tag = tag;
        this.description = description;
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        data.put("tag", tag);
        data.put("description", description);
        String result = rh.sendPostRequest(Constants.DBADDHELPINGLOCATION,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("connection error"));
        else
        ((Account)callingFragment.getActivity().getApplication()).loadBids((BieteFragment)callingFragment);
    }
}

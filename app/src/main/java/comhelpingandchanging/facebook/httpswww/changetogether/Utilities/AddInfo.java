package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.OwnProfileFragment;

/**
 * Created by Yannick on 05.11.2016.
 */

public class AddInfo extends AsyncTask<Void, Void, String > {

    Account account;
    Activity callingActivity;
    String email;
    String key;
    String value;
    RequestHandler rh = new RequestHandler();

    public AddInfo(Activity callingActivity, String email, String key, String value){

        account = (Account) callingActivity.getApplication();
        this.callingActivity = callingActivity;
        this.email = email;
        this.key = key;
        this.value = value;
    }
    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("email", email);
        data.put(key, value);
        String link = "";
        switch (key){
            case("password"):
                link = Constants.DBEDITPASSWORD;
                break;
            case("location"):
                link = Constants.DBEDITLOCATION;
                break;
            case("language"):
                link = Constants.DBEDITLANGUAGE;
                break;
        }
        String result = rh.sendPostRequest(link,data);

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("Success")) {
            ((OwnProfileFragment)account.getFragmentManager().findFragmentByTag("ownprofile")).setElements();
            callingActivity.finish();
        }
        if(s.equals("Error")) {
            Toast.makeText(callingActivity, s, Toast.LENGTH_SHORT).show();
            callingActivity.finish();
        }
    }
}

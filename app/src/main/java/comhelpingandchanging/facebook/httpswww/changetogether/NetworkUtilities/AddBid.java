package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 30.10.2016.
 */

public class AddBid extends AsyncTask<Void, Void, String > {

    ProgressDialog loading;
    private Fragment callingFragment;
    private String emailAuth;
    private String sessionId;
    private String email;
    private String tag;
    private String description;
    private String location;
    double lat;
    double lng;
    RequestHandler rh = new RequestHandler();

    public AddBid(Fragment callingFragment, String emailAuth, String sessionId, String email, String tag, String description, String location, double lat, double lng){

        this.callingFragment = callingFragment;
        this.emailAuth = emailAuth;
        this.sessionId = sessionId;
        this.email = email;
        this.tag = tag;
        this.description = description;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(callingFragment.getActivity(), "Uploading...", null,true,true);
    }

    @Override
    protected String doInBackground(Void... params) {
        HashMap<String,String> data = new HashMap<>();

        data.put("emailAuth", emailAuth);
        data.put("sessionId", sessionId);

        data.put("email", email);
        data.put("tag", tag);
        data.put("description", description);
        data.put("location", location);
        data.put("latitude", String.valueOf(lat));
        data.put("longitude", String.valueOf(lng));
        String result = rh.sendPostRequest(Constants.DBADDBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loading.dismiss();
        if(result.equals("connection error"))
            Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AddBid(callingFragment, emailAuth, sessionId, email, tag, description, location, lat, lng).execute();
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }
}

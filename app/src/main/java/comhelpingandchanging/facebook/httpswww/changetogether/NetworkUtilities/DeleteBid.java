package comhelpingandchanging.facebook.httpswww.changetogether.NetworkUtilities;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;

/**
 * Created by Yannick on 02.11.2016.
 */

public class DeleteBid extends AsyncTask<Void, Void, String > {

    Fragment callingFragment;
    private String email;
    private String tag;
    private String description;
    RequestHandler rh = new RequestHandler();

    public DeleteBid(Fragment callingFragment, String email, String tag, String description){

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
        String result = rh.sendPostRequest(Constants.DBDELETEBID,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("connection error"))
            Snackbar.make(callingFragment.getActivity().findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rh.retry();
                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
    }
}

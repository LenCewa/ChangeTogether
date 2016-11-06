package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;

/**
 * Created by Yannick on 05.11.2016.
 */

public class AddFeedback extends AsyncTask <Void, Void, String>{

    Account account;
    DialogFragment callingDialog;
    RequestHandler rh = new RequestHandler();
    private String tag;
    private String toUser;
    private String fromUser;
    private String text;
    private float rating;

    public AddFeedback(DialogFragment callingDialog, String tag, String toUser, String fromUser, String text, float rating){

        account = (Account) callingDialog.getActivity().getApplication();
        this.callingDialog = callingDialog;
        this.tag = tag;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.text = text;
        this.rating = rating;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("tag", tag);
        data.put("toUser", toUser);
        data.put("fromUser", fromUser);
        data.put("text", text);
        data.put("rating", String.valueOf(rating));
        String result = rh.sendPostRequest(Constants.DBADDFEEDBACK,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Activity callingActivity = callingDialog.getActivity();
        callingDialog.dismiss();
        if(result.equals("connection error")) Snackbar.make(callingActivity.findViewById(android.R.id.content), "Connection error", Snackbar.LENGTH_INDEFINITE)
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

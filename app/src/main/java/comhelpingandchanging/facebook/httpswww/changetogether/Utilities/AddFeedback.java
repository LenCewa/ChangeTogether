package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ProfileActivity;

/**
 * Created by Yannick on 05.11.2016.
 */

public class AddFeedback extends AsyncTask <Void, Void, String>{

    Account account;
    DialogFragment callingDialog;
    RequestHandler rh = new RequestHandler();
    private String toUser;
    private String fromUser;
    private String text;
    private float rating;

    public AddFeedback(DialogFragment callingDialog, String toUser, String fromUser, String text, float rating){

        account = (Account) callingDialog.getActivity().getApplication();
        this.callingDialog = callingDialog;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.text = text;
        this.rating = rating;
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String,String> data = new HashMap<>();

        data.put("toUser", toUser);
        data.put("fromUser", fromUser);
        data.put("text", text);
        data.put("rating", String.valueOf(rating));
        String result = rh.sendPostRequest(Constants.DBADDFEEDBACK,data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        callingDialog.dismiss();
        ((ProfileActivity)callingDialog.getActivity()).listItems.clear();
        account.searchFeedback(callingDialog.getActivity());
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Utilities;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by Yannick on 02.11.2016.
 */

public class DeleteBid extends AsyncTask<Void, Void, String > {

    private String email;
    private String tag;
    private String description;
    RequestHandler rh = new RequestHandler();

    public DeleteBid(String email, String tag, String description){

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
}

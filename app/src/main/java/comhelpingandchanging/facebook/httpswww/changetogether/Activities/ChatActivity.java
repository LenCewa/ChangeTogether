package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by len13 on 19.11.2016.
 */

public class ChatActivity extends Activity {

    Button send;
    EditText msgField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}

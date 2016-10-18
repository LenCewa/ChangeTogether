package comhelpingandchanging.facebook.httpswww.changetogether;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by len13 on 18.10.2016.
 */

public class FeedbackActivity extends Activity {

    String feedbackText;
    String profileName;
    TextView feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback = (TextView) findViewById(R.id.textView5);

        profileName = getIntent().getStringExtra("profileName");
        feedbackText = getIntent().getStringExtra("feedbackText");

        feedback.setText(profileName + "\n" + "\n" + feedbackText);
    }
}

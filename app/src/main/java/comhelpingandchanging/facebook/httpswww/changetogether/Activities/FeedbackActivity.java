package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 18.10.2016.
 */

public class FeedbackActivity extends Activity {

    RatingBar ratingBar;
    TextView feedback;
    TextView profileNameView;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        account = (Account) getApplication();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar3);
        profileNameView = (TextView) findViewById(R.id.profileNameFeedback);
        feedback = (TextView) findViewById(R.id.textView5);

        Float rating = getIntent().getFloatExtra("rating", 0);
        String profileName = getIntent().getStringExtra("profileName");
        String feedbackText = getIntent().getStringExtra("feedbackText");

        ratingBar.setRating(rating);
        profileNameView.setText(profileName);
        feedback.setText(feedbackText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("onlineStatus", account.getOnlineStatus());
        editor.putString("email", account.getEmail());
        editor.putString("password", account.getPassword());
        editor.commit();
    }
}

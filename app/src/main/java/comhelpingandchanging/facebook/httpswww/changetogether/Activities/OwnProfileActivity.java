package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Ludwig on 29.10.2016.
 */

public class OwnProfileActivity extends Activity {
    Button settings;
    Account account;
    TextView username;
    TextView location;
    TextView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownprofile);
        account = (Account) getApplication();
        username = (TextView) findViewById(R.id.ownUsername);
        location = (TextView) findViewById(R.id.ownLoaction);
        language = (TextView) findViewById(R.id.ownLanguage);

        username.setText(account.getEmail());
        location.setText(account.getLocation());
        language.setText(account.getLanguage());

        settings = (Button) findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SettingsActivity = new Intent(OwnProfileActivity.this, SettingsActivity.class);
                startActivity(SettingsActivity);
                finish();
            }
        });
    }
}

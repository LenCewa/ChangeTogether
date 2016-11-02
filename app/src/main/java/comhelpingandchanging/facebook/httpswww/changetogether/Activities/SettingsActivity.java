package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Ludwig on 29.10.2016.
 */

public class SettingsActivity extends Activity {
    Button save;
    Account account;
    EditText location;
    EditText language;
    EditText password;
    EditText passwordConfirm;
    ImageView profilePic;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        account = (Account) getApplication();
        save = (Button) findViewById(R.id.Save);
        location = (EditText) findViewById(R.id.changeLocation);
        language = (EditText) findViewById(R.id.changeLanguage);
        password = (EditText) findViewById(R.id.changePassword);
        passwordConfirm = (EditText) findViewById(R.id.ConfirmPassword);
        profilePic = (ImageView) findViewById(R.id.changeProfilePic);

        location.setText(account.getLocation());
        language.setText(account.getLanguage());

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().length() == 0 && passwordConfirm.getText().toString().length() == 0)
                    account.addUserInfo(SettingsActivity.this, account.getPassword(), location.getText().toString(), language.getText().toString());

                else {
                    if (password.getText().toString().equals(passwordConfirm.getText().toString()))
                        account.addUserInfo(SettingsActivity.this, password.getText().toString(), location.getText().toString(), language.getText().toString());
                    else
                        Toast.makeText(SettingsActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

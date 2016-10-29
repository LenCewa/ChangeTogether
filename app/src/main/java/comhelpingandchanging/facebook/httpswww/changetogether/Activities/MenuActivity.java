package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class MenuActivity extends Activity {
    Button profile, biete, inbox, search, logout, helpingLocations;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        account = (Account) getApplication();

        profile = (Button) findViewById(R.id.profileButton);
        biete = (Button) findViewById(R.id.bieteButton);
        inbox = (Button) findViewById(R.id.inboxButton);
        search = (Button) findViewById(R.id.searchButton);
        logout = (Button) findViewById(R.id.logoutButton);
        helpingLocations = (Button) findViewById(R.id.helpingLocationsButton);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent OwnProfileActivity = new Intent(MenuActivity.this, OwnProfileActivity.class);
                startActivity(OwnProfileActivity);
                finish();
            }
        });

        biete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bieteActivity = new Intent(MenuActivity.this, BieteActivity.class);
                startActivity(bieteActivity);
                finish();
            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inboxActivity = new Intent(MenuActivity.this, InboxActivity.class);
                startActivity(inboxActivity);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(searchActivity);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.logout();
                Intent main = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(main);
                finishAffinity();
            }
        });

        helpingLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(MenuActivity.this, HelpingLocationsActivity.class);
                startActivity(main);
                finish();
            }
        });

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

package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
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
                Intent profileActivity = new Intent(MenuActivity.this, ProfileActivity.class);
                profileActivity.putExtra("searchedUser", account.getEmail());
                startActivity(profileActivity);
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
                account.logout();
                Intent main = new Intent(MenuActivity.this, HelpingLocationsActivity.class);
                startActivity(main);
                finish();
            }
        });

    }
}

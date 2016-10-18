package comhelpingandchanging.facebook.httpswww.changetogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by len13 on 17.10.2016.
 */

public class MenuActivity extends Activity {
    Button profile, biete, inbox, search, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        profile = (Button) findViewById(R.id.profileButton);
        biete = (Button) findViewById(R.id.bieteButton);
        inbox = (Button) findViewById(R.id.inboxButton);
        search = (Button) findViewById(R.id.searchButton);
        logout = (Button) findViewById(R.id.logoutButton);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActivity = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(profileActivity);
            }
        });

        biete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bieteActivity = new Intent(MenuActivity.this, BieteActivity.class);
                startActivity(bieteActivity);
            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inboxActivity = new Intent(MenuActivity.this, InboxActivity.class);
                startActivity(inboxActivity);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(searchActivity);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(login);
            }
        });

    }
}

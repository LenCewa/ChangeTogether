package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchItemActivity extends Activity {

    Account account;
    ImageView userProfile;
    TextView userEmail;
    TextView userBid;
    TextView userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        account = (Account) getApplication();
        userProfile = (ImageView) findViewById(R.id.userProfile);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userBid = (TextView) findViewById(R.id.userBid);
        userDescription = (TextView) findViewById(R.id.userDescription);

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(SearchItemActivity.this, ProfileActivity.class);
                startActivity(profile);
            }
        });

        String[] info = (String[]) getIntent().getExtras().get("searchInfo");
        Log.e("info", Arrays.toString(info));
        account.searchUser(this, info[0], info[1], info[2]);
    }

    public void setElements(){

        userProfile.setImageBitmap(account.getSearchProfilePic());
        userEmail.setText(account.getSearchEmail());
        userBid.setText(account.getSearchTag());
        userDescription.setText(account.getSearchDescription());
    }
}

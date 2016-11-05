package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileActivity extends Activity {

    TextView profileName;
    TextView profileLocation;
    TextView profileLanguage;
    ImageView profilePic;
    ListView bidList;
    public ArrayList<String[]> bieteItems = new ArrayList<String[]>();
    public ArrayList<String[]> helpingLocations = new ArrayList<String[]>(); // TODO: Benutzen, wegen LoadHelpingLocationsActivity.java
    public CustomAdapterProfile adapter;
    Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        account =(Account)getApplication();

        profileName = (TextView) findViewById(R.id.profileName);
        profileName.setText(account.getSearchEmail());

        profileLocation = (TextView) findViewById(R.id.profileLocation);
        profileLocation.setText(account.getLocation());

        profileLanguage = (TextView) findViewById(R.id.profileLanguage);
        profileLanguage.setText(account.getLanguage());

        profilePic = (ImageView) findViewById(R.id.imageView);
        profilePic.setImageBitmap(account.getSearchProfilePic());

        bidList = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapterProfile(this, bieteItems);
        bidList.setAdapter(adapter);

        account.loadBidsActivity(this);
        account.loadHelpingLocationsActivity(this);

        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] arr = (String[]) adapter.getItem(position);
                String tag = arr[0];
                String description = arr[1];

                Intent intent = new Intent(ProfileActivity.this, ShowBidFeedback.class);
                intent.putExtra("tag", tag);
                intent.putExtra("description", description);
                startActivity(intent);
            }
        });
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileFragment extends Fragment {

    View view;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profile, container, false);
        account =(Account) getActivity().getApplication();

        profileName = (TextView) view.findViewById(R.id.profileName);
        profileName.setText(account.getSearchEmail());

        profileLocation = (TextView) view.findViewById(R.id.profileLocation);
        profileLocation.setText(account.getSearchLocation());

        profileLanguage = (TextView) view.findViewById(R.id.profileLanguage);
        profileLanguage.setText(account.getSearchLanguage());

        profilePic = (ImageView) view.findViewById(R.id.profPic);
        profilePic.setImageBitmap(account.getSearchProfilePic());

        bidList = (ListView) view.findViewById(R.id.list);
        adapter = new CustomAdapterProfile(getActivity(), bieteItems);
        bidList.setAdapter(adapter);

        account.loadBidsActivity(this);
        //account.loadHelpingLocationsActivity(this);

        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String[] arr = (String[]) adapter.getItem(position);
                String tag = arr[0];
                int ID = Integer.parseInt(arr[2]);

                Intent intent = new Intent(getActivity(), ShowBidFeedback.class);
                intent.putExtra("id", ID);
                intent.putExtra("tag", tag);
                startActivity(intent);
            }
        });

        return view;
    }
}

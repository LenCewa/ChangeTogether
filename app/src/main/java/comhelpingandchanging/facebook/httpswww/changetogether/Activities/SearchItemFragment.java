package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchItemFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;
    ImageView userProfile;
    TextView userEmail;
    TextView userBid;
    TextView userDescription;

    public SearchItemFragment(){
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search_item, container, false);
        callingActivity = getActivity();

        account = (Account) callingActivity.getApplication();
        userProfile = (ImageView) view.findViewById(R.id.userProfile);
        userEmail = (TextView) view.findViewById(R.id.userEmail);
        userBid = (TextView) view.findViewById(R.id.userBid);
        userDescription = (TextView) view.findViewById(R.id.userDescription);

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent profile = new Intent(SearchItemFragment.this, ProfileActivity.class);
                //startActivity(profile);
            }
        });

        String[] info = (String[]) getArguments().get("searchInfo");
        account.searchUser(this, info[0], info[1], info[2]);

        return view;
    }

    public void setElements(){

        userProfile.setImageBitmap(account.getSearchProfilePic());
        userEmail.setText(account.getSearchEmail());
        userBid.setText(account.getSearchTag());
        userDescription.setText(account.getSearchDescription());
    }
}

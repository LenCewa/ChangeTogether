package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class OwnProfileFragment extends Fragment {

    View view;
    ListView ownlist;
    ImageView settings;
    Account account;
    TextView username;
    TextView location;
    TextView language;
    public ImageView profilePic;
    ArrayList<String> myArray = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ownprofile, container, false);

        account = (Account) getActivity().getApplication();

        ownlist = (ListView) view.findViewById(R.id.ownList);
        username = (TextView) view.findViewById(R.id.ownProfileName);
        location = (TextView) view.findViewById(R.id.ownProfileLocation);
        language = (TextView) view.findViewById(R.id.ownProfileLanguage);
        profilePic = (ImageView) view.findViewById(R.id.ownProfilePic);

        myArray.add("Hallo");
        myArray.add("Hallo");
        myArray.add("Hallo");
        myArray.add("Hallo");
        myArray.add("Hallo");

        ownlist.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myArray));

        refresh();

        settings = (ImageView) view.findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsActivity = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsActivity);
            }
        });
        return view;
    }

    private void refresh() {
        profilePic.setImageBitmap(account.getProfilePic());
        username.setText(account.getEmail());
        location.setText(account.getLocation());
        language.setText(account.getLanguage());
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        ((MainAppActivity)getActivity()).navigationView.setCheckedItem(R.id.nav_own_profile);
    }
}

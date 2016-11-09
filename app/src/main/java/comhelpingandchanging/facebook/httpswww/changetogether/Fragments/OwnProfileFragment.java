package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SettingsActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class OwnProfileFragment extends Fragment {

    View view;
    AppBarLayout toolbar;
    ImageView settings;
    Account account;
    TextView location;
    TextView language;
    public ImageView profilePic;
    ArrayList<String> myArray = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ownprofile, container, false);

        account = (Account) getActivity().getApplication();

        ((AppBarLayout)getActivity().findViewById(R.id.app_bar_layout)).setExpanded(true);

        location = (TextView) view.findViewById(R.id.ownProfileLocation);
        language = (TextView) view.findViewById(R.id.ownProfileLanguage);
        profilePic = (ImageView) getActivity().findViewById(R.id.ownProfilePic);

        refresh();

        settings = (ImageView) getActivity().findViewById(R.id.fab);
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

        ((AppBarLayout)getActivity().findViewById(R.id.app_bar_layout)).setExpanded(true);
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitle(account.getEmail());
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(account.getProfilePic());
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

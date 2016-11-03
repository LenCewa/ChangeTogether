package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class OwnProfileFragment extends Fragment {

    View view;
    Button settings;
    Account account;
    TextView username;
    TextView location;
    TextView language;
    public ImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ownprofile, container, false);

        account = (Account) getActivity().getApplication();
        username = (TextView) view.findViewById(R.id.ownUsername);
        location = (TextView) view.findViewById(R.id.ownLoaction);
        language = (TextView) view.findViewById(R.id.ownLanguage);
        profilePic = (ImageView) view.findViewById(R.id.ownProfilePic);

        setElements();

        settings = (Button) view.findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsActivity = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsActivity);
            }
        });
        return view;
    }

    public void setElements() {
        profilePic.setImageBitmap(account.getProfilePic());
        username.setText(account.getEmail());
        location.setText(account.getLocation());
        language.setText(account.getLanguage());
    }
}

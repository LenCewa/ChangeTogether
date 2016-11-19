package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.DialogFragments.FeedbackDialog;
import comhelpingandchanging.facebook.httpswww.changetogether.DialogFragments.MyDialogCloseListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 05.11.2016.
 */

public class SearchItemFragment extends Fragment{

    View view;
    Activity callingActivity;
    Account account;
    Button join;
    ImageView userProfile;
    TextView userEmail;
    TextView userBid;
    TextView userDescription;
    TextView ratings;
    RatingBar ratingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search_item, container, false);
        callingActivity = getActivity();

        account = (Account) callingActivity.getApplication();
        userProfile = (ImageView) view.findViewById(R.id.userProfile);
        userEmail = (TextView) view.findViewById(R.id.userEmail);
        userBid = (TextView) view.findViewById(R.id.userBid);
        userDescription = (TextView) view.findViewById(R.id.userDescription);
        ratingBar = (RatingBar) view.findViewById(R.id.avergageRating);
        ratings = (TextView) view.findViewById(R.id.rezensionen);
        join = (Button) view.findViewById(R.id.joinButton);

        userProfile.setImageBitmap(account.getSearchProfilePic());
        userEmail.setText(account.getSearchEmail());
        userBid.setText(account.getSearchTag());
        userDescription.setText(account.getSearchDescription());
        ratingBar.setRating(account.getSearchAverageRating());
        ratings.setText(account.getSearchCount() + " Rezensionen");

        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("Angebot von " + account.getSearchEmail());
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(null);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TODO", "An DB senden");
            }
        });

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getActivity(), ShowBidFeedback.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowBidFeedback.class);
                startActivity(intent);
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment f = new ProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_frame, f, "profile").addToBackStack(null).commit();
            }
        });

        return view;
    }
}

package app.radiant.c.lly.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import app.radiant.c.lly.DialogFragments.EditDialog;
import app.radiant.c.lly.Activities.ShowBidFeedbackActivity;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Ludwig on 20.11.2016.
 */

public class OwnSearchItemFragment extends Fragment {

        View view;
        Activity callingActivity;
        Account account;
        Button edit;
        ImageView userProfile;
        TextView userEmail;
        TextView userBid;
        TextView timenDate;
        TextView userDescription;
        TextView ratings;
        RatingBar ratingBar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_own_search_item, container, false);
            callingActivity = getActivity();

            account = (Account) callingActivity.getApplication();
            userProfile = (ImageView) view.findViewById(R.id.userProfile);
            userEmail = (TextView) view.findViewById(R.id.userEmail);
            userBid = (TextView) view.findViewById(R.id.userBid);
            userDescription = (TextView) view.findViewById(R.id.userDescription);
            timenDate = (TextView) view.findViewById(R.id.timenDate);
            ratingBar = (RatingBar) view.findViewById(R.id.avergageRating);
            ratings = (TextView) view.findViewById(R.id.rezensionen);
            edit = (Button) view.findViewById(R.id.editButton);

            userProfile.setImageBitmap(account.getProfilePic());
            userEmail.setText(account.getEmail());
            userBid.setText(account.getSearchTag());
            timenDate.setText(account.getSearchDate() + " - " + account.getSearchTime() + " Uhr");
            userDescription.setText(account.getSearchDescription());
            ratingBar.setRating(account.getSearchAverageRating());
            ratings.setText(account.getSearchCount() + " Rezensionen");

            ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
            ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("Angebot von " + account.getSearchEmail());
            ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(null);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditDialog add = new EditDialog();
                    add.show(getChildFragmentManager(), "Edit Dialog");
                }
            });

            ratingBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Intent intent = new Intent(getActivity(), ShowBidFeedbackActivity.class);
                        startActivity(intent);
                    }
                    return true;
                }
            });

            ratings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ShowBidFeedbackActivity.class);
                    startActivity(intent);
                }
            });

            return view;
        }


        public boolean listContainsId(String id){

            for (String[] s : account.getParticipations()){
                if(s[0].equals(id))
                    return true;
            }
            return false;
        }
    }


package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.RecyclerItemClickListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileFragment extends SuperProfileFragment {

    TextView profileName;
    TextView profileLocation;
    TextView profileLanguage;
    ImageView profilePic;
    public ArrayList<String[]> helpingLocations = new ArrayList<String[]>(); // TODO: Benutzen, wegen LoadHelpingLocationsActivity.java

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_ownprofile, container, false);
        account =(Account) getActivity().getApplication();

        profileName = (TextView) view.findViewById(R.id.profileName);

        profileLocation = (TextView) view.findViewById(R.id.ownProfileLocation);
        profileLocation.setText(account.getSearchLocation());

        profileLanguage = (TextView) view.findViewById(R.id.ownProfileLanguage);
        profileLanguage.setText(account.getSearchLanguage());

        profilePic = (ImageView) view.findViewById(R.id.ownProfilePic);

        adapter = new CustomRecyclerViewAdapter(bieteItems);
        bidList = (RecyclerView) view.findViewById(R.id.cardList);
        bidList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        bidList.setLayoutManager(llm);
        bidList.setAdapter(adapter);

        bidList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {


                        String[] arr = (String[]) adapter.getItem(position);
                        int ID = Integer.parseInt(arr[0]);
                        String tag = arr[1];

                        Intent intent = new Intent(getActivity(), ShowBidFeedback.class);
                        intent.putExtra("id", ID);
                        intent.putExtra("tag", tag);
                        startActivity(intent);
                    }
                })
        );

        account.loadBidsActivity(this, account.getSearchEmail());

        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("");
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(true);
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitle(account.getSearchEmail() + "'s Profil");

        //Anpassung für andere Display Densities
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, getActivity().getResources().getDisplayMetrics());
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(Bitmap.createScaledBitmap(account.getSearchProfilePic(),getActivity().getResources().getDisplayMetrics().widthPixels, (int)px, false));

        return view;
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomAdapterProfile;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.RecyclerItemClickListener;
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
    RecyclerView bidList;
    public ArrayList<String[]> bieteItems = new ArrayList<String[]>();
    public ArrayList<String[]> helpingLocations = new ArrayList<String[]>(); // TODO: Benutzen, wegen LoadHelpingLocationsActivity.java
    public CustomRecyclerViewAdapter adapter;
    Account account;


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

        account.loadBidsActivity(this);

        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("");
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(true);
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitle(account.getSearchEmail() + "'s Profil");
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(account.getSearchProfilePic());

        return view;
    }
}

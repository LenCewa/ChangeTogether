package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ChatActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.RecyclerItemClickListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileFragment extends SuperProfileFragment {

    //TextView profileName;
    TextView profileLocation;
    TextView profileLanguage;
    ImageView profilePic;
    FloatingActionButton sendMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_profile, container, false);
        account =(Account) getActivity().getApplication();

        //profileName = (TextView) view.findViewById(R.id.profileName);

        profileLocation = (TextView) view.findViewById(R.id.ownProfileLocation);
        profileLanguage = (TextView) view.findViewById(R.id.ownProfileLanguage);

        profilePic = (ImageView) getActivity().findViewById(R.id.ownProfilePic);

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


                        SearchItemFragment f = new SearchItemFragment();
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, f, "searchItem").addToBackStack(null).commit();

                        /**
                        String[] arr = (String[]) adapter.getItem(position);
                        int ID = Integer.parseInt(arr[0]);
                        String tag = arr[1];

                        Intent intent = new Intent(getActivity(), ShowBidFeedback.class);
                        intent.putExtra("id", ID);
                        intent.putExtra("tag", tag);
                        startActivity(intent);
                         **/
                    }
                })
        );

        account.loadBidsActivity(this, account.getSearchEmail());

        sendMessage = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        sendMessage.setImageResource(R.drawable.ic_menu_send);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChatActivity.class);
                startActivity(i);
            }
        });


        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256, r.getDisplayMetrics());

        profilePic.setImageBitmap(ThumbnailUtils.extractThumbnail(account.getSearchProfilePic(), profilePic.getWidth(), (int)px));
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("");
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(true);
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitle(account.getSearchEmail() + "'s Profil");


        account.searchUer(this);

        return view;
    }

    public void setElements(){

        profileLocation.setText(account.getSearchUserLocation());
        profileLanguage.setText(account.getSearchLanguage());
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapterOwnProfile;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.RecyclerItemClickListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class OwnBidsFragment extends SuperProfileFragment {


    TextView location;
    TextView language;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        account = (Account) getActivity().getApplication();

        location = (TextView) view.findViewById(R.id.ownProfileLocation);
        language = (TextView) view.findViewById(R.id.ownProfileLanguage);

        adapter = new CustomRecyclerViewAdapterOwnProfile(this, account.getOwnBids());
        bidList = (RecyclerView) view.findViewById(R.id.cardList);
        bidList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        bidList.setLayoutManager(llm);
        bidList.setAdapter(adapter);

        bidList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        CustomRecyclerViewAdapterOwnProfile adapter = (CustomRecyclerViewAdapterOwnProfile) OwnBidsFragment.this.adapter;

                        String id = adapter.getItem(position)[0];
                        String email = adapter.getItem(position)[1];
                        String tag = adapter.getItem(position)[2];
                        String description = adapter.getItem(position)[3];
                        String location = adapter.getItem(position)[4];
                        String averageRating = adapter.getItem(position)[5];
                        String count = adapter.getItem(position)[6];
                        String distance = adapter.getItem(position)[7];
                        String date = adapter.getItem(position)[8];
                        String time = adapter.getItem(position)[9];
                        String part = adapter.getItem(position)[10];
                        String maxPart = adapter.getItem(position)[11];

                        account.setSearchedItem(getActivity(), id, email, tag, description, location, averageRating, count, distance, date, time, part, maxPart, null);
                        OwnSearchItemFragment f = new OwnSearchItemFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f, "OwnsearchItem").addToBackStack(null).commit();
                    }
                })
        );

        return view;
    }

    private void refresh() {

        account.loadOwnBids(this);
        location.setText(account.getLocation());
        language.setText(account.getLanguage());
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
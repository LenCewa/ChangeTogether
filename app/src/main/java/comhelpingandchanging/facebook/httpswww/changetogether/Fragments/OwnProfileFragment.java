package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SettingsActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.ShowBidFeedback;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.RecyclerItemClickListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class OwnProfileFragment extends SuperProfileFragment {

    ImageView settings;
    TextView location;
    TextView language;
    public ImageView profilePic;
    int n = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ownprofile, container, false);

        account = (Account) getActivity().getApplication();

        location = (TextView) view.findViewById(R.id.ownProfileLocation);
        language = (TextView) view.findViewById(R.id.ownProfileLanguage);
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

        account.loadBidsActivity(this, account.getEmail());

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getActivity().getResources().getDisplayMetrics());
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(Bitmap.createScaledBitmap(account.getProfilePic(),getActivity().getResources().getDisplayMetrics().widthPixels, (int)px, false));

        //refresh();

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

        Log.e("hallo", "hall");
        //((ImageView)getActivity().findViewById(R.id.profPic)).setImageBitmap(account.getProfilePic());
        Picasso
                .with(getActivity())
                .load(account.uri)
                .fit()
                .centerCrop()
                .into((ImageView)getActivity().findViewById(R.id.profPic));
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("");
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(true);
        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitle(account.getEmail());
        location.setText(account.getLocation());
        language.setText(account.getLanguage());


        Picasso
                .with(getActivity())
                .load(account.uri)
                .fit()
                .centerCrop()
                .into(profilePic);
        //Anpassung für andere Display Densities
        //float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getActivity().getResources().getDisplayMetrics());
        //((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(Bitmap.createScaledBitmap(account.getProfilePic(),getActivity().getResources().getDisplayMetrics().widthPixels, (int)px, false));

    }

    @Override
    public void onResume() {
        super.onResume();
        if(n == 1)
        refresh();
        n++;
        ((MainAppActivity)getActivity()).navigationView.setCheckedItem(R.id.nav_own_profile);
    }
}

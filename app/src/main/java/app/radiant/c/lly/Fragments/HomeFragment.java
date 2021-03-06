package app.radiant.c.lly.Fragments;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import app.radiant.c.lly.Activities.MainAppActivity;
import app.radiant.c.lly.Activities.SettingsActivity;
import app.radiant.c.lly.Adapter.CustomRecyclerViewAdapter;
import app.radiant.c.lly.Adapter.CustomRecyclerViewAdapterHome;
import app.radiant.c.lly.Adapter.RecyclerItemClickListener;
import app.radiant.c.lly.DialogFragments.BidDialog;
import app.radiant.c.lly.NetworkUtilities.HomeShowBids;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;
import app.radiant.c.lly.Utilities.Constants;
import app.radiant.c.lly.Widgets.HidingScrollListener;

/**
 * Created by Yannick on 03.11.2016.
 */

public class HomeFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;
    Double[] latLong;
    public RecyclerView searches;
    public ArrayList<String[]> listItems = new ArrayList<String[]>();
    public CustomRecyclerViewAdapterHome adapter;
    View.OnClickListener setLocation;
    public Comparator<String[]> cmp;
    public SwipeRefreshLayout swipeContainer;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BidDialog add = new BidDialog();
                add.show(getChildFragmentManager(), "Biete Dialog");
            }
        });

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        tabLayout.setVisibility(TabLayout.GONE);

        adapter = new CustomRecyclerViewAdapterHome(getActivity(), listItems);
        searches = (RecyclerView) view.findViewById(R.id.homeList);
        searches.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searches.setLayoutManager(llm);
        searches.setAdapter(adapter);
        searches.setOnScrollListener(new HidingScrollListener() {
            AppBarLayout mToolbar = (AppBarLayout) getActivity().findViewById(R.id.app_bar_layout);
            @Override
            public void onHide() {
                fab.hide();
                mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {
                fab.show();
                mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
            }
        });

        cmp = new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                int distance1 = Integer.parseInt(o1[7]);
                int distance2 = Integer.parseInt(o2[7]);
                if(distance1 < distance2)
                    return -1;
                else if(distance1 > distance2)
                    return 1;
                else
                    return 0;
            }
        };

        setLocation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setLocation = new Intent(getActivity(), SettingsActivity.class);
                startActivity(setLocation);
            }
        };

        searches.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

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

                account.setSearchedItem(getActivity(), id, email, tag, description, location, averageRating, count, distance, date, time, part, maxPart);
                SearchItemFragment f = new SearchItemFragment();
                account.fm.beginTransaction().replace(R.id.content_frame, f, "searchItem").addToBackStack("searchItem").commit();
            }
        }));

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(account.getSelf().getLocation().equals("N/A"))
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Please set your location first", Snackbar.LENGTH_LONG)
                            .setAction("Set location", setLocation)
                            .setActionTextColor(Color.RED)
                            .show();
                else {
                    HashMap<String, String> data = account.getAuthMap();
                    data.put("email", account.getSelf().getEmail());
                    data.put("latitude", String.valueOf(account.getSelf().getLat()));
                    data.put("longitude", String.valueOf(account.getSelf().getLng()));
                    data.put("lastId", Constants.lastIdHome);
                    new HomeShowBids(HomeFragment.this, data).execute();
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        ((FloatingActionButton) getActivity().findViewById(R.id.fab)).setVisibility(View.GONE);

        if(account.getSelf().getLocation().equals("N/A"))
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please set your location first", Snackbar.LENGTH_LONG)
                    .setAction("Set location", setLocation)
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            HashMap<String, String> data = account.getAuthMap();
            data.put("email", account.getSelf().getEmail());
            data.put("latitude", String.valueOf(account.getSelf().getLat()));
            data.put("longitude", String.valueOf(account.getSelf().getLng()));
            data.put("lastId", Constants.lastIdHome);
            new HomeShowBids(this, data).execute();
        }

        return view;
    }

    private void refresh(){

        RelativeLayout content = (RelativeLayout) getActivity().findViewById(R.id.content_main_app);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) content.getLayoutParams();
        p.setBehavior(null);
        content.setLayoutParams(p);

        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("Angebote in deiner Nähe");
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        ((MainAppActivity)getActivity()).navigationView.setCheckedItem(R.id.nav_home);
    }
}

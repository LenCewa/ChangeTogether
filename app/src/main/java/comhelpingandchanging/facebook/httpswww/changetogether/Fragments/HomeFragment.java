package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SettingsActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomAdapterSearch;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class HomeFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;
    Double[] latLong;
    public ListView searches;
    public ArrayList<String[]> listItems = new ArrayList<String[]>();
    public CustomAdapterSearch adapter;
    View.OnClickListener setLocation;
    public Comparator<String[]> cmp;

    public HomeFragment(){
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();

        searches = (ListView) view.findViewById(R.id.homeList);
        adapter = new CustomAdapterSearch(callingActivity, listItems);
        searches.setAdapter(adapter);

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

        searches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchItemFragment f = new SearchItemFragment();
                f.getArguments().putStringArray("searchInfo", (String[]) adapter.getItem(position));
                getFragmentManager().beginTransaction().replace(R.id.content_frame, f, "searchItem").addToBackStack(null).commit();
            }
        });

        refresh();

        return view;
    }

    public Double[] getLocationFromAddress(String strAddress){

        Double[] latLong = new Double[2];
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,1);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            latLong[0] = location.getLatitude();
            latLong[1] = location.getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLong;
    }

    private void refresh(){

        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("Angebote in deiner Nähe");
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(null);

        if(account.getLocation().equals("N/A"))
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please set your location first", Snackbar.LENGTH_LONG)
                    .setAction("Set location", setLocation)
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            latLong = getLocationFromAddress(account.getLocation());
            if(account != null && latLong != null)
                account.homeShowBids(this, latLong[0], latLong[1]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisible())
            refresh();
        ((MainAppActivity)getActivity()).navigationView.setCheckedItem(R.id.nav_home);
    }
}

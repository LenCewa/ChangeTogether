package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        final View.OnClickListener setLocation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setLocation = new Intent(getActivity(), SettingsActivity.class);
                startActivity(setLocation);
            }
        };

        searches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent searchItem = new Intent(getActivity(), SearchItemActivity.class);
                searchItem.putExtra("searchInfo", (String[]) adapter.getItem(position));
                startActivity(searchItem);
            }
        });

        if(account.getLocation().equals("N/A"))
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please set your location first", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Set location", setLocation)
                    .setActionTextColor(Color.RED)
                    .show();
        else {
            latLong = getLocationFromAddress(account.getLocation());
            account.homeShowBids(this, latLong[0], latLong[1]);
        }

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
        if(!account.getLocation().equals("N/A")) {
            latLong = getLocationFromAddress(account.getLocation());
            account.homeShowBids(this, latLong[0], latLong[1]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh();
    }
}

package app.radiant.c.lly.Fragments;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import app.radiant.c.lly.Activities.MainAppActivity;
import app.radiant.c.lly.Adapter.CustomAdapterBiete;
import app.radiant.c.lly.DialogFragments.BidDialog;
import app.radiant.c.lly.DialogFragments.MyDialogCloseListener;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class BieteFragment extends Fragment implements MyDialogCloseListener {

    View view;
    MainAppActivity callingActivity;
    ListView bieteList;
    public CustomAdapterBiete adapter;
    Account account;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_biete, container, false);
        callingActivity = (MainAppActivity) getActivity();

        account = (Account) callingActivity.getApplication();

        bieteList = (ListView) view.findViewById(R.id.bieteList);
        adapter = new CustomAdapterBiete(getActivity(), account.getOwnBids());
        bieteList.setAdapter(adapter);


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BidDialog add = new BidDialog();
                add.show(getChildFragmentManager(), "Biete Dialog");
            }
        });

        bieteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ID) {
                String id = adapter.getItem(position)[0];
                String email = adapter.getItem(position)[1];
                String tag = adapter.getItem(position)[2];
                String description = adapter.getItem(position)[3];
                String location = adapter.getItem(position)[4];
                String averageRating = adapter.getItem(position)[5];
                String count = adapter.getItem(position)[6];
                String date = adapter.getItem(position)[7];
                String time = adapter.getItem(position)[8];
                String part = adapter.getItem(position)[9];
                String maxPart = adapter.getItem(position)[10];

                account.setSearchedItem(getActivity(), id, email, tag, description, location, averageRating, count, null, date, time, part, maxPart);
                OwnSearchItemFragment f = new OwnSearchItemFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f, "OwnsearchItem").addToBackStack(null).commit();
            }
        });


        bieteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //account.deleteBid(BieteFragment.this, adapter.getItem(position), "");
                //account.loadBids(BieteFragment.this);
                return true;
            }
        });

        return view;
    }

    private void refresh(){

        ((CollapsingToolbarLayout)getActivity().findViewById(R.id.collapsing_toolbar)).setTitleEnabled(false);
        ((TextView)getActivity().findViewById(R.id.toolbar_title)).setText("Deine Angebote");
        ((ImageView)getActivity().findViewById(R.id.ownProfilePic)).setImageBitmap(null);
        account.loadOwnBids(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
        ((MainAppActivity)getActivity()).navigationView.setCheckedItem(R.id.nav_biete);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        refresh();
    }
}

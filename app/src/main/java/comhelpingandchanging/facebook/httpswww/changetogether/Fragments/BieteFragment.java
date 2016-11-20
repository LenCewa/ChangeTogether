package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

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

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.MainAppActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomAdapterBiete;
import comhelpingandchanging.facebook.httpswww.changetogether.DialogFragments.BidDialogNew;
import comhelpingandchanging.facebook.httpswww.changetogether.DialogFragments.MyDialogCloseListener;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class BieteFragment extends Fragment implements MyDialogCloseListener {

    View view;
    MainAppActivity callingActivity;
    public ArrayList<String[]> bieteItems = new ArrayList<>();
    ListView bieteList_class;
    public CustomAdapterBiete adapter;
    Account account;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_biete, container, false);
        callingActivity = (MainAppActivity) getActivity();

        account = (Account) callingActivity.getApplication();

        bieteList_class = (ListView) view.findViewById(R.id.bieteList);
        adapter = new CustomAdapterBiete(getActivity(), bieteItems);
        bieteList_class.setAdapter(adapter);

        account.loadBids(this);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BidDialogNew add = new BidDialogNew();
                add.show(getChildFragmentManager(), "Biete Dialog");
            }
        });

        bieteList_class.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        account.loadBids(this);
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

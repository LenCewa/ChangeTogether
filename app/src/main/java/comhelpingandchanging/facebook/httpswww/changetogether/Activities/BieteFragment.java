package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class BieteFragment extends Fragment {

    View view;
    MainAppActivity callingActivity;
    public ArrayList<String> bieteItems = new ArrayList<>();
    ListView bieteList_class;
    public ArrayAdapter<String> adapter;
    TextView profileInfo;
    Account account;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_biete, container, false);
        callingActivity = (MainAppActivity) getActivity();

        account = (Account) callingActivity.getApplication();

        profileInfo = (TextView) view.findViewById(R.id.textView2);
        profileInfo.setText(account.getEmail() + " - " + account.getLocation() + " - " + account.getLanguage());

        bieteList_class = (ListView) view.findViewById(R.id.bieteList);
        adapter = new ArrayAdapter<String>(callingActivity, android.R.layout.simple_list_item_1, bieteItems);
        bieteList_class.setAdapter(adapter);

        account.loadBids(this, account.getEmail());

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BidDialog add = new BidDialog();
                add.setTargetFragment(BieteFragment.this, 0);
                add.show(getFragmentManager(), "Biete Dialog");
            }
        });

        bieteList_class.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                account.deleteBid(adapter.getItem(position), "");
                bieteItems.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        return view;
    }
}

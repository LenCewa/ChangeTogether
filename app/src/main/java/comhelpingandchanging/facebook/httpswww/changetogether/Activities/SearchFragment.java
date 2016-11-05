package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class SearchFragment extends Fragment {

    View view;
    Activity callingActivity;
    Button menu;
    TextView profileInfo;
    EditText searchField;
    Button searchBtn;
    Account account;
    public ListView searches;
    public ArrayList<String[]> listItems = new ArrayList<String[]>();
    public CustomAdapterSearch adapter;

    public SearchFragment(){
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();

        profileInfo = (TextView) view.findViewById(R.id.textView);
        profileInfo.setText(account.getEmail() + " - " + account.getLocation() + " - " + account.getLanguage());

        searches = (ListView) view.findViewById(R.id.searchList);
        adapter = new CustomAdapterSearch(callingActivity, listItems);
        searches.setAdapter(adapter);

        searchField = (EditText) view.findViewById(R.id.editText3);
        searchField.setText(getArguments().getString("searchText"));

        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.clear();
                adapter.notifyDataSetChanged();
                account.searchBid(SearchFragment.this, searchField.getText().toString());
            }
        });

        searches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent searchItem = new Intent(getActivity(), SearchItemActivity.class);
                searchItem.putExtra("searchInfo", (String[]) adapter.getItem(position));
                startActivity(searchItem);
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        getArguments().putString("searchText", searchField.getText().toString());
    }
}

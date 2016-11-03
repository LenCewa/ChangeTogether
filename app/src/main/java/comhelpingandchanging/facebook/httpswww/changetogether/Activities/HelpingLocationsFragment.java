package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 03.11.2016.
 */

public class HelpingLocationsFragment extends Fragment {

    View view;
    Button menu;
    Account account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_biete, container, false);
        account = (Account) getActivity().getApplication();
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) view.findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item)); // vorher getActivity() anstelle von this

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                //Toast.makeText(this, description, Toast.LENGTH_SHORT).show(); // TODO vorher getActivity() anstelle von this
            }
        });
        return view;
    }
}
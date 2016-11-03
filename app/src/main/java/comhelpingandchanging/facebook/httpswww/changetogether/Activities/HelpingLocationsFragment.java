package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    MainAppActivity callingActivity;
    View view;
    Account account;
    Button addLocation, deleteLocation;
    TextView locationName, addedLocations;
    ListView addedLocationsList;
    ArrayAdapter adapter;
    ArrayList<String> usersHelpingLocations = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_helping_locations, container, false);
        account = (Account) getActivity().getApplication();
        callingActivity = (MainAppActivity) getActivity();

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


        locationName = (TextView) view.findViewById(R.id.autocomplete);
        addLocation = (Button) view.findViewById(R.id.addLocationBtn);
        addedLocations = (TextView) view.findViewById(R.id.addedLocationsTextView);

        //ListView vorbereiten
        addedLocationsList = (ListView) view.findViewById(R.id.addedLocationsListView);
        adapter = new ArrayAdapter<String>(callingActivity, android.R.layout.simple_list_item_1, usersHelpingLocations);
        addedLocationsList.setAdapter(adapter);
        //ListView vorbereitet

        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = locationName.getText().toString();
                if (!isDuplicate(location)) {
                    usersHelpingLocations.add(location);
                    Log.e("autocomplete", location);
                    adapter.notifyDataSetChanged();
                }
                else
                    Log.e("adding a dupl. location", location);
            }
        });

        deleteLocation = (Button) view.findViewById(R.id.deleteLocationBtn);
        deleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Info");
                alertDialogBuilder.setMessage("Um etwas zu löschen drücken Sie lange auf das zu löschende Element.")
                        .setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        addedLocationsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                usersHelpingLocations.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                return true;
            }
        });


        // Ret view Statement
        return view;
    }

    public boolean isDuplicate(String location) {
        for (String loc : usersHelpingLocations)
            if (loc.equals(location))
                return true;
        return false;
    }

}
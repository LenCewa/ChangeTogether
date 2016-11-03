package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 29.10.2016.
 */

public class HelpingLocationsActivity extends Activity {
    Button menu, addLocation;
    Account account;
    TextView locationName, addedLocations;
    ListView addedLocationsList;
    ArrayAdapter<String> adapter;
    ArrayList<String> usersHelpingLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_locations);

        account = (Account) getApplication();
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item)); // vorher getActivity() anstelle von this

        menu = (Button) findViewById(R.id.button4);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(HelpingLocationsActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        /*autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                //Toast.makeText(this, description, Toast.LENGTH_SHORT).show(); // vorher getActivity() anstelle von this
            }
        });*/

        locationName = (TextView) findViewById(R.id.autocomplete);
        addLocation = (Button) findViewById(R.id.addLocationBtn);
        addedLocations = (TextView) findViewById(R.id.addedLocationTextView);

        //ListView vorbereiten
        addedLocationsList = (ListView) findViewById(R.id.addedLocationsListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersHelpingLocations);
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

    }

    public boolean isDuplicate(String location) {
        for (String loc : usersHelpingLocations)
            if (loc.equals(location))
                return true;
        return false;
    }
}

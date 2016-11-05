package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Ludwig on 05.11.2016.
 */

public class BidDialogNew extends DialogFragment {
    BieteFragment callingFragment;
    Spinner bidTypes;
    TextView description, locationName;
    Button done;
    ArrayList<String> usersHelpingLocations = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_select_biete_new, container, false);

        callingFragment = (BieteFragment) getTargetFragment();

        bidTypes = (Spinner) rootView.findViewById(R.id.bidTypes);

        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) rootView.findViewById(R.id.location);
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


        locationName = (TextView) rootView.findViewById(R.id.location);

        description = (TextView) rootView.findViewById(R.id.description);

        done = (Button) rootView.findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Account) callingFragment.getActivity().getApplication()).
                        addBid(callingFragment, bidTypes.getSelectedItem().toString(), description.getText().toString());
                getDialog().dismiss();
            }
        });

        return rootView;
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

public class BidDialogNew extends DialogFragment {
    AutoCompleteTextView location;
    BieteFragment callingFragment;
    String city;
    Spinner bidTypes;
    TextView description;
    Button done;
    ArrayList<String> usersHelpingLocations = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_select_biete_new, container, false);

        callingFragment = (BieteFragment) getParentFragment();

        location = (AutoCompleteTextView) rootView.findViewById(R.id.location);

        bidTypes = (Spinner) rootView.findViewById(R.id.bidTypes);

        final AutoCompleteTextView autocompleteView = (AutoCompleteTextView) rootView.findViewById(R.id.location);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item)); // vorher getActivity() anstelle von this

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                city = parent.getItemAtPosition(position).toString();
            }
        });



        description = (TextView) rootView.findViewById(R.id.description);

        done = (Button) rootView.findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city.equals(location.getText().toString())) {
                    Double[] latLong = getLocationFromAddress(autocompleteView.getText().toString());

                    ((Account) callingFragment.getActivity().getApplication()).
                            addBid(callingFragment, bidTypes.getSelectedItem().toString(), description.getText().toString(),
                                    autocompleteView.getText().toString(), latLong[0], latLong[1]);
                    getDialog().dismiss();
                }
                else
                    location.setError("Location doesn't exist");
            }
        });

        return rootView;
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Activity activity = getActivity();
        if(callingFragment instanceof MyDialogCloseListener)
            ((MyDialogCloseListener)callingFragment).handleDialogClose(dialog);
        else if(activity instanceof MyDialogCloseListener)
            ((MyDialogCloseListener)activity).handleDialogClose(dialog);
    }
}

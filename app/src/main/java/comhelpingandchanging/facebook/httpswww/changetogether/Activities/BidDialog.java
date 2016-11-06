package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 26.10.2016.
 */

public class BidDialog extends DialogFragment {

    Button done;
    CheckBox cb_sprachkurs, cb_wohnungsscuhe, cb_freizeit, cb_nachhilfe, cb_orga, cb_sport, cb_kochen, cb_bewerbung, cb_freelancer, cb_sonstiges, cb_job;
    BieteFragment callingFragment;
    ArrayList<String> bieteItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_select_biete, container, false);

        callingFragment = (BieteFragment) getTargetFragment();

        bieteItems = new ArrayList<>();

        cb_bewerbung = (CheckBox) rootView.findViewById(R.id.bewerbung);
        cb_freelancer = (CheckBox) rootView.findViewById(R.id.freelancer);
        cb_freizeit = (CheckBox) rootView.findViewById(R.id.freizeit);
        cb_kochen = (CheckBox) rootView.findViewById(R.id.kochen);
        cb_nachhilfe = (CheckBox) rootView.findViewById(R.id.nachhilfe);
        cb_orga = (CheckBox) rootView.findViewById(R.id.organisatorisches);
        cb_sonstiges = (CheckBox) rootView.findViewById(R.id.sonstiges);
        cb_sport = (CheckBox) rootView.findViewById(R.id.sport);
        cb_sprachkurs = (CheckBox) rootView.findViewById(R.id.sprachkurs);
        cb_wohnungsscuhe = (CheckBox) rootView.findViewById(R.id.wohnungssuche);
        cb_job = (CheckBox) rootView.findViewById((R.id.jobCheckBox));

        done = (Button) rootView.findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addClickedItems();
                for(String s : bieteItems);
                    //((Account) callingFragment.getActivity().getApplication()).addBid(callingFragment, s, "");
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    void addClickedItems(){

        if (cb_bewerbung.isChecked() && !callingFragment.bieteItems.contains(cb_bewerbung.getText().toString()))
            bieteItems.add(cb_bewerbung.getText().toString());

        if (cb_freelancer.isChecked() && !callingFragment.bieteItems.contains(cb_freelancer.getText().toString()))
            bieteItems.add(cb_freelancer.getText().toString());

        if (cb_freizeit.isChecked() && !callingFragment.bieteItems.contains(cb_freizeit.getText().toString()))
            bieteItems.add(cb_freizeit.getText().toString());

        if (cb_kochen.isChecked() && !callingFragment.bieteItems.contains(cb_kochen.getText().toString()))
            bieteItems.add(cb_kochen.getText().toString());

        if (cb_nachhilfe.isChecked() && !callingFragment.bieteItems.contains(cb_nachhilfe.getText().toString()))
            bieteItems.add(cb_nachhilfe.getText().toString());

        if (cb_orga.isChecked() && !callingFragment.bieteItems.contains(cb_orga.getText().toString()))
            bieteItems.add(cb_orga.getText().toString());

        if (cb_sonstiges.isChecked() && !callingFragment.bieteItems.contains(cb_sonstiges.getText().toString()))
            bieteItems.add(cb_sonstiges.getText().toString());

        if (cb_sport.isChecked() && !callingFragment.bieteItems.contains(cb_sport.getText().toString()))
            bieteItems.add(cb_sport.getText().toString());

        if (cb_sprachkurs.isChecked() && !callingFragment.bieteItems.contains(cb_sprachkurs.getText().toString()))
            bieteItems.add(cb_sprachkurs.getText().toString());

        if (cb_wohnungsscuhe.isChecked() && !callingFragment.bieteItems.contains(cb_wohnungsscuhe.getText().toString()))
            bieteItems.add(cb_wohnungsscuhe.getText().toString());

        if(cb_job.isChecked() && !callingFragment.bieteItems.contains(cb_job.getText().toString()))
            bieteItems.add(cb_job.getText().toString());

    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
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
    CheckBox cb_sprachkurs, cb_wohnungsscuhe, cb_freizeit, cb_nachhilfe, cb_orga, cb_sport, cb_kochen, cb_bewerbung, cb_freelancer, cb_sonstiges;
    BieteActivity callingActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_select_biete, container, false);

        callingActivity = (BieteActivity) getActivity();

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

        cb_bewerbung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_bewerbung.isChecked())
                    callingActivity.bieteItems.add(cb_bewerbung.getText().toString());
            }
        });

        cb_freelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_freelancer.isChecked())
                    callingActivity.bieteItems.add(cb_freelancer.getText().toString());
            }
        });

        cb_freizeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_freizeit.isChecked())
                    callingActivity.bieteItems.add(cb_freizeit.getText().toString());
            }
        });

        cb_kochen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_kochen.isChecked())
                    callingActivity.bieteItems.add(cb_kochen.getText().toString());
            }
        });

        cb_nachhilfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_nachhilfe.isChecked())
                    callingActivity.bieteItems.add(cb_nachhilfe.getText().toString());
            }
        });

        cb_orga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_orga.isChecked())
                    callingActivity.bieteItems.add(cb_orga.getText().toString());
            }
        });

        cb_sonstiges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sonstiges.isChecked())
                    callingActivity.bieteItems.add(cb_sonstiges.getText().toString());
            }
        });

        cb_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sport.isChecked())
                    callingActivity.bieteItems.add(cb_sport.getText().toString());
            }
        });

        cb_sprachkurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sprachkurs.isChecked())
                    callingActivity.bieteItems.add(cb_sprachkurs.getText().toString());
            }
        });

        cb_wohnungsscuhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_wohnungsscuhe.isChecked())
                    callingActivity.bieteItems.add(cb_wohnungsscuhe.getText().toString());
            }
        });

        done = (Button) rootView.findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String s : callingActivity.bieteItems)
                    ((Account) callingActivity.getApplication()).addBid(s, "");
                callingActivity.adapter.notifyDataSetChanged();
                getDialog().dismiss();
            }
        });

        return rootView;
    }
}

package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by len13 on 26.10.2016.
 */

public class AddBidActivity extends Activity {

    Button done;
    CheckBox cb_sprachkurs, cb_wohnungsscuhe, cb_freizeit, cb_nachhilfe, cb_orga, cb_sport, cb_kochen, cb_bewerbung, cb_freelancer, cb_sonstiges;
    ArrayList<String> bieteItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_biete);

        cb_bewerbung = (CheckBox) findViewById(R.id.bewerbung);
        cb_freelancer = (CheckBox) findViewById(R.id.freelancer);
        cb_freizeit = (CheckBox) findViewById(R.id.freizeit);
        cb_kochen = (CheckBox) findViewById(R.id.kochen);
        cb_nachhilfe = (CheckBox) findViewById(R.id.nachhilfe);
        cb_orga = (CheckBox) findViewById(R.id.organisatorisches);
        cb_sonstiges  = (CheckBox) findViewById(R.id.sonstiges);
        cb_sport = (CheckBox) findViewById(R.id.sport);
        cb_sprachkurs = (CheckBox) findViewById(R.id.sprachkurs);
        cb_wohnungsscuhe = (CheckBox) findViewById(R.id.wohnungssuche);

        cb_bewerbung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_bewerbung.isChecked())
                    bieteItems.add(cb_bewerbung.getText().toString());
            }
        });

        cb_freelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_freelancer.isChecked())
                    bieteItems.add(cb_freelancer.getText().toString());
            }
        });

        cb_freizeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_freizeit.isChecked())
                    bieteItems.add(cb_freizeit.getText().toString());
            }
        });

        cb_kochen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_kochen.isChecked())
                    bieteItems.add(cb_kochen.getText().toString());
            }
        });

        cb_nachhilfe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_nachhilfe.isChecked())
                    bieteItems.add(cb_nachhilfe.getText().toString());
            }
        });

        cb_orga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_orga.isChecked())
                    bieteItems.add(cb_orga.getText().toString());
            }
        });

        cb_sonstiges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_sonstiges.isChecked())
                    bieteItems.add(cb_sonstiges.getText().toString());
            }
        });

        cb_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_sport.isChecked())
                    bieteItems.add(cb_sport.getText().toString());
            }
        });

        cb_sprachkurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_sprachkurs.isChecked())
                    bieteItems.add(cb_sprachkurs.getText().toString());
            }
        });

        cb_wohnungsscuhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_wohnungsscuhe.isChecked())
                    bieteItems.add(cb_wohnungsscuhe.getText().toString());
            }
        });

        done = (Button) findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent bieteActivityIntent = new Intent(AddBidActivity.this, BieteActivity.class);
                bieteActivityIntent.putExtra("bieteItems", bieteItems);
                startActivity(bieteActivityIntent);
            }
        });


    }


}

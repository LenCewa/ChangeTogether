package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by len13 on 26.10.2016.
 */

public class AddBidActivity extends Activity {

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_biete);

        done = (Button) findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent bieteActivityIntent = new Intent(AddBidActivity.this, BieteActivity.class);
                startActivity(bieteActivityIntent);
            }
        });
    }


}

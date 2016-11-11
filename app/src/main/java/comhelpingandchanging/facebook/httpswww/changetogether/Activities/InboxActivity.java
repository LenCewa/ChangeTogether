package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.Button;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class InboxActivity extends Activity {
    Button menu;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar)).setTitle("PROFILE NAME");
    }
}

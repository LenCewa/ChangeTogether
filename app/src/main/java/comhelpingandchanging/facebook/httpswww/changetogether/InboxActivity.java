package comhelpingandchanging.facebook.httpswww.changetogether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by len13 on 17.10.2016.
 */

public class InboxActivity extends Activity {
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(InboxActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });
    }
}

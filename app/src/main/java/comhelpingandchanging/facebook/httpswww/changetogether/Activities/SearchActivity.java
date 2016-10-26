package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 17.10.2016.
 */

public class SearchActivity extends Activity {
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(SearchActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });
    }

}

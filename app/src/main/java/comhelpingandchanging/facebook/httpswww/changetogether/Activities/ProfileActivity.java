package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.ConnectionManager;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileActivity extends Activity {
    Button menu;
    Button rate;
    Button feedbackButton;
    TextView profileInfo;
    ListView feedbackList;
    ArrayList<String[]> listItems = new ArrayList<String[]>();
    customAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        profileInfo = (TextView) findViewById(R.id.textView4);
        profileInfo.setText(ConnectionManager.getEmail() + " - " + ConnectionManager.getLanguage() + " - " + ConnectionManager.getLanguage());
        feedbackList = (ListView) findViewById(R.id.list);
        adapter = new customAdapter(this, listItems);
                //new ArrayAdapter<String>(this,
                //R.layout.feedback_list_item,
                //listItems);
        feedbackList.setAdapter(adapter);

        feedbackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] arr = (String[]) adapter.getItem(position);
                String profileName = arr[0];
                String feedbackText = arr[1];
                float rating = Float.parseFloat(arr[2]);

                Intent intent = new Intent(ProfileActivity.this, FeedbackActivity.class);
                intent.putExtra("profileName", profileName);
                intent.putExtra("feedbackText", feedbackText);
                intent.putExtra("rating", rating);
                startActivity(intent);
            }
        });

        rate = (Button) findViewById(R.id.rateBtn);
        rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FeedbackDialog dialog = new FeedbackDialog();
                dialog.show(getFragmentManager(), "Dialog Fragment");
            }
        });

        //if(adapter.getCount() == 0)

    }
}

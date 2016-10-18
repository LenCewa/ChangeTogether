package comhelpingandchanging.facebook.httpswww.changetogether;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by len13 on 17.10.2016.
 */

public class ProfileActivity extends Activity {
    Button menu;
    Button feedbackButton;
    ListView feedbackList;
    EditText feedback;
    ArrayList<String> listItems=new ArrayList<String>();
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

        feedbackList = (ListView) findViewById(R.id.list);
        adapter = new customAdapter(this, listItems);
                //new ArrayAdapter<String>(this,
                //R.layout.feedback_list_item,
                //listItems);
        feedbackList.setAdapter(adapter);

        feedback = (EditText) findViewById(R.id.editText);

        feedbackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String feedbackText = (String) adapter.getItem(position);

                Intent intent = new Intent(ProfileActivity.this, FeedbackActivity.class);
                intent.putExtra("feedbackText", feedbackText);
                startActivity(intent);
            }
        });

        feedbackButton = (Button) findViewById(R.id.sendButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Editable feedbackText = feedback.getText();
                listItems.add(0,feedbackText.toString());
                adapter.notifyDataSetChanged();
            }
        });




    }
}

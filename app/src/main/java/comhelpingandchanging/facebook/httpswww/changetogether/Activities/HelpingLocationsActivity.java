package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.PlacesAutoCompleteAdapter;

/**
 * Created by len13 on 29.10.2016.
 */

public class HelpingLocationsActivity extends Activity {
    Button menu;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_locations);

        account = (Account) getApplication();
        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item)); // vorher getActivity() anstelle von this

        menu = (Button) findViewById(R.id.button2);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(HelpingLocationsActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                //Toast.makeText(this, description, Toast.LENGTH_SHORT).show(); // TODO vorher getActivity() anstelle von this
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("onlineStatus", account.getOnlineStatus());
        editor.putString("email", account.getEmail());
        editor.putString("password", account.getPassword());
        editor.commit();
    }
}

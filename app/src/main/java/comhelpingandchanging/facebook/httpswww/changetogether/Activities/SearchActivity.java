package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 17.10.2016.
 */

public class SearchActivity extends Activity {
    Button menu;
    TextView profileInfo;
    EditText searchField;
    Button searchBtn;
    Account account;
    public ListView searches;
    public ArrayList<String[]> listItems = new ArrayList<String[]>();
    public CustomAdapterSearch adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        account = (Account) getApplication();

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(SearchActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        profileInfo = (TextView) findViewById(R.id.textView);
        profileInfo.setText(account.getEmail() + " - " + account.getLocation() + " - " + account.getLanguage());

        searches = (ListView) findViewById(R.id.searchList);
        adapter = new CustomAdapterSearch(this, listItems);
        searches.setAdapter(adapter);

        searchField = (EditText) findViewById(R.id.editText3);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.clear();
                adapter.notifyDataSetChanged();
                account.searchBid(SearchActivity.this, searchField.getText().toString());
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

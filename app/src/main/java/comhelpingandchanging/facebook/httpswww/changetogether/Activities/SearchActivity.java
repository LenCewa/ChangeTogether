package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Constants;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.SearchDB;

/**
 * Created by Yannick on 17.10.2016.
 */

public class SearchActivity extends Activity {
    Button menu;
    TextView profileInfo;
    EditText searchField;
    Button searchBtn;
    Account account;

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

        searchField = (EditText) findViewById(R.id.editText3);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDB s = new SearchDB(SearchActivity.this);
                s.execute(Constants.USERTABLE, Constants.EMAILROW, searchField.getText().toString());
            }
        });
    }
}

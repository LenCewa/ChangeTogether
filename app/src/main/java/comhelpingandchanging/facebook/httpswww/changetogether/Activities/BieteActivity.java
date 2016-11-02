package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 17.10.2016.
 */

public class BieteActivity extends Activity {
    Button menu, add;
    public ArrayList<String> bieteItems = new ArrayList<>();
    ListView bieteList_class;
    public ArrayAdapter<String> adapter;
    TextView profileInfo;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biete);
        account = (Account) getApplication();

        profileInfo = (TextView) findViewById(R.id.textView2);
        profileInfo.setText(account.getEmail() + " - " + account.getLocation() + " - " + account.getLanguage());

        bieteList_class = (ListView) findViewById(R.id.bieteList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bieteItems);
        bieteList_class.setAdapter(adapter);

        account.loadBids(this);

        menu = (Button) findViewById(R.id.menuButton);
        add = (Button) findViewById(R.id.addButton);

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent menuActivity = new Intent(BieteActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BidDialog add = new BidDialog();
                add.show(getFragmentManager(), "Biete Dialog");
            }
        });

        bieteList_class.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                account.deleteBid(adapter.getItem(position), "");
                bieteItems.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}

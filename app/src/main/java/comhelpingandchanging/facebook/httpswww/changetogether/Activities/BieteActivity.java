package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by len13 on 17.10.2016.
 */

public class BieteActivity extends Activity {
    Button menu, add;
    ArrayList<String> bieteItems = new ArrayList<>();
    String[] itemsForListView;
    ListView bieteList_class;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biete);

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

        bieteList_class = (ListView) findViewById(R.id.bieteList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bieteItems);
        bieteList_class.setAdapter(adapter);
    }
}

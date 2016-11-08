package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Ludwig on 05.11.2016.
 */

public class ShowBidFeedback extends Activity implements MyDialogCloseListener{

    TextView bid;
    FloatingActionButton rateBtn;
    ListView feedbackList;
    RatingBar ratingBar;
    public CustomAdapter adapter;
    public ArrayList<String[]> feedbacks = new ArrayList<>();
    Account account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bid_feedback);

        account = (Account) getApplication();

        bid = (TextView) findViewById(R.id.bidType);
        rateBtn = (FloatingActionButton) findViewById(R.id.rateBtn);
        feedbackList = (ListView) findViewById(R.id.listFeedback);
        ratingBar = (RatingBar) findViewById(R.id.avergageRating);

        bid.setText(getIntent().getStringExtra("tag"));
        adapter = new CustomAdapter(this, feedbacks);
        feedbackList.setAdapter(adapter);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackDialog add = new FeedbackDialog();
                add.setArguments(new Bundle());
                add.getArguments().putInt("id", getIntent().getIntExtra("id", -1));
                add.getArguments().putString("tag", getIntent().getStringExtra("tag"));
                add.show(getFragmentManager(), "Feedback Dialog");
            }
        });

        account.searchFeedback(this, getIntent().getIntExtra("id", -1), getIntent().getStringExtra("tag"));
    }

    public void setStars(float stars){
        ratingBar.setRating(stars);
    }

    private void refresh(){
        feedbacks.clear();
        account.searchFeedback(this, getIntent().getIntExtra("id", -1), getIntent().getStringExtra("tag"));
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        refresh();
    }
}

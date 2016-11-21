package app.radiant.c.lly.Activities;

import android.support.v4.app.FragmentActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.radiant.c.lly.Adapter.CustomAdapter;
import app.radiant.c.lly.DialogFragments.FeedbackDialog;
import app.radiant.c.lly.DialogFragments.MyDialogCloseListener;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Ludwig on 05.11.2016.
 */

public class ShowBidFeedbackActivity extends FragmentActivity implements MyDialogCloseListener {

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

        bid.setText(account.getSearchTag());
        adapter = new CustomAdapter(this, feedbacks);
        feedbackList.setAdapter(adapter);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackDialog add = new FeedbackDialog();
                add.setArguments(new Bundle());
                add.getArguments().putInt("id", Integer.parseInt(account.getSearchID()));
                add.getArguments().putString("tag", account.getSearchTag());
                add.show(getSupportFragmentManager(), "Feedback Dialog");
            }
        });
        account.searchFeedback(this, Integer.parseInt(account.getSearchID()), account.getSearchTag());
    }

    public void setStars(double stars){
        ratingBar.setRating((float)stars);
    }

    private void refresh(){
        feedbacks.clear();
        account.searchFeedback(this, Integer.parseInt(account.getSearchID()), account.getSearchTag());
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {
        refresh();
    }
}

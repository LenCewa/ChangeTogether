package comhelpingandchanging.facebook.httpswww.changetogether;

import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * Created by Yannick on 18.10.2016.
 */

public class FeedbackDialog extends DialogFragment {

    EditText feedback;
    RatingBar ratingBar;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.dialog_feedback, container, false);
        getDialog().setTitle("Share your feedback");

        feedback = (EditText) rootView.findViewById(R.id.feedbackText);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);

        submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                Editable feedbackText = feedback.getText();

                ProfileActivity callingActivity = (ProfileActivity) getActivity();
                callingActivity.listItems.add(0,new String[]{"Len Williamson", feedbackText.toString(), String.valueOf(rating)});
                callingActivity.adapter.notifyDataSetChanged();
                getDialog().dismiss();
            }
        });

        return rootView;
    }
}

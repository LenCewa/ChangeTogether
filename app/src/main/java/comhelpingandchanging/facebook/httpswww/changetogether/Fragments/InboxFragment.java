package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by len13 on 11.11.2016.
 */

public class InboxFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;

    public InboxFragment() {
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_inbox, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void refresh(){
        // TODO
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

}

package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;
//import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by len13 on 11.11.2016.
 *
 * https://codelabs.developers.google.com/codelabs/firebase-android/#0
 */

public class InboxFragment extends Fragment /*implements GoogleApiClient.OnConnectionFailedListener */{

    /*public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }*/

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
    /*
    // Interface...
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }*/

}

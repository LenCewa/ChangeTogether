package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import comhelpingandchanging.facebook.httpswww.changetogether.Activities.SignInActivity;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

//Tutorial Impoprts

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.bumptech.glide.Glide;

import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.FriendlyMessage;
import de.hdodenhof.circleimageview.CircleImageView;

import static de.hdodenhof.circleimageview.R.styleable.CircleImageView;

/**
 * Created by len13 on 11.11.2016.
 *
 * https://codelabs.developers.google.com/codelabs/firebase-android/#7
 * https://github.com/firebase/friendlychat
 * https://console.firebase.google.com/
 */

public class InboxFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }

    private static final String TAG = "InboxFragment";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseAdapter;


    // My classical declarations
    View view;
    Activity callingActivity;
    Account account;

    public InboxFragment() {
        setArguments(new Bundle());
    }


    // Firebase instance variables

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_inbox, container, false);
        callingActivity = getActivity();
        //account = (Account) callingActivity.getApplication();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUsername = ANONYMOUS;

        // Inititalize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(getActivity(), SignInActivity.class));
            callingActivity.finish();
            return view; // Todo mögl. error!
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API).build(); // Todo vs onStart

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        // mProgressBar.setVisibility(ProgressBar.INVISIBLE); Replaced by:
        //New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
                FriendlyMessage.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage model, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.messageTextView.setText(model.getText());
                viewHolder.messengerTextView.setText(model.getName());
                if (model.getPhotoUrl() == null) {
                    viewHolder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(getActivity() /*.this*/,
                            R.drawable.ic_action_arrow_left /*.ic_account_circle_black_36dp*/));
                } else {
                    Glide.with(getActivity() /*.this*/).load(model.getPhotoUrl()).into(viewHolder.messengerImageView);
                }
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
           @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
               super.onItemRangeInserted(positionStart, itemCount);
               int friendlyMessageCount = mFirebaseAdapter.getItemCount();
               int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

               // If the recycler view is initially being loaded or the
               // user is at the bottom of the list, scroll to the bottom
               // of the list to show the newly added message.
               if (lastVisiblePosition == -1 ||
                       (positionStart >= (friendlyMessageCount - 1) &&
                               lastVisiblePosition == (positionStart - 1))) {
                   mMessageRecyclerView.scrollToPosition(positionStart);
               }
           }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(mSharedPreferences.getInt("friendly_msg_length", DEFAULT_MSG_LENGTH_LIMIT))});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSendButton = (Button) view.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send messages on click.
            }
        });

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

    @Override
    public void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Egal=?
    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = callingActivity.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(getActivity(), SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Interface...
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        //Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

}

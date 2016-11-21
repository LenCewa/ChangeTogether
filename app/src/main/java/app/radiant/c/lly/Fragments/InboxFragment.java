package app.radiant.c.lly.Fragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

import app.radiant.c.lly.Activities.ChatActivity;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;


/**
 * Created by len13 on 11.11.2016.
 *
 * https://www.firebase.com/docs/android/quickstart.html
 */

public class InboxFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;
    ListView chats;

    ArrayList<String> exampleContent = new ArrayList<>();
    ArrayAdapter<String> adapter;

    // Chat instance variables
    private FirebaseAuth userAuth;
    private FirebaseUser user;
    String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Firebase.setAndroidContext(getActivity());
        view = inflater.inflate(R.layout.fragment_inbox, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();

        // My Firebaseapp
        Firebase myFirebaseRef = new Firebase("https://change-together-149218.firebaseio.com/");

        // Write Test Data to Firebase
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");

        // Reading the Data but check the authorization rules http://stackoverflow.com/questions/37477644/firebase-permission-denied-error
        myFirebaseRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("DB Test ", snapshot.getValue().toString());  //prints "Do you have data? You'll love Firebase."
            }
            @Override public void onCancelled(FirebaseError error) { }
        });

        // Code
        chats = (ListView) view.findViewById(R.id.userChatList);

        // Add example content to array
        exampleContent.add("Chat 1");
        exampleContent.add("Chat 2");
        //exampleContent.add(username);

        adapter = new ArrayAdapter<String>(callingActivity, android.R.layout.simple_list_item_1, exampleContent);
        chats.setAdapter(adapter);

        chats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ChatActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

}

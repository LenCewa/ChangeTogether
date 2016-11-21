package app.radiant.c.lly.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import app.radiant.c.lly.Activities.ChatActivity;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;


/**
 * Created by len13 on 11.11.2016.
 *
 * https://codelabs.developers.google.com/codelabs/firebase-android/#0
 * http://www.androidhive.info/2016/02/android-push-notifications-using-gcm-php-mysql-realtime-chat-app-part-1/
 */

public class InboxFragment extends Fragment {

    View view;
    Activity callingActivity;
    Account account;
    ListView chats;

    ArrayList<String> exampleContent = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inbox, container, false);
        callingActivity = getActivity();
        account = (Account) callingActivity.getApplication();

        chats = (ListView) view.findViewById(R.id.userChatList);

        // Add example content to array
        exampleContent.add("Chat 1");
        exampleContent.add("Chat 2");

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

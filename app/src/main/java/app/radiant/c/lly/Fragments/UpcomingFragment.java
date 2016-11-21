package app.radiant.c.lly.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

import app.radiant.c.lly.Adapter.CustomRecyclerViewAdapterUpcoming;
import app.radiant.c.lly.Adapter.RecyclerItemClickListener;
import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Yannick on 20.11.2016.
 */

public class UpcomingFragment extends SuperProfileFragment {

    TextView location;
    TextView language;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        account = (Account) getActivity().getApplication();

        location = (TextView) view.findViewById(R.id.ownProfileLocation);
        language = (TextView) view.findViewById(R.id.ownProfileLanguage);

        cmp = new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                String date1 = o1[8];
                String date2 = o2[8];
                String time1 = o1[9];
                String time2 = o2[9];

                String[] d1 = date1.split(Pattern.quote("/"));
                String[] d2 = date2.split(Pattern.quote("/"));
                String[] t1 = time1.split(Pattern.quote(":"));
                String[] t2 = time2.split(Pattern.quote(":"));

                int day1 = Integer.parseInt(d1[0]);
                int month1 = Integer.parseInt(d1[1]);
                int year1 = Integer.parseInt(d1[2]);
                int hours1 = Integer.parseInt(t1[0]);
                int minutes1 = Integer.parseInt(t1[1]);

                int day2 = Integer.parseInt(d2[0]);
                int month2 = Integer.parseInt(d2[1]);
                int year2 = Integer.parseInt(d2[2]);
                int hours2 = Integer.parseInt(t2[0]);
                int minutes2 = Integer.parseInt(t2[1]);

                if(year1 < year2)
                    return -1;
                else if(year1 > year2)
                    return 1;
                else {
                    if(month1 < month2)
                        return -1;
                    else if(month1 > month2)
                        return 1;
                    else {
                        if(day1 < day2)
                            return -1;
                        else if(day1 > day2)
                            return 1;
                        else {
                            if(hours1 < hours2)
                                return -1;
                            else if(hours1 > hours2)
                                return 1;
                            else {
                                if(minutes1 < minutes2)
                                    return -1;
                                else if(minutes1 > minutes2)
                                    return 1;
                                else
                                    return 0;
                            }
                        }
                    }
                }
            }
        };

        Collections.sort(account.getParticipations(), cmp);

        adapter = new CustomRecyclerViewAdapterUpcoming(getActivity(), account.getParticipations());
        bidList = (RecyclerView) view.findViewById(R.id.cardList);
        bidList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        bidList.setLayoutManager(llm);
        bidList.setAdapter(adapter);

        bidList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        CustomRecyclerViewAdapterUpcoming adapter = (CustomRecyclerViewAdapterUpcoming) UpcomingFragment.this.adapter;

                        String id = adapter.getItem(position)[0];
                        String email = adapter.getItem(position)[1];
                        String tag = adapter.getItem(position)[2];
                        String description = adapter.getItem(position)[3];
                        String location = adapter.getItem(position)[4];
                        String averageRating = adapter.getItem(position)[5];
                        String count = adapter.getItem(position)[6];
                        String distance = adapter.getItem(position)[7];
                        String date = adapter.getItem(position)[8];
                        String time = adapter.getItem(position)[9];
                        String part = adapter.getItem(position)[10];
                        String maxPart = adapter.getItem(position)[11];
                        String encodedPic = adapter.getItem(position)[12];

                        if(encodedPic.length() != 0) {
                            byte[] decodedString = Base64.decode(encodedPic, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            account.setSearchedUserProfilePic(decodedByte);
                        }
                        else{
                            Bitmap bitmap = BitmapFactory.decodeResource(UpcomingFragment.this.getResources(),
                                    R.drawable.blank_profile_pic);
                            account.setSearchedUserProfilePic(bitmap);
                        }

                        account.setSearchedItem(getActivity(), id, email, tag, description, location, averageRating, count, distance, date, time, part, maxPart, encodedPic);
                        SearchItemFragment f = new SearchItemFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f, "searchItem").addToBackStack(null).commit();
                    }
                })
        );

        refresh();

        return view;
    }

    private void refresh() {

        location.setText(account.getLocation());
        language.setText(account.getLanguage());
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}

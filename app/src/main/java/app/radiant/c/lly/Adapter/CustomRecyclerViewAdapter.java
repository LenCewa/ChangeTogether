package app.radiant.c.lly.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.radiant.c.lly.R;
import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Yannick on 10.11.2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ProfileInfoViewHolder> {

    Account account;
    ArrayList<String[]> data;
    ProfileInfoViewHolder holder;
    ViewGroup parent;

    public CustomRecyclerViewAdapter(Activity activity, ArrayList<String[]> data) {

        this.account = (Account)activity.getApplication();
        this.data = data;
    }

    @Override
    public CustomRecyclerViewAdapter.ProfileInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.profile_list_item, parent, false);
        holder = new ProfileInfoViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileInfoViewHolder holder, int position) {

        holder.tag.setText(data.get(position)[2]);
        holder.location.setText(data.get(position)[4]);
        holder.distance.setText("<=" + data.get(position)[7] + "km");
        holder.time.setText(data.get(position)[8] + " - " + data.get(position)[9] + " Uhr");
        holder.ratingBar.setRating(Float.parseFloat(data.get(position)[5]));
        holder.count.setText(data.get(position)[6] + " Bewertungen");
        holder.maxPart.setText(data.get(position)[10] + "/" + data.get(position)[11]);
        holder.profilePic.setImageBitmap(account.getSearchProfilePic());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public String[] getItem(int position){
        
        return data.get(position);
    }

    public class ProfileInfoViewHolder extends RecyclerView.ViewHolder{

        TextView tag;
        TextView location;
        TextView distance;
        TextView time;
        ImageView profilePic;
        RatingBar ratingBar;
        TextView count;
        TextView maxPart;

        public ProfileInfoViewHolder(View vi) {

            super(vi);
            tag = (TextView) vi.findViewById(R.id.tag);
            location = (TextView) vi.findViewById(R.id.location);
            distance = (TextView) vi.findViewById(R.id.distance);
            time = (TextView) vi.findViewById(R.id.time);
            profilePic = (ImageView) vi.findViewById(R.id.profilePic);
            ratingBar = (RatingBar) vi.findViewById(R.id.ratingBar4);
            count = (TextView) vi.findViewById(R.id.count);
            maxPart = (TextView) vi.findViewById(R.id.maxPart);
        }
    }
}

package app.radiant.c.lly.Adapter;

import android.support.v4.app.Fragment;
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

public class CustomRecyclerViewAdapterOwnProfile extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Account account;
    Fragment context;
    ArrayList<String[]> data;
    CustomRecyclerViewAdapterOwnProfile.ProfileHeaderViewHolder headerHolder;
    CustomRecyclerViewAdapterOwnProfile.ProfileInfoViewHolder infoHolder;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    public CustomRecyclerViewAdapterOwnProfile(Fragment context, ArrayList<String[]> data) {

        account = (Account) context.getActivity().getApplication();
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.own_profile_list_item, parent, false);
            infoHolder = new CustomRecyclerViewAdapterOwnProfile.ProfileInfoViewHolder(itemView);
            return infoHolder;
        } else{
            //inflate your layout and pass it to view holder
            View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.profile_list_header, parent, false);
            headerHolder = new CustomRecyclerViewAdapterOwnProfile.ProfileHeaderViewHolder(itemView);
            return headerHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        if (viewHolder instanceof ProfileInfoViewHolder) {
            ProfileInfoViewHolder holder = (ProfileInfoViewHolder) viewHolder;
            int position = pos - 1;

            holder.tag.setText(data.get(position)[2]);
            holder.location.setText(data.get(position)[4]);
            holder.time.setText(data.get(position)[7] + " - " + data.get(position)[8] + " Uhr");
            holder.ratingBar.setRating(Float.parseFloat(data.get(position)[5]));
            holder.count.setText(data.get(position)[6] + " Bewertungen");
            holder.maxPart.setText(data.get(position)[9] + "/" + data.get(position)[10]);
            holder.profilePic.setImageBitmap(account.getSelf().getProfilePic());
        }
        else if (viewHolder instanceof ProfileHeaderViewHolder) {
            ProfileHeaderViewHolder holder = (ProfileHeaderViewHolder) viewHolder;

            holder.location.setText(account.getSelf().getLocation());
            holder.language.setText(account.getSelf().getLanguage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public String[] getItem(int position){
        
        return data.get(position - 1);
    }

    public class ProfileInfoViewHolder extends RecyclerView.ViewHolder{

        TextView tag;
        TextView location;
        TextView time;
        ImageView profilePic;
        RatingBar ratingBar;
        TextView count;
        TextView maxPart;

        public ProfileInfoViewHolder(View vi) {

            super(vi);
            tag = (TextView) vi.findViewById(R.id.tag);
            location = (TextView) vi.findViewById(R.id.location);
            time = (TextView) vi.findViewById(R.id.time);
            profilePic = (ImageView) vi.findViewById(R.id.profilePic);
            ratingBar = (RatingBar) vi.findViewById(R.id.ratingBar4);
            count = (TextView) vi.findViewById(R.id.count);
            maxPart = (TextView) vi.findViewById(R.id.maxPart);
        }
    }

    public class ProfileHeaderViewHolder extends RecyclerView.ViewHolder{

        TextView location;
        TextView language;

        public ProfileHeaderViewHolder(View vi){

            super(vi);
            location = (TextView) vi.findViewById(R.id.ownProfileLocation);
            language = (TextView) vi.findViewById(R.id.ownProfileLanguage);
        }
    }
}

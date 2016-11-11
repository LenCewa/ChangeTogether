package comhelpingandchanging.facebook.httpswww.changetogether.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 10.11.2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ProfileInfoViewHolder> {

    ArrayList<String[]> data;
    ProfileInfoViewHolder holder;

    public CustomRecyclerViewAdapter(ArrayList<String[]> data) {
        this.data = data;
    }

    @Override
    public CustomRecyclerViewAdapter.ProfileInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.profile_list_item, parent, false);
        holder = new ProfileInfoViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileInfoViewHolder holder, int position) {

        holder.tag.setText(data.get(position)[1]);
        holder.description.setText(data.get(position)[2]);
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
        TextView description;

        public ProfileInfoViewHolder(View itemView) {
            super(itemView);
            tag = (TextView) itemView.findViewById(R.id.tag);
            description = (TextView) itemView.findViewById(R.id.ownProfileLanguage);
        }
    }
}

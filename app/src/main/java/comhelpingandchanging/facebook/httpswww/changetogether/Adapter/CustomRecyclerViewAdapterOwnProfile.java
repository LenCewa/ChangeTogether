package comhelpingandchanging.facebook.httpswww.changetogether.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 10.11.2016.
 */

public class CustomRecyclerViewAdapterOwnProfile extends RecyclerView.Adapter<CustomRecyclerViewAdapterOwnProfile.ProfileInfoViewHolder> {

    ArrayList<String[]> data;
    ProfileInfoViewHolder holder;
    ViewGroup parent;

    public CustomRecyclerViewAdapterOwnProfile(ArrayList<String[]> data) {
        this.data = data;
    }

    @Override
    public CustomRecyclerViewAdapterOwnProfile.ProfileInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.parent = parent;
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.own_profile_list_item, parent, false);
        holder = new ProfileInfoViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProfileInfoViewHolder holder, int position) {

        if(data.get(position)[12].length() != 0) {
            byte[] decodedString = Base64.decode(data.get(position)[12], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.profilePic.setImageBitmap(decodedByte);
        }
        else{
            Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(),
                    R.drawable.blank_profile_pic);
            holder.profilePic.setImageBitmap(bitmap);
        }

        holder.tag.setText(data.get(position)[2]);
        holder.location.setText(data.get(position)[4]);
        holder.time.setText(data.get(position)[8] + " - " + data.get(position)[9]);
        holder.ratingBar.setRating(Float.parseFloat(data.get(position)[5]));
        holder.count.setText(data.get(position)[6] + " Bewertungen");
        holder.maxPart.setText(data.get(position)[10] + "/" + data.get(position)[11]);
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
}

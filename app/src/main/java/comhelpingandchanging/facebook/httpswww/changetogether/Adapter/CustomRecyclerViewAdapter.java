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

        if(data.get(position)[11].length() != 0) {
            byte[] decodedString = Base64.decode(data.get(position)[11], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.profilePic.setImageBitmap(decodedByte);
        }

        holder.tag.setText(data.get(position)[2]);
        holder.location.setText(data.get(position)[4]);
        holder.distance.setText("<=" + data.get(position)[7] + "km");
        holder.time.setText(data.get(position)[8] + " - " + data.get(position)[9]);
        holder.ratingBar.setRating(Float.parseFloat(data.get(position)[5]));
        holder.count.setText(data.get(position)[6] + " Bewertungen");
        holder.maxPart.setText("0/" + data.get(position)[10]);
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

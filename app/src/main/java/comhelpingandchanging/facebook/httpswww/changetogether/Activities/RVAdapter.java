package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 08.11.2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    ArrayList<String[]> data;

    RVAdapter(ArrayList<String[]>  data){
        this.data = data;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_row, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        holder.personName.setText(data.get(position)[0]);
        holder.personAge.setText(data.get(position)[1]);
        holder.personPhoto.setText(data.get(position)[3]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        TextView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.searchTag2);
            personAge = (TextView)itemView.findViewById(R.id.profileNameSearch2);
            personPhoto = (TextView)itemView.findViewById(R.id.searchLocation2);
        }
    }

}

package comhelpingandchanging.facebook.httpswww.changetogether.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.R;

/**
 * Created by Yannick on 18.10.2016.
 */

public class CustomAdapterProfile extends BaseAdapter {
    Context context;
    ArrayList<String[]> data;
    private static LayoutInflater inflater = null;

    public CustomAdapterProfile(Context context, ArrayList<String[]> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.profile_list_item, null);

        TextView tag = (TextView) vi.findViewById(R.id.tag);
        TextView description = (TextView) vi.findViewById(R.id.description);

        tag.setText(data.get(position)[2]);
        description.setText(data.get(position)[3]);

        return vi;
    }
}

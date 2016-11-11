package comhelpingandchanging.facebook.httpswww.changetogether.Fragments;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomRecyclerViewAdapter;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

/**
 * Created by Yannick on 10.11.2016.
 */

public class SuperProfileFragment extends Fragment {

    View view;
    RecyclerView bidList;
    public ArrayList<String[]> bieteItems = new ArrayList<String[]>();
    public CustomRecyclerViewAdapter adapter;
    Account account;
}

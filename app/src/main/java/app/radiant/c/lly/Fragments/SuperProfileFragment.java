package app.radiant.c.lly.Fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;

import app.radiant.c.lly.Utilities.Account;

/**
 * Created by Yannick on 10.11.2016.
 */

public class SuperProfileFragment extends Fragment {

    View view;
    RecyclerView bidList;
    public ArrayList<String[]> bieteItems = new ArrayList<String[]>();
    public RecyclerView.Adapter adapter;
    public Comparator<String[]> cmp;
    Account account;
}

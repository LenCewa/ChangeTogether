package app.radiant.c.lly.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.radiant.c.lly.Fragments.OwnBidsFragment;
import app.radiant.c.lly.Fragments.UpcomingFragment;

/**
 * Created by Yannick on 24.11.2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    String[] tabs;

    public ViewPagerAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new OwnBidsFragment().newInstance();
            case 1:
                return new UpcomingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
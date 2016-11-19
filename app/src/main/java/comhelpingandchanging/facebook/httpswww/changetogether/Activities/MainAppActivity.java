package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comhelpingandchanging.facebook.httpswww.changetogether.Adapter.CustomAdapterSearch;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.BieteFragment;
//import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.HelpingLocationsFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.HomeFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.InboxFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.OwnProfileFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.Fragments.SearchFragment;
import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Account account;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    BieteFragment bieteFragment;
    OwnProfileFragment ownProfileFragment;
    //HelpingLocationsFragment helpingLocationsFragment;
    InboxFragment inboxFragment;
    View header;

    public NavigationView navigationView;
    AppBarLayout appBarLayout;

    public ListView searches;
    public ArrayList<String[]> listItems = new ArrayList<String[]>();
    public CustomAdapterSearch adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        account = (Account) getApplication();

        if (savedInstanceState != null) {
            searchFragment = (SearchFragment) getFragmentManager().getFragment(savedInstanceState, "search");
            bieteFragment = (BieteFragment) getFragmentManager().getFragment(savedInstanceState, "biete");
            ownProfileFragment = (OwnProfileFragment) getFragmentManager().getFragment(savedInstanceState, "ownprofile");
            inboxFragment = (InboxFragment) getFragmentManager().getFragment(savedInstanceState, "inbox");
        }
        else {
            homeFragment = new HomeFragment();
            searchFragment = new SearchFragment();
            bieteFragment = new BieteFragment();
            ownProfileFragment = new OwnProfileFragment();
          //  helpingLocationsFragment = new HelpingLocationsFragment();
            inboxFragment = new InboxFragment();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        header = navigationView.getHeaderView(0);
        ((ImageView) header.findViewById(R.id.profPic)).setImageBitmap(account.getProfilePic());
        ((TextView) header.findViewById(R.id.profEmail)).setText(account.getEmail());
        ((TextView) header.findViewById(R.id.profLocation)).setText(account.getLocation());

        getFragmentManager().beginTransaction().replace(R.id.content_frame, homeFragment, "home").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if(id == R.id.nav_home){
            fragmentManager.beginTransaction().replace(R.id.content_frame, homeFragment, "home").addToBackStack(null).commit();
        } else if (id == R.id.nav_search) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, searchFragment, "search").addToBackStack(null).commit();
        } else if (id == R.id.nav_biete) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, bieteFragment, "biete").addToBackStack(null).commit();
        } else if (id == R.id.nav_own_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, ownProfileFragment, "ownprofile").addToBackStack(null).commit();
        //} else if (id == R.id.nav_helping) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame, helpingLocationsFragment, "helping").addToBackStack(null).commit();
        } else if (id == R.id.nav_inbox) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, inboxFragment, "helping").addToBackStack(null).commit();
        }
        else if (id == R.id.nav_logout){
            account.logout(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Double[] getLocationFromAddress(String strAddress){

        Double[] latLong = new Double[2];
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,1);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            latLong[0] = location.getLatitude();
            latLong[1] = location.getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLong;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("hallo", "hallo");
        ((ImageView) header.findViewById(R.id.profPic)).setImageBitmap(account.getProfilePic());
        ((TextView) header.findViewById(R.id.profEmail)).setText(account.getEmail());
        ((TextView) header.findViewById(R.id.profLocation)).setText(account.getLocation());
    }
}

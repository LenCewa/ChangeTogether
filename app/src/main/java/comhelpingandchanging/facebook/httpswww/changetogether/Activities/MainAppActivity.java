package comhelpingandchanging.facebook.httpswww.changetogether.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import comhelpingandchanging.facebook.httpswww.changetogether.R;
import comhelpingandchanging.facebook.httpswww.changetogether.Utilities.Account;

public class MainAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Account account;
    SearchFragment searchFragment;
    BieteFragment bieteFragment;
    OwnProfileFragment ownProfileFragment;
    HelpingLocationsFragment helpingLocationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        account = (Account) getApplication();
        if (savedInstanceState != null) {
            Log.e("Null","NULL");
            searchFragment = (SearchFragment) getFragmentManager().getFragment(savedInstanceState, "search");
            bieteFragment = (BieteFragment) getFragmentManager().getFragment(savedInstanceState, "biete");
            ownProfileFragment = (OwnProfileFragment) getFragmentManager().getFragment(savedInstanceState, "ownprofile");
        }
        else {
            Log.e("not null","not null");
            searchFragment = new SearchFragment();
            bieteFragment = new BieteFragment();
            ownProfileFragment = new OwnProfileFragment();
            helpingLocationsFragment = new HelpingLocationsFragment();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            account.logout();
            Intent main = new Intent(MainAppActivity.this, MainActivity.class);
            startActivity(main);
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_search) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, searchFragment, "search").commit();
        } else if (id == R.id.nav_biete) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, bieteFragment, "biete").commit();
        } else if (id == R.id.nav_own_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, ownProfileFragment, "ownprofile").commit();
        } else if (id == R.id.nav_helping) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, helpingLocationsFragment, "helping").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("login_state", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(sp.getBoolean("stayLoggedIn", false)) {
            editor.putString("email", account.getEmail());
            editor.putString("password", account.getPassword());
            editor.commit();
        }
    }
}

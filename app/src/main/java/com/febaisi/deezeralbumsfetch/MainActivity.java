package com.febaisi.deezeralbumsfetch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.febaisi.deezeralbumsfetch.fragments.AboutFragment;
import com.febaisi.deezeralbumsfetch.fragments.AlbumsFragment;
import com.febaisi.deezeralbumsfetch.fragments.ConfigFragment;
import com.febaisi.deezeralbumsfetch.fragments.CustomFragment;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_config:
                    manageFragment(new ConfigFragment());
                    return true;
                case R.id.navigation_albums:
                    manageFragment(new AlbumsFragment());
                    return true;
                case R.id.navigation_about:
                    manageFragment(new AboutFragment());
                    return true;
            }
            return false;
        }
    };

    private void setupActionBar() {
        ActionBar.LayoutParams lp1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View customNav = LayoutInflater.from(this).inflate(R.layout.action_bar, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setCustomView(customNav, lp1);
    }

    protected void manageFragment(CustomFragment fragment) {
        if (findViewById(R.id.fragment_container) != null) {

            //Prevent replace a fragment that is already on the top of the screen
            if (getSupportFragmentManager().findFragmentByTag(fragment.getCustomTag()) == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.getCustomTag()).commit();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        FetchAlbumsController fetchAlbumsController = new FetchAlbumsController(this);
//        fetchAlbumsController.fetchAlbums();

        // If we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState == null) {
            manageFragment(new ConfigFragment());
        }
    }
}
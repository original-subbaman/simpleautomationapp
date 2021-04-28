package com.mact.simpleautomationapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.mact.simpleautomationapp.Fragments.AndroidAutoFragment;
import com.mact.simpleautomationapp.Fragments.IOTAutoFragment;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;
import com.mact.simpleautomationapp.Services.BroadcastManager;
import com.mact.simpleautomationapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mBinding;
    private static BroadcastManager mBroadcastManager;
    private boolean isMenuOpen = false;
    private OvershootInterpolator interpolator = new OvershootInterpolator();
    private AutomatedTaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);

        setUpUIComponents(savedInstanceState);

        mBroadcastManager = new BroadcastManager(this);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(AutomatedTaskViewModel.class);

    }

    @Override
    public void onBackPressed() {
        if(mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    private void setUpUIComponents(Bundle savedInstanceState) {
        setUpSideNavigationDrawer();
        if(savedInstanceState == null){
            switchFragment(R.id.nav_bar_android);
        }
        setUpFAB();
        setSubTitleText(getResources().getString(R.string.subtitle_android));

    }

    private void setUpSideNavigationDrawer(){
        mBinding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setSubTitleText(String title){
        mBinding.subtitleText.setText(title);
    }

    private void setUpFAB() {
        mBinding.createAndroidAutoFab.setAlpha(0f);
        mBinding.createIotAutoFab.setAlpha(0f);

        mBinding.createAndroidAutoFab.setTranslationY(100f);
        mBinding.createIotAutoFab.setTranslationY(100f);

        mBinding.createAndroidAutoFab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AutomateAndroid.class);
            startActivity(intent);
        });

        mBinding.createIotAutoFab.setOnClickListener(v -> {

        });

        mBinding.fab.setOnClickListener(v -> {
            if(isMenuOpen){
                closeFabMenu();
            }else{
                openFabMenu();
            }
        });
    }

    private void openFabMenu(){
        isMenuOpen = !isMenuOpen;

        mBinding.fab.animate().setInterpolator(interpolator)
                .rotation(45f)
                .setDuration(300)
                .start();
        mBinding.createAndroidAutoFab.animate().translationY(0f)
                .alpha(1f)
                .setInterpolator(interpolator)
                .setDuration(300)
                .start();
        mBinding.createIotAutoFab.animate().translationY(0f)
                .alpha(1f)
                .setInterpolator(interpolator)
                .setDuration(300)
                .start();
    }

    private void closeFabMenu(){
        isMenuOpen = !isMenuOpen;

        mBinding.fab.animate().setInterpolator(interpolator)
                .rotation(0f)
                .setDuration(300)
                .start();

        mBinding.createAndroidAutoFab.animate().translationY(100f)
                .alpha(0f)
                .setInterpolator(interpolator)
                .setDuration(300)
                .start();

        mBinding.createIotAutoFab.animate().translationY(100f)
                .alpha(0f)
                .setInterpolator(interpolator)
                .setDuration(300)
                .start();

    }

    private boolean switchFragment(int itemId){
        switch(itemId){
            case R.id.nav_bar_android:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_tag, AndroidAutoFragment.class, null, "ANDROID")
                        .commit();
                setSubTitleText(getResources().getString(R.string.subtitle_android));
                break;
            case R.id.nav_bar_iot:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_tag, IOTAutoFragment.class, null, "IOT")
                        .commit();
                setSubTitleText(getResources().getString(R.string.subtitle_iot));
                break;
            default:
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        androidx.appcompat.widget.SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_search:
                Toast.makeText(MainActivity.this, "Autos Setting clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return switchFragment(item.getItemId());
    }
}
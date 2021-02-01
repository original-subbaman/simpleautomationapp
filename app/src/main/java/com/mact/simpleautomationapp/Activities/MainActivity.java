package com.mact.simpleautomationapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mact.simpleautomationapp.Fragments.AndroidAutoFragment;
import com.mact.simpleautomationapp.Fragments.IOTAutoFragment;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Utils.ViewAnimation;
import com.mact.simpleautomationapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private boolean isFABRotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        setUpUIComponents();
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private void setUpUIComponents() {
        setUpBottomAppBar();
        setUpFAB();
    }

    private void setUpFAB() {
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFABRotate = ViewAnimation.rotateFAB(v, !isFABRotate);
                showSelectAutoTypeDialog();
            }
        });
    }

    private void showSelectAutoTypeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_select_auto, null);
        builder.setView(view)
        .create()
        .show();

    }

    private void setUpBottomAppBar() {
        setSupportActionBar(mBinding.bottomAppBar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_setting:
                Toast.makeText(MainActivity.this, "Autos Setting clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_bar_android:
                mBinding.toolbar.setTitle("Android Auto");
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in,
                                android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, AndroidAutoFragment.class, null)
                        .commit();
                break;
            case R.id.app_bar_iot:
                mBinding.toolbar.setTitle("IOT Auto");
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.animator.fade_in, android.R.animator.fade_out)
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view_tag, IOTAutoFragment.class, null)
                        .commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
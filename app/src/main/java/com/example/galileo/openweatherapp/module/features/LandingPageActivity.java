package com.example.galileo.openweatherapp.module.features;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.galileo.openweatherapp.R;
import com.example.galileo.openweatherapp.app.BaseActivity;

import java.io.IOException;

public class LandingPageActivity extends BaseActivity {

    ListFragment listFragment;
    FragmentManager manager;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_landing_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        listFragment = ListFragment.getInstance();

        try {
            initFragment(listFragment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initFragment(Fragment fragment) throws IOException {

        if(manager == null) manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            if(transaction != null) transaction.addToBackStack(null);
            transaction.replace(R.id.fragContainer, fragment);
        }
        transaction.commit();

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit ", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

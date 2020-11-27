package com.example.eko12rpl022018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new fragmenthome()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                /*case R.id.nav_home:
                    selectedFragment = new fragmenthome();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new fragmentprofile();
                    break;*/
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            return true;
        }
    };
}
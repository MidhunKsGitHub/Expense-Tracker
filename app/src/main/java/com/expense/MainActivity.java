package com.expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    FrameLayout container;

    Fragment homeFragment;
    Fragment expenseFragment;
    Fragment addFragment;
    Fragment profileFragment;
    FragmentManager fm;

    ImageView ic_home;
    LinearLayout home_c, expense_c, add_c, profile_c;
    ImageView ic_expense, ic_add, ic_profile;
    CardView home_bar, expense_bar, add_bar, profile_bar;
    boolean isHome = true;
    int backPressed = 0;
    CardView fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.primary_grey);
        MidhunUtils.setStatusBarIcon(MainActivity.this, true);
        container = activityMainBinding.container;


        fab = activityMainBinding.fab;
        ic_home = activityMainBinding.icHome;
        home_c = activityMainBinding.homeC;
        expense_c = activityMainBinding.expenseC;
        add_c = activityMainBinding.addC;
        profile_c = activityMainBinding.profileC;
        ic_expense = activityMainBinding.icExpense;
        ic_add = activityMainBinding.icAdd;
        ic_profile = activityMainBinding.icProfile;

        home_bar = activityMainBinding.homeBar;
        expense_bar = activityMainBinding.expenseBar;
        add_bar = activityMainBinding.addBar;
        profile_bar = activityMainBinding.profileBar;


        homeFragment = new HomeFragment();
        expenseFragment = new ExpenseFragment();
        addFragment = new ShowFragment();
        profileFragment = new ProfileFragment();

        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {

            fm.beginTransaction().add(R.id.container, homeFragment, "1").commit();
            fm.beginTransaction().add(R.id.container, expenseFragment, "2").hide(expenseFragment).commit();
            fm.beginTransaction().add(R.id.container, addFragment, "3").hide(addFragment).commit();
            fm.beginTransaction().add(R.id.container, profileFragment, "4").hide(addFragment).commit();

            fm.beginTransaction().hide(expenseFragment).hide(addFragment).hide(profileFragment).show(homeFragment).commit();

        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });

        home_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHome = true;
                MidhunUtils.colorFilter(MainActivity.this, ic_home, R.color.black_light);
                MidhunUtils.colorFilter(MainActivity.this, ic_expense, R.color.black);
                MidhunUtils.colorFilter(MainActivity.this, ic_add, R.color.black);


                home_bar.setVisibility(View.VISIBLE);
                expense_bar.setVisibility(View.INVISIBLE);
                add_bar.setVisibility(View.INVISIBLE);
                profile_bar.setVisibility(View.INVISIBLE);
                fm.beginTransaction().hide(expenseFragment).hide(addFragment).hide(profileFragment).show(homeFragment).commit();

                MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.primary_grey);
                MidhunUtils.setStatusBarIcon(MainActivity.this, true);

            }
        });

        expense_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHome = false;
                MidhunUtils.colorFilter(MainActivity.this, ic_expense, R.color.primary_blue_dark);
                MidhunUtils.colorFilter(MainActivity.this, ic_home, R.color.black);
                MidhunUtils.colorFilter(MainActivity.this, ic_add, R.color.black);


                expense_bar.setVisibility(View.VISIBLE);
                home_bar.setVisibility(View.INVISIBLE);
                add_bar.setVisibility(View.INVISIBLE);
                profile_bar.setVisibility(View.INVISIBLE);

                fm.beginTransaction().hide(homeFragment).hide(addFragment).hide(profileFragment).show(expenseFragment).commit();

                MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.primary_grey);
                MidhunUtils.setStatusBarIcon(MainActivity.this, true);
            }
        });

        add_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHome = false;
                MidhunUtils.colorFilter(MainActivity.this, ic_add, R.color.black_light);
                MidhunUtils.colorFilter(MainActivity.this, ic_home, R.color.black);
                MidhunUtils.colorFilter(MainActivity.this, ic_expense, R.color.black);


                add_bar.setVisibility(View.VISIBLE);
                home_bar.setVisibility(View.INVISIBLE);
                expense_bar.setVisibility(View.INVISIBLE);
                profile_bar.setVisibility(View.INVISIBLE);

                fm.beginTransaction().hide(homeFragment).hide(expenseFragment).hide(profileFragment).show(addFragment).commit();


                MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.white);
                MidhunUtils.setStatusBarIcon(MainActivity.this, true);

            }
        });

        profile_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHome = false;
                MidhunUtils.colorFilter(MainActivity.this, ic_expense, R.color.black);
                MidhunUtils.colorFilter(MainActivity.this, ic_home, R.color.black);
                MidhunUtils.colorFilter(MainActivity.this, ic_add, R.color.black);


                profile_bar.setVisibility(View.VISIBLE);
                home_bar.setVisibility(View.INVISIBLE);
                add_bar.setVisibility(View.INVISIBLE);
                expense_bar.setVisibility(View.INVISIBLE);

                fm.beginTransaction().hide(homeFragment).hide(addFragment).hide(expenseFragment).show(profileFragment).commit();

                MidhunUtils.changeStatusBarColor(MainActivity.this, R.color.primary_grey);
                MidhunUtils.setStatusBarIcon(MainActivity.this, true);

            }
        });


    }


}


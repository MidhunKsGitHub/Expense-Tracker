package com.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.expense.Utils.MidhunUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MidhunUtils.changeStatusBarColor(SplashActivity.this,R.color.white);
        MidhunUtils.setStatusBarIcon(SplashActivity.this,true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(SplashActivity.this,SigninActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}
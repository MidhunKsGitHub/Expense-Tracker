package com.expense;

import static com.expense.Utils.MidhunUtils.setWindowFlag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MidhunUtils.showFullScreen(SigninActivity.this);

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SigninActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
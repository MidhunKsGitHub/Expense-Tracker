package com.expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.expense.Adapter.BankListAdapter;
import com.expense.Model.BankModel;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityListBankAccountBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListBankAccountActivity extends AppCompatActivity {
    ActivityListBankAccountBinding binding;

    RecyclerView recyclerView;
    List<BankModel> bankModelList;
    BankListAdapter bankListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBankAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(ListBankAccountActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(ListBankAccountActivity.this, true);


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
         bankModelList=new ArrayList<>();
         loadData();
         bankListAdapter=new BankListAdapter(ListBankAccountActivity.this,bankModelList);
         recyclerView.setAdapter(bankListAdapter);

        binding.addBankFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BankAccountDetailsActivity.class));
            }
        });

    }
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("bank list", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("bank key", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<BankModel>>() {
        }.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        bankModelList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (bankModelList == null) {
            // if the array list is empty
            // creating a new array list.
            bankModelList = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("bank list", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(bankModelList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("bank key", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Toast.makeText(getApplicationContext(), "New Bank Added ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        bankListAdapter=new BankListAdapter(ListBankAccountActivity.this,bankModelList);
        recyclerView.setAdapter(bankListAdapter);
    }
}
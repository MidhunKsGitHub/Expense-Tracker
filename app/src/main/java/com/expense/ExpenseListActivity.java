package com.expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.expense.Adapter.AllListAdpater;
import com.expense.Adapter.ExpenseListAdapter;
import com.expense.Model.AllExpense;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityExpenseListBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {
    ActivityExpenseListBinding binding;

    RecyclerView recyclerView;

    List<AllExpense> allExpenseList;

    ExpenseListAdapter allListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(ExpenseListActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(ExpenseListActivity.this, true);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        allExpenseList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
        String month = getIntent().getExtras().getString("month");
        String type = getIntent().getExtras().getString("type");
        binding.month.setText(month);

        allListAdpater = new ExpenseListAdapter(getApplicationContext(), sortList(allExpenseList, type, month));
        recyclerView.setAdapter(allListAdpater);


    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("courses", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<AllExpense>>() {
        }.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        allExpenseList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (allExpenseList == null) {
            // if the array list is empty
            // creating a new array list.
            allExpenseList = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(allExpenseList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        Toast.makeText(getApplicationContext(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    List<AllExpense> sortList(List<AllExpense> expenses, String name, String month) {

        int length = expenses.size();
        int index = length - 1;


        for (int i = 0; i < length; i++) {
            if (expenses.get(index).getDate().contains(month) && expenses.get(index).getExpenseType().equalsIgnoreCase(name)) {

            } else {
                expenses.remove(index);
            }
            index--;

        }
        return expenses;
    }
}
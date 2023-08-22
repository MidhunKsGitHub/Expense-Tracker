package com.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.expense.Adapter.AllListAdpater;
import com.expense.Model.AllExpense;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityAddExpenseBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {
    List<AllExpense> allExpenseList;
    ActivityAddExpenseBinding binding;

    AllListAdpater allListAdpater;
    final Calendar myCalendar = Calendar.getInstance();

    List<String> expenseList;
    List<String> categoryList;

    String cat_expense;


    int count;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(AddExpenseActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(AddExpenseActivity.this, true);
        allExpenseList = new ArrayList<>();
        loadData();
        count = allExpenseList.size() + 1;
        ID = String.valueOf(count);

        expenseList = new ArrayList<>();
        expenseList.add("Income");
        expenseList.add("Expense");

        categoryList = new ArrayList<>();
        categoryList.add("Food");
        categoryList.add("Shopping");
        categoryList.add("Entertainment");
        categoryList.add("Travel");
        categoryList.add("Recharge");
        categoryList.add("Gifts");
        categoryList.add("Clothes");
        categoryList.add("Bills");

        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down_custom, expenseList);
        binding.expenseType.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down_custom, categoryList);
        binding.categoryType.setAdapter(adapter1);

        if (getIntent().getExtras().getString("type").equalsIgnoreCase("expense")) {
            cat_expense = getIntent().getExtras().getString("cat");
            binding.expenseType.setText("Expense");
            binding.categoryType.setText(cat_expense);

        } else {
            binding.expenseType.setText("Income");
        }


        binding.expenseType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.inputDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, day);
                        String myFormat = "dd MMM yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        binding.dateTxt.setText(dateFormat.format(myCalendar.getTime()));
                    }
                };

                new DatePickerDialog(AddExpenseActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allExpenseList.add(new AllExpense(binding.amountTxt.getText().toString(), binding.expenseType.getText().toString(), binding.categoryType.getText().toString(), binding.dateTxt.getText().toString(), binding.descTxt.getText().toString(), ID));
                allListAdpater = new AllListAdpater(getApplicationContext(), allExpenseList);
               saveData();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<AllExpense>>() {
        }.getType();

        allExpenseList = gson.fromJson(json, type);

        if (allExpenseList == null) {

            allExpenseList = new ArrayList<>();
        }
    }

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(allExpenseList);

        editor.putString("courses", json);

        editor.apply();

        Toast.makeText(this, "Saved to list. ", Toast.LENGTH_SHORT).show();
    }
}
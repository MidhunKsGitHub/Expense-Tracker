package com.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.expense.Model.AllExpense;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityEditBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    List<AllExpense> allExpenseList;
    int id;

    String ID;

    final Calendar myCalendar = Calendar.getInstance();

    List<String> expenseList;
    List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(EditActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(EditActivity.this, true);

        allExpenseList = new ArrayList<>();
        loadData();
        String idd = getIntent().getExtras().get("id").toString();
        ID = getIntent().getExtras().getString("idd");
        id = Integer.parseInt(idd);
        //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        binding.expenseType.setText(getIntent().getExtras().getString("type"));
        binding.categoryType.setText(getIntent().getExtras().getString("cat"));
        binding.descTxt.setText(getIntent().getExtras().getString("desc"));
        binding.dateTxt.setText(getIntent().getExtras().getString("date"));
        binding.amountTxt.setText(getIntent().getExtras().getString("amount"));



        expenseList = new ArrayList<>();
        expenseList.add("Income");
        expenseList.add("Expense");

        categoryList=new ArrayList<>();
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

       binding.imgBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allExpenseList.set(id, new AllExpense(binding.amountTxt.getText().toString(), binding.expenseType.getText().toString(), binding.categoryType.getText().toString(), binding.dateTxt.getText().toString(), binding.descTxt.getText().toString(), ID));
                saveData();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allExpenseList.remove(id);
                saveData();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

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

                new DatePickerDialog(EditActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
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
}
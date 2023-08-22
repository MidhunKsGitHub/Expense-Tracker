package com.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expense.Model.AllExpense;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityAddBinding;
import com.expense.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    PieChart pieChart;

    int foodCount = 1;
    int entertainmentCount = 1;
    int billsCount = 1;
    int rechargeCount = 1;
    int travelCount = 1;
    int shoppingCount = 1;
    int clothesCount = 1;
    int giftsCount = 1;

    List<AllExpense> allExpenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(AddActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(AddActivity.this, true);
        pieChart = binding.chart1;

        allExpenseList = new ArrayList<>();
        loadData();
        foodCount = itemAllCount(allExpenseList, "food");
        rechargeCount = itemAllCount(allExpenseList, "recharge");
        entertainmentCount = itemAllCount(allExpenseList, "entertainment");
        billsCount = itemAllCount(allExpenseList, "bills");
        travelCount = itemAllCount(allExpenseList, "travel");
        shoppingCount = itemAllCount(allExpenseList, "shopping");
        clothesCount = itemAllCount(allExpenseList, "clothes");
        giftsCount = itemAllCount(allExpenseList, "gifts");


        showPieChart();
        binding.expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "");
                startActivity(intent);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imgEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Entertainment");
                startActivity(intent);

            }
        });

        binding.imgBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Bills");
                startActivity(intent);

            }
        });


        binding.imgRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Recharge");
                startActivity(intent);

            }
        });

        binding.imgTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Travel");
                startActivity(intent);

            }
        });

        binding.imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Food");
                startActivity(intent);

            }
        });

        binding.imgShoppimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Shopping");
                startActivity(intent);

            }
        });

        binding.imgClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Clothes");
                startActivity(intent);

            }
        });

        binding.imgGifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "expense");
                intent.putExtra("cat", "Gifts");
                startActivity(intent);

            }
        });

        binding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddExpenseActivity.class);
                intent.putExtra("type", "income");
                intent.putExtra("cat", "");
                startActivity(intent);
            }
        });
    }

    private void showPieChart() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";


        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Food", foodCount);
        typeAmountMap.put("Recharge", rechargeCount);
        typeAmountMap.put("Clothes", clothesCount);
        typeAmountMap.put("Bills", billsCount);
        typeAmountMap.put("Shopping", shoppingCount);
        typeAmountMap.put("Travel", travelCount);
        typeAmountMap.put("Gifts", giftsCount);
        typeAmountMap.put("Entertainment", entertainmentCount);


        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0, Color.parseColor("#f44336"));
        colors.add(1, Color.parseColor("#4caf50"));
        colors.add(2, Color.parseColor("#00b0ff"));
        colors.add(3, Color.parseColor("#8c9eff"));
        colors.add(4, Color.parseColor("#ff6e40"));
        colors.add(5, Color.parseColor("#aa00ff"));
        colors.add(6, Color.parseColor("#d500f9"));
        colors.add(7, Color.parseColor("#ffa726"));

        //input data and fit data into pie chart entry
        for (String type : typeAmountMap.keySet()) {
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
        //setting text size of the value
        pieDataSet.setValueTextSize(15f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(false);
        pieChart.getLegend().setEnabled(false);

        Legend legend = pieChart.getLegend();

        for (int i = 0; i < colors.size(); i++) {

            View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_legend, null);// custom layout for create legends
            TextView tvLegend = (TextView) view1.findViewById(R.id.label);
            View legendView = (View) view1.findViewById(R.id.view);
            legendView.setBackgroundColor(colors.get(i));
            tvLegend.setText(pieEntries.get(i).getLabel());


            binding.childlayput.addView(view1);  // add lagend to linear layout ( vertical)
        }


        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawSliceText(false);


        pieChart.setData(pieData);
        pieChart.invalidate();


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

    int itemAllCount(List<AllExpense> expenses, String name) {
        int sum = 0;
        int length = expenses.size();
        int index = length - 1;
        int amount;

        for (int i = 0; i < length; i++) {
            if (expenses.get(index).getCategory().equalsIgnoreCase(name)) {
                amount = Integer.parseInt(expenses.get(index).getAmpunt());
                sum = sum + amount;
            }
            index--;

        }
        return sum;
    }
}
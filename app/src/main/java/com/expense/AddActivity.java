package com.expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityAddBinding;
import com.expense.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
ActivityAddBinding binding;
PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityAddBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
        MidhunUtils.changeStatusBarColor(AddActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(AddActivity.this, true);
       pieChart=binding.chart1;

       showPieChart();
       binding.expense.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),AddExpenseActivity.class));
           }
       });

        binding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddExpenseActivity.class));
            }
        });
    }
    private void showPieChart() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Food", 200);
        typeAmountMap.put("Pets", 230);
        typeAmountMap.put("Clothes", 100);
        typeAmountMap.put("Travel", 500);
        typeAmountMap.put("Shoppimg", 500);
        typeAmountMap.put("Entertainment", 500);


        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ff8a65"));
        colors.add(Color.parseColor("#8c9eff"));
        colors.add(Color.parseColor("#ba68c8"));
        colors.add(Color.parseColor("#2196f3"));
        colors.add(Color.parseColor("#f44336"));
        colors.add(Color.parseColor("#EF629F"));
        colors.add(Color.parseColor("#f7ff00"));

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
        pieData.setDrawValues(true);
        pieChart.getLegend().setEnabled(false);

        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawSliceText(true);


        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
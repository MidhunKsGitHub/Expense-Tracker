package com.expense;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expense.Utils.MidhunUtils;
import com.expense.databinding.FragmentAddBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddFragment extends Fragment {

    FragmentAddBinding binding;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddBinding.inflate(getLayoutInflater(), container, false);


        pieChart = binding.chart1;
        showPieChart();



        binding.chartIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chartIncome.setBackground(getActivity().getResources().getDrawable(R.drawable.gradient_background_fab));
                binding.chartIncomeTxt.setTextColor(getActivity().getResources().getColor(R.color.white));

                binding.chartExpense.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_background));
                binding.chartExpenseTxt.setTextColor(getActivity().getResources().getColor(R.color.black));
                pieChart.animateX(1000);
            }
        });


        binding.chartExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chartExpense.setBackground(getActivity().getResources().getDrawable(R.drawable.gradient_background_fab));
                binding.chartExpenseTxt.setTextColor(getActivity().getResources().getColor(R.color.white));

                binding.chartIncome.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_background));
                binding.chartIncomeTxt.setTextColor(getActivity().getResources().getColor(R.color.black));
                pieChart.animateX(1000);
            }
        });

        return binding.getRoot();
    }

    private void showPieChart() {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Jan", 200);
        typeAmountMap.put("Feb", 230);
        typeAmountMap.put("March", 100);
        typeAmountMap.put("April", 500);
        typeAmountMap.put("May", 150);
        typeAmountMap.put("June", 250);
        typeAmountMap.put("July", 500);
        typeAmountMap.put("August", 350);
        typeAmountMap.put("September", 750);
        typeAmountMap.put("October", 850);
        typeAmountMap.put("November", 450);
        typeAmountMap.put("December", 650);

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
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
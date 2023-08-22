package com.expense;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.expense.Model.AllExpense;
import com.expense.databinding.FragmentAddBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowFragment extends Fragment {

    FragmentAddBinding binding;
    PieChart pieChart;

    List<AllExpense> allExpenseList;

    String expenseIncome="expense";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddBinding.inflate(getLayoutInflater(), container, false);

        allExpenseList = new ArrayList<>();
        loadData();
        pieChart = binding.chart1;
        showPieChart("expense");


        binding.chartIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.chartIncome.setBackground(getActivity().getResources().getDrawable(R.drawable.gradient_background_fab));
                binding.chartIncomeTxt.setTextColor(getActivity().getResources().getColor(R.color.white));

                binding.chartExpense.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_background));
                binding.chartExpenseTxt.setTextColor(getActivity().getResources().getColor(R.color.black));
                pieChart.animateX(1000);
                showPieChart("income");
                expenseIncome="income";
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
                showPieChart("expense");
                expenseIncome="expense";
            }
        });

        return binding.getRoot();
    }

    private void showPieChart(String name) {

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();

        typeAmountMap.put("Jan", itemAllCount(allExpenseList,name,"Jan"));
        typeAmountMap.put("Feb", itemAllCount(allExpenseList,name,"Feb"));
        typeAmountMap.put("Mar", itemAllCount(allExpenseList,name,"Mar"));
        typeAmountMap.put("Apr", itemAllCount(allExpenseList,name,"Apr"));
        typeAmountMap.put("May", itemAllCount(allExpenseList,name,"May"));
        typeAmountMap.put("Jun", itemAllCount(allExpenseList,name,"Jun"));
        typeAmountMap.put("Jul", itemAllCount(allExpenseList,name,"Jul"));
        typeAmountMap.put("Aug", itemAllCount(allExpenseList,name,"Aug"));
        typeAmountMap.put("Sep", itemAllCount(allExpenseList,name,"Sep"));
        typeAmountMap.put("Oct", itemAllCount(allExpenseList,name,"Oct"));
        typeAmountMap.put("Nov", itemAllCount(allExpenseList,name,"Nov"));
        typeAmountMap.put("Dec", itemAllCount(allExpenseList,name,"Dec"));

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
            if (typeAmountMap.get(type).floatValue() > 1) {
                pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
            }
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
        pieChart.getDescription().setEnabled(false);

        pieChart.setData(pieData);
        pieChart.invalidate();



      pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
          @Override
          public void onValueSelected(Entry e, Highlight h) {
              String month=pieEntries.get((int) h.getX()).getLabel();

               Intent intent = new Intent();
               intent.setClass(getActivity(), ExpenseListActivity.class);
               intent.putExtra("type",expenseIncome);
               intent.putExtra("month",month);
               startActivity(intent);

          }

          @Override
          public void onNothingSelected() {

          }
      });
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

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
        Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    int itemAllCount(List<AllExpense> expenses, String name,String month) {
        int sum = 0;
        int length = expenses.size();
        int index = length - 1;
        int amount;
try {


    for (int i = 0; i < length; i++) {
        if (expenses.get(index).getDate().contains(month) && expenses.get(index).getExpenseType().equalsIgnoreCase(name)) {
            try {
                amount = Integer.parseInt(expenses.get(index).getAmpunt());
                sum = sum + amount;
            } catch (Exception e) {

            }
        }
        index--;

    }
}
catch (Exception e){

}
        return sum;
    }


}
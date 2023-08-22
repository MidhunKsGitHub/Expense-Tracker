package com.expense;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.expense.Adapter.AllListAdpater;
import com.expense.Model.AllExpense;
import com.expense.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    RecyclerView recyclerView;
    AllListAdpater allListAdpater;

    List<AllExpense> allExpenseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        allExpenseList = new ArrayList<>();
        loadData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        allListAdpater = new AllListAdpater(getActivity(), allExpenseList);
        recyclerView.setAdapter(allListAdpater);


        //balance
        int balanec = itemAllCount(allExpenseList, "income") - itemAllCount(allExpenseList, "expense");
        binding.balance.setText(String.valueOf("Rs. " + balanec));
        //expense
        binding.expenseTxt.setText("Rs. " + String.valueOf(itemAllCount(allExpenseList, "expense")));

        //income
        binding.incomeTxt.setText("Rs. " + String.valueOf(itemAllCount(allExpenseList, "income")));

        binding.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SmsActivity.class));
            }
        });


        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListBankAccountActivity.class));
            }
        });
        return binding.getRoot();

    }

    public void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<AllExpense>>() {
        }.getType();

        allExpenseList = gson.fromJson(json, type);

        if (allExpenseList == null) {

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

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        allListAdpater = new AllListAdpater(getActivity(), allExpenseList);
        recyclerView.setAdapter(allListAdpater);
    }

    private void removeDuplicates(List<AllExpense> list)
    {
        int count = list.size();

        for (int i = 0; i < count; i++)
        {
            for (int j = i + 1; j < count; j++)
            {
                if (list.get(i).getAmpunt().equals(list.get(j).getAmpunt()) && list.get(i).getDate().equals(list.get(j).getDate()) &&list.get(i).getCategory().equals(list.get(j).getCategory()) && list.get(i).getDesc().equals(list.get(j).getDesc()) )
                {
                    list.remove(j--);
                    count--;
                }
            }
        }
    }

  public   int itemAllCount(List<AllExpense> expenses, String name) {


      int sum = 0;
        int length = expenses.size();
        int index = length - 1;
        int amount;

        for (int i = 0; i < length; i++) {
            if (expenses.get(index).getExpenseType().equalsIgnoreCase(name)) {
                try {
                    amount = Integer.parseInt(expenses.get(index).getAmpunt().trim().trim());
                    sum = sum + amount;
                } catch (Exception e) {
                }
            }
            index--;

        }
        return sum;
    }
}
package com.expense;


import static com.expense.Contants.constant.JSON_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.expense.Model.AllExpense;
import com.expense.Model.BankList;
import com.expense.Model.BankModel;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivityBankAccountBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDetailsActivity extends AppCompatActivity {
    ActivityBankAccountBinding binding;

    List<BankList> bankListList;

    List<String> bankNames;

    List<BankModel> bankModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MidhunUtils.changeStatusBarColor(BankAccountDetailsActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(BankAccountDetailsActivity.this, true);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bankListList = new ArrayList<>();
        bankNames = new ArrayList<>();
        bankModelList = new ArrayList<>();

        loadBank();
        loadData();

        for (int i = 0; i < bankListList.size(); i++) {
            bankNames.add(bankListList.get(i).getName().replace("_", " "));
        }


        binding.nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.username.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.expiryTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.expiry.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.amountTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.accountnumber.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankModelList.add(new BankModel(String.valueOf(bankModelList.size()),binding.testView.getText().toString(), binding.username.getText().toString(), binding.accountnumber.getText().toString(), binding.expiry.getText().toString(), "321"));
                MidhunUtils.addLocalData(BankAccountDetailsActivity.this,"primary","bkey",binding.testView.getText().toString());

                saveData();
                finish();
            }
        });

        binding.testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog alertDialog = new AlertDialog.Builder(BankAccountDetailsActivity.this).create();
//                View view = getLayoutInflater().inflate(R.layout.custom_bank_list_item, null);
                Dialog dialog = new Dialog(BankAccountDetailsActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.custom_bank_list_item);

                // set custom height and width
                dialog.getWindow().setLayout(binding.base.getWidth(), binding.base.getHeight());

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);


                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.drop_down_custom, bankNames);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        binding.testView.setText((CharSequence) adapter.getItem(position));
                        binding.bankname.setText(binding.testView.getText().toString());

                        dialog.dismiss();
                    }
                });

            }

        });

    }

    private void loadBank() {

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = JSON_URL;

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<BankList>>() {
        }.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        bankListList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (bankListList == null) {
            // if the array list is empty
            // creating a new array list.
            bankListList = new ArrayList<>();
        }
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
}
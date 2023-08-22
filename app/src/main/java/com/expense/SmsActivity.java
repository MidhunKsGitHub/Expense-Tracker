package com.expense;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.expense.Adapter.SmsAdapter;
import com.expense.Model.AllExpense;
import com.expense.Model.Sms;
import com.expense.Utils.MidhunUtils;
import com.expense.databinding.ActivitySmsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsActivity extends AppCompatActivity {
    ActivitySmsBinding binding;

    List<Sms> allSmsListDebited;
    List<Sms> allSmsListCredited;

    RecyclerView recyclerView;
    RecyclerView recyclerView1;

    SmsAdapter smsAdapter;

    String colum[] = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};

    String bankSortString;
    private static final int RECORD_REQUEST_CODE = 101;

    List<AllExpense> allExpenseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //permission

//        if ((ActivityCompat.checkSelfPermission(this, colum[0]) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, colum[1]) != PackageManager.PERMISSION_GRANTED)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(colum, 123);
//            }
//        }
        setupPermissions();

        bankSortString = MidhunUtils.localData(SmsActivity.this, "primary", "bkey").toLowerCase();


        allSmsListCredited = new ArrayList<>();
        allSmsListDebited = new ArrayList<>();

        recyclerView = binding.recyclerview;
        recyclerView1 = binding.recylerview1;


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);


        MidhunUtils.changeStatusBarColor(SmsActivity.this, R.color.white);
        MidhunUtils.setStatusBarIcon(SmsActivity.this, true);


        loadData();

        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.floatingActionButton3.hide();
                syncCredited();
                syncDebited();
                removeDuplicates(allExpenseList);

            }
        });

        ///button chartincome clicked

        binding.chartIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allSmsListCredited.clear();
                binding.chartIncome.setBackground(getResources().getDrawable(R.drawable.gradient_background_fab));
                binding.chartIncomeTxt.setTextColor(getResources().getColor(R.color.white));

                binding.chartExpense.setBackground(getResources().getDrawable(R.drawable.grey_background));
                binding.chartExpenseTxt.setTextColor(getResources().getColor(R.color.black));

                getAllSmsCredited();
                smsAdapter = new SmsAdapter(allSmsListCredited, SmsActivity.this);
                recyclerView1.setAdapter(smsAdapter);
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        });

        /// button chartdebited clicked

        binding.chartExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allSmsListDebited.clear();
                binding.chartExpense.setBackground(getResources().getDrawable(R.drawable.gradient_background_fab));
                binding.chartExpenseTxt.setTextColor(getResources().getColor(R.color.white));

                binding.chartIncome.setBackground(getResources().getDrawable(R.drawable.grey_background));
                binding.chartIncomeTxt.setTextColor(getResources().getColor(R.color.black));

                getAllSmsDebited();
                smsAdapter = new SmsAdapter(allSmsListDebited, SmsActivity.this);
                recyclerView.setAdapter(smsAdapter);
                recyclerView1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {

            smsAdapter = new SmsAdapter(allSmsListDebited, SmsActivity.this);
            recyclerView.setAdapter(smsAdapter);
            getAllSmsDebited();

        }
    }


    /// load database

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


    ////save database
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
        Toast.makeText(getApplicationContext(), "Syncing Finished", Toast.LENGTH_SHORT).show();
    }


    ///sync all debited sms

    @SuppressLint("Range")
    public List<Sms> syncCredited() {
        String amount = null;
        String amountReplace = null;
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }


                if (objSms.getMsg().contains("credited") && objSms.getMsg().toLowerCase().contains(bankSortString)) {
                    lstSms.add(objSms);


                }

                c.moveToNext();
            }
        }

        int length = lstSms.size();
        int index = length - 1;

        for (int i = 0; i < length; i++) {


            String text = lstSms.get(index).getMsg();
            Pattern pattern = Pattern.compile("Rs.\\s*([0-9,]+(\\.\\d{2})?)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                amount = matcher.group();

                if(amount.contains("Rs.")){
                    amount = amount.replace("Rs.", "");
                }

                if(amount.contains("Rs")){
                    amount = amount.replace("Rs", "");
                }


                amount = amount.replace(".00", "");

            }
            if (lstSms.get(index).getMsg().contains("credited") && lstSms.get(index).getMsg().toLowerCase().contains(bankSortString)) {
                if (!lstSms.get(index).getMsg().contains("debited")) {
                    String amountTxt = amount.trim().trim();
                    String dateTxt = getDate(Long.parseLong(lstSms.get(index).getTime()));

                    if (allExpenseList.contains(amountTxt) && allExpenseList.contains(dateTxt)) {

                    } else {

                        allExpenseList.add(new AllExpense(amountTxt, "income", lstSms.get(index).getAddress(), dateTxt, "desc", String.valueOf(index)));

                    }
                }
            }
            index--;
        }


        c.close();

        return lstSms;
    }

    @SuppressLint("Range")
    public List<Sms> syncDebited() {
        String amount = null;
        String amountReplace = null;
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }


                if (objSms.getMsg().contains("debited") && objSms.getMsg().toLowerCase().contains(bankSortString)) {
                    lstSms.add(objSms);


                }

                c.moveToNext();
            }
        }

        int length = lstSms.size();
        int index = length - 1;

        for (int i = 0; i < length; i++) {


            String text = lstSms.get(index).getMsg();
            Pattern pattern = Pattern.compile("Rs.\\s*([0-9,]+(\\.\\d{2})?)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                amount = matcher.group();
                amount = amount.replace("Rs", "");
                amount = amount.replace(".00", "");

            }
            if (lstSms.get(index).getMsg().contains("debited") && lstSms.get(index).getMsg().toLowerCase().contains(bankSortString)) {
                String amountTxt = amount.replace(".", "").trim().trim();
                String dateTxt = getDate(Long.parseLong(lstSms.get(index).getTime()));

                if (allExpenseList.contains(amountTxt) && allExpenseList.contains(dateTxt)) {

                } else {

                    allExpenseList.add(new AllExpense(amountTxt, "expense", lstSms.get(index).getAddress(), dateTxt, "banksms", String.valueOf(index)));

                }
            }
            index--;
        }


        c.close();

        return lstSms;
    }


    //date converation
    private String getDate(long time_stamp_server) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(time_stamp_server);
    }


    private void removeDuplicates(List<AllExpense> list) {

        int count = list.size();

        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (list.get(i).getAmpunt().equals(list.get(j).getAmpunt()) && list.get(i).getDate().equals(list.get(j).getDate()) && list.get(i).getCategory().equals(list.get(j).getCategory()) && list.get(i).getDesc().equals(list.get(j).getDesc())) {
                    list.remove(j--);
                    count--;
                }
            }
        }
        saveData();
    }


    ///get all debited sms

    @SuppressLint("Range")
    public List<Sms> getAllSmsDebited() {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                if (objSms.getMsg().contains("debited") && objSms.getMsg().toLowerCase().contains(bankSortString)) {
                    lstSms.add(objSms);
                }

                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        allSmsListDebited.addAll(lstSms);
        int length = allSmsListDebited.size();
        int index = length - 1;

        for (int i = 0; i < length; i++) {
            if (allSmsListDebited.get(index).getMsg().contains("debited") && allSmsListDebited.get(index).getMsg().toLowerCase().contains(bankSortString)) {

            } else {
                allSmsListDebited.remove(index);
            }
            index--;
        }
        c.close();

        return allSmsListDebited;
    }


    ///get all credited sms

    @SuppressLint("Range")
    public List<Sms> getAllSmsCredited() {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                if (objSms.getMsg().contains("credited") && objSms.getMsg().toLowerCase().contains(bankSortString)) {
                    lstSms.add(objSms);
                }

                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        allSmsListCredited.addAll(lstSms);
        int length = allSmsListCredited.size();
        int index = length - 1;

        for (int i = 0; i < length; i++) {
            if (allSmsListCredited.get(index).getMsg().contains("credited") && allSmsListCredited.get(index).getMsg().toLowerCase().contains(bankSortString)) {

                if (allSmsListCredited.get(index).getMsg().contains("debited")) {
                    allSmsListCredited.remove(index);
                }

            } else {
                allSmsListCredited.remove(index);
            }
            index--;
        }
        c.close();

        return allSmsListCredited;
    }


    ///permission
    private void setupPermissions() {

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }


    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");
                } else {
                    smsAdapter = new SmsAdapter(allSmsListDebited, SmsActivity.this);
                    recyclerView.setAdapter(smsAdapter);
                    getAllSmsDebited();
                }
            }
        }
    }
}
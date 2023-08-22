package com.expense.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.expense.Model.BankModel;
import com.expense.R;
import com.expense.Utils.MidhunUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder> {

    Context context;
    List<BankModel> bankModelList;

    int row_index = 0;

    public BankListAdapter(Context context, List<BankModel> bankModelList) {
        this.context = context;
        this.bankModelList = bankModelList;
    }

    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banklist, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        BankModel bankModel = bankModelList.get(position);
        holder.bankname.setText(bankModel.getBankName());
        holder.accountnumber.setText(bankModel.getAccountNumber());
        holder.username.setText(bankModel.getUserName());
        holder.expiry.setText(bankModel.getExpiry());
        holder.base.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bankModelList.remove(holder.getAdapterPosition());
                saveData();
               notifyDataSetChanged();
                return true;
            }
        });


        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, bankModel.getBankName()+" is now your primary account ", Toast.LENGTH_SHORT).show();
                 MidhunUtils.addLocalData(context,"primary","bkey",bankModel.getBankName());
                row_index = holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });
        if (row_index == position) {
            holder.primary.setVisibility(View.VISIBLE);
        } else {
            holder.primary.setVisibility(View.GONE);

        }

        if(MidhunUtils.localDataCtx(context,"primary","bkey").equalsIgnoreCase(bankModel.getBankName())){
            holder.primary.setVisibility(View.VISIBLE);
        }
        else{
            holder.primary.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bankModelList.size();
    }

    class BankViewHolder extends RecyclerView.ViewHolder {

        TextView bankname;
        TextView accountnumber;
        TextView username;
        TextView expiry;
        TextView primary;

        LinearLayout base;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            bankname = itemView.findViewById(R.id.bankname);
            accountnumber = itemView.findViewById(R.id.accountnumber);
            username = itemView.findViewById(R.id.username);
            expiry = itemView.findViewById(R.id.expiry);
            primary = itemView.findViewById(R.id.primary);
            base = itemView.findViewById(R.id.base);
        }
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = context.getSharedPreferences("bank list", MODE_PRIVATE);

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
        SharedPreferences sharedPreferences = context.getSharedPreferences("bank list", MODE_PRIVATE);

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
        Toast.makeText(context, "Bank Removed", Toast.LENGTH_SHORT).show();
    }
}

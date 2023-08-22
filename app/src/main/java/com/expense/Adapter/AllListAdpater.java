package com.expense.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.EditActivity;
import com.expense.Model.AllExpense;
import com.expense.R;
import com.expense.Utils.MidhunUtils;
import com.google.gson.Gson;

import java.util.List;

public class AllListAdpater extends RecyclerView.Adapter<AllListAdpater.AllListViewHolder> {

    Context context;
    List<AllExpense> allExpenseList;

    public AllListAdpater(Context context, List<AllExpense> allExpenseList) {
        this.context = context;
        this.allExpenseList = allExpenseList;
    }

    @NonNull
    @Override
    public AllListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_allexpense_item, parent, false);
        return new AllListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllListViewHolder holder, int position) {
        AllExpense allExpense = allExpenseList.get(position);
        holder.expense_type.setText(allExpense.getCategory());
        holder.expense_amount.setText(allExpense.getAmpunt());
        holder.date.setText(allExpense.getDate());

        holder.base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setClass(context, EditActivity.class);
                intent.putExtra("id", holder.getAdapterPosition());
                intent.putExtra("idd", allExpense.getId());
                intent.putExtra("type", allExpense.getExpenseType());
                intent.putExtra("cat", allExpense.getCategory());
                intent.putExtra("desc", allExpense.getDesc());
                intent.putExtra("date", allExpense.getDate());
                intent.putExtra("amount", allExpense.getAmpunt());
                context.startActivity(intent);


            }
        });


        if (position == allExpenseList.size() - 1) {
           MidhunUtils.setMargins(holder.base, 0, 0, 0, 250);

            holder.base.setBackground(context.getResources().getDrawable(R.drawable.grey_background_corner));
        }
        else{
            MidhunUtils.setMargins(holder.base, 0, 0, 0, 0);
            holder.base.setBackground(context.getResources().getDrawable(R.drawable.grey_background_corner_1));

        }

        if (allExpense.getExpenseType().equalsIgnoreCase("income")) {
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            holder.leading.setRotation(90);
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
        } else {
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.leading.setRotation(270);
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_horizontal_rule_24));

        }
    }


    @Override
    public int getItemCount() {
        return allExpenseList.size();
    }


    class AllListViewHolder extends RecyclerView.ViewHolder {
        TextView expense_type;
        TextView expense_amount;
        TextView date;

        LinearLayout base;
        CardView leading;
        ImageView plusminus;

        public AllListViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type = itemView.findViewById(R.id.sms_address);
            expense_amount = itemView.findViewById(R.id.sms_txt);
            base = itemView.findViewById(R.id.base);
            leading = itemView.findViewById(R.id.leading);
            plusminus = itemView.findViewById(R.id.plusminus);
            date = itemView.findViewById(R.id.date);
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);

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
        Toast.makeText(context, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
}

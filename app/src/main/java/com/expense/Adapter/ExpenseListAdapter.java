package com.expense.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.ExpenseListActivity;
import com.expense.Model.AllExpense;
import com.expense.R;

import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder> {

    Context context;
    List<AllExpense> allExpenseList;

    public ExpenseListAdapter(Context context, List<AllExpense> allExpenseList) {
        this.context = context;
        this.allExpenseList = allExpenseList;
    }

    @NonNull
    @Override
    public ExpenseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_expenselist_item, parent, false);

        return new ExpenseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListViewHolder holder, int position) {
        AllExpense allExpense = allExpenseList.get(position);
        holder.expense_type.setText(allExpense.getCategory());
        holder.expense_amount.setText(allExpense.getAmpunt());
        holder.date.setText(allExpense.getDate());
        holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_arrow_right_alt_24_white));

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

    class  ExpenseListViewHolder extends RecyclerView.ViewHolder{
        TextView expense_type;
        TextView expense_amount;
        TextView date;

        LinearLayout base;
        CardView leading;
        ImageView plusminus;
        ImageView icon;

        public ExpenseListViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type = itemView.findViewById(R.id.sms_address);
            expense_amount = itemView.findViewById(R.id.sms_txt);
            base = itemView.findViewById(R.id.base);
            leading = itemView.findViewById(R.id.leading);
            plusminus = itemView.findViewById(R.id.plusminus);
            date = itemView.findViewById(R.id.date);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}

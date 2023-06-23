package com.expense.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.Model.AllExpense;
import com.expense.R;
import com.expense.Utils.MidhunUtils;

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
        View view = LayoutInflater.from(context).inflate(R.layout.custom_allexpense_item,parent,false);
        return new AllListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllListViewHolder holder, int position) {
        AllExpense allExpense= allExpenseList.get(position);
        holder.expense_type.setText(allExpense.getExpenseType());
        holder.expense_amount.setText(allExpense.getAmpunt());

        if(position==allExpenseList.size()-1){
            MidhunUtils.setMargins(holder.base,0,0,0,250);
            MidhunUtils.gradientUi(holder.base,30,0xffffffff,0xffffffff);
        }

        if(allExpense.getExpenseType().equalsIgnoreCase("income")){
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            holder.leading.setRotation(90);
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
        }
        else{
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
            holder.leading.setRotation(270);
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_horizontal_rule_24));

        }
    }



    @Override
    public int getItemCount() {
        return allExpenseList.size();
    }


    class AllListViewHolder extends RecyclerView.ViewHolder{
          TextView expense_type;
          TextView expense_amount;

          CardView base;
          CardView leading;
          ImageView plusminus;
        public AllListViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type=itemView.findViewById(R.id.expense_type);
            expense_amount=itemView.findViewById(R.id.expense_amount);
            base=itemView.findViewById(R.id.base);
            leading=itemView.findViewById(R.id.leading);
            plusminus=itemView.findViewById(R.id.plusminus);
        }
    }

}

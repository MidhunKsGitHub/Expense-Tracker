package com.expense.Adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.Model.Sms;
import com.expense.R;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {


    List<Sms> smsList;
    Context context;

    public SmsAdapter(List<Sms> smsList, Context context) {
        this.smsList = smsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_sms, parent, false);

        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        Sms sms = smsList.get(position);
        holder.sms_address.setText(sms.getAddress());


        holder.date.setText(getDate(Long.parseLong(sms.getTime())));

        String text = sms.getMsg();
        Pattern pattern= Pattern.compile("Rs.\\s*([0-9,]+(\\.\\d{2})?)");
        Matcher matcher=pattern.matcher(text);

        if(matcher.find()){
            holder.sms_txt.setText(matcher.group());
         // holder.sms_txt.setText(sms.getMsg());
        }

        if(sms.getMsg().contains("debited")){
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_horizontal_rule_24));
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
        }
        else{
            holder.plusminus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
            holder.leading.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            holder.leading.setRotation(90);

        }
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView sms_txt;
        TextView sms_address;
        TextView date;

        CardView leading;
        ImageView plusminus;
        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            sms_txt = itemView.findViewById(R.id.sms_txt);
            sms_address = itemView.findViewById(R.id.sms_address);
            date = itemView.findViewById(R.id.date);
            leading = itemView.findViewById(R.id.leading);
            plusminus = itemView.findViewById(R.id.plusminus);
        }
    }

    private String getDate(long time_stamp_server) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(time_stamp_server);
    }
}

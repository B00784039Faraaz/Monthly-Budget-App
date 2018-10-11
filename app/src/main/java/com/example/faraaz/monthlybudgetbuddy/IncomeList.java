package com.example.faraaz.monthlybudgetbuddy;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by faraa on 2018-04-05.
 */

public class IncomeList extends ArrayAdapter<Income>  {

    private Activity context;
    private List<Income> incomeList;

    public IncomeList(Activity context,List<Income> incomeList){
        super(context,R.layout.list_layout,incomeList);
        this.context= context;
        this.incomeList=incomeList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =context.getLayoutInflater();

        View view = inflater.inflate(R.layout.list_layout,null,true);

        TextView type = view.findViewById(R.id.type);
        TextView description = view.findViewById(R.id.description);
        TextView amount = view.findViewById(R.id.amount);
        TextView datePicker = view.findViewById(R.id.datePicker);

        Income income = incomeList.get(position);

        type.setText(income.getType1());
        description.setText(income.getDesc());
        amount.setText(""+(income.getAmt()));
        datePicker.setText(income.getDate());

        return view;
    }
}

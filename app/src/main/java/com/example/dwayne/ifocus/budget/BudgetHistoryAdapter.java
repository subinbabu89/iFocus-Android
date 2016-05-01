package com.example.dwayne.ifocus.budget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dwayne.ifocus.R;

import java.util.List;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetHistoryAdapter extends BaseAdapter {

    private List<Budget> budgetHistoryObjects;
    private Activity context;

    public BudgetHistoryAdapter(List<Budget> budgetHistoryObjects, Activity context) {
        this.budgetHistoryObjects =    budgetHistoryObjects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return budgetHistoryObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        LayoutInflater inflater = context.getLayoutInflater();

        if (rowView == null) {

          //  rowView = inflater.inflate(R.layout.list_budget_history_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_budget_amt = (TextView) rowView.findViewById(R.id.txtv_health_task_value);
            viewHolder.txtv_budget_desc = (TextView) rowView.findViewById(R.id.txtv_health_task_value);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtv_budget_amt.setText((int) budgetHistoryObjects.get(position).getAmount());
        holder.txtv_budget_desc.setText(budgetHistoryObjects.get(position).getDescription());


        return rowView;
    }


    static class ViewHolder{
        public TextView txtv_budget_amt;
        public TextView txtv_budget_desc;
    }
}

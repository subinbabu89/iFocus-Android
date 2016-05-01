package com.example.dwayne.ifocus.budget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwayne.ifocus.MainActivity;
import com.example.dwayne.ifocus.budget.BudgetContract;
import com.example.dwayne.ifocus.budget.BudgetHistoryDao;
import com.example.dwayne.ifocus.budget.BudgetHistoryDatabase;
import com.example.dwayne.ifocus.budget.BudgetEditClickListener;
import com.example.dwayne.ifocus.budget.Budget;
import com.example.dwayne.ifocus.R;

import java.util.List;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetTaskAdapter extends ArrayAdapter<Budget> {
    private BudgetFragment context;
    private  BudgetHistoryDatabase budgetHistoryDatabase;

    public BudgetTaskAdapter(BudgetFragment context) {
        super(context.getActivity(),android.R.layout.simple_list_item_2);
        this.context = context;
       budgetHistoryDatabase = ((MainActivity)context.getActivity()).getBudgetHistoryDatabase();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        LayoutInflater inflater = context.getActivity().getLayoutInflater();


        if (rowView == null) {

            rowView = inflater.inflate(R.layout.list_budget_history_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtv_Budget_task_desc = (TextView) rowView.findViewById(R.id.txtv_budget_task_desc);
            viewHolder.txtv_Budget_task_amt = (TextView) rowView.findViewById(R.id.txtv_budget_task_amt);
            viewHolder.imgbtn_task_delete = (ImageButton)rowView.findViewById(R.id.imgbtn_task_bdelete);
            viewHolder.imgbtn_task_edit = (ImageButton)rowView.findViewById(R.id.imgbtn_task_bedit);
            rowView.setTag(viewHolder);
        }

        final Budget budget = getItem(position);


        ViewHolder holder = (ViewHolder) rowView.getTag();
        if(budget!=null)
            holder.txtv_Budget_task_desc.setText(budget.getDescription());
        if(budget!=null)
            holder.txtv_Budget_task_amt.setText(Float.toString(budget.getAmount()));

        holder.imgbtn_task_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity()).setMessage("are you sure you want to delete this task?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((BudgetEditClickListener)context).onDeleteClicked(budget);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

            }
        });

        holder.imgbtn_task_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BudgetEditClickListener)context).onEditClicked(budget);

            }
        });



        return rowView;
    }

    public void setData(List<Budget> BudgetTasks){
        clear();
        if(BudgetTasks!=null){
            for (Budget budget : BudgetTasks){
                add(budget);
            }
        }
    }

    static class ViewHolder {
        public TextView txtv_Budget_task_desc;
        public TextView txtv_Budget_task_amt;

       // public ImageButton imgbtn_task_incomplete;
       // public ImageButton imgbtn_task_complete;
        public ImageButton imgbtn_task_delete;
        public ImageButton imgbtn_task_edit;
    }
}



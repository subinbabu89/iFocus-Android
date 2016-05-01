package com.example.dwayne.ifocus.budget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.dwayne.ifocus.R;


public class BudgetDialogFragment extends DialogFragment {

    private EditText ed_task_amount;
    private EditText ed_task_description;

    private BudgetDialogClickListener callback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = (BudgetDialogClickListener) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = (getArguments()!=null && getArguments().size()>0)? "Edit Budget Task": ".";
        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DateFormat df = new android.text.format.DateFormat();
                                String currentDate = df.format("yyyy-MM-dd", new java.util.Date()).toString();
                                if(getArguments()!=null && getArguments().size()>0){
                                    Budget budget = new Budget(Float.valueOf(ed_task_amount.getEditableText().toString()), ed_task_description.getEditableText().toString());
                                    int id = getArguments().getInt(BudgetContract.BudgetColumns.BUDGET_ID);
                                    budget.setId(id);
                                    callback.onEditOKClick(budget);
                                }else {
                                    callback.onCreateOKClick(new Budget(Float.valueOf(ed_task_amount.getEditableText().toString()), ed_task_description.getEditableText().toString()));
                                }
                            }
                        }
                ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                });
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.edit_budget_dialog,null);
        ed_task_description = (EditText)v.findViewById(R.id.edittext);
        ed_task_amount = (EditText)v.findViewById(R.id.editText);
        if(getArguments()!=null && getArguments().size()>0){
            String amt = getArguments().getString(BudgetContract.BudgetColumns.BUDGET_AMOUNT);
            String desc = getArguments().getString(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION );
            ed_task_description.setText(desc);
            ed_task_amount.setText(amt);
        }
        b.setView(v);
        return b.create();
    }

}

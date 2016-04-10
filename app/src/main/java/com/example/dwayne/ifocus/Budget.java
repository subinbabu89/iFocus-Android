package com.example.dwayne.ifocus;

/**
 * Created by Dwayne on 3/15/2016.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Budget extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.budget, container, false);
        FloatingActionButton fab;
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditBudgetDialog();
            }
        });
        return view;
    }

    private void showEditBudgetDialog() {

    // get prompts.xml view
    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
    View promptView = layoutInflater.inflate(R.layout.edit_budget_dialog, null);
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setView(promptView);

    final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
    // setup a dialog window
    alertDialogBuilder.setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {

        }
    })
            .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
    });

    // create an alert dialog
    AlertDialog alert = alertDialogBuilder.create();
    alert.show();
}
}
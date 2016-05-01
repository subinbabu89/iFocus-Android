package com.example.dwayne.ifocus.budget;

/**
 * Created by Dwayne on 3/15/2016.
 */
import android.support.v4.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dwayne.ifocus.MainActivity;
import com.example.dwayne.ifocus.R;
import com.example.dwayne.ifocus.user.User;
import com.example.dwayne.ifocus.user.UserDatabase;

import java.util.List;

public class BudgetFragment extends Fragment implements BudgetDialogClickListener,BudgetEditClickListener,LoaderManager.LoaderCallbacks<List<Budget>>{

    private TextView MonthlyBudget;

    private ListView lst_budget_tasks;
    private FloatingActionButton fab_add_budget_task;
    private BudgetTaskAdapter budgetTaskAdapter;


    public static final int DIALOG_FRAGMENT = 1;

    private ContentResolver contentResolver;
    private static final int LOADER_ID = 1;
    private List<Budget> budgetList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view, view1;
        view = init(inflater, container);
        view1= setBudget(inflater, container);
        wiredEvents();
        customizeEvents();
        return view;
    }

    private View setBudget(LayoutInflater inflater, ViewGroup container )
    {
        View view = inflater.inflate(R.layout.create_new_budget, container, false);

        return view;
    }
    private void customizeEvents() {

    }

    private View init(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.budget, container, false);

        lst_budget_tasks = (ListView)view.findViewById(R.id.lst_budget_tasks);
        fab_add_budget_task = (FloatingActionButton) view.findViewById(R.id.fab);

        MonthlyBudget = (TextView)view.findViewById(R.id.MonthlyBudget);
        MonthlyBudget.setText("Monthly Budget: $"+ User.getInstance().getUserBudget());

        contentResolver = getActivity().getContentResolver();
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
    }

    private void wiredEvents() {
        budgetTaskAdapter = new BudgetTaskAdapter(this);
        lst_budget_tasks.setAdapter(budgetTaskAdapter);

        fab_add_budget_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                BudgetDialogFragment editAmountDialog = new BudgetDialogFragment();
                editAmountDialog.setTargetFragment(BudgetFragment.this, DIALOG_FRAGMENT);
                editAmountDialog.show(fm, "fragment_add_task");
            }
        });}

   /* private void showEditBudgetDialog() {

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
                        Toast.makeText(getActivity(), "Transaction Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "No Transaction Added", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }*/

        public void onCreateOKClick(Budget budgetTaskObject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BudgetContract.BudgetColumns.BUDGET_AMOUNT,budgetTaskObject.getAmount());
        contentValues.put(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION,budgetTaskObject.getDescription());

        Uri returnedUri = contentResolver.insert(BudgetContract.URI_TABLE,contentValues);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

        public void onEditOKClick(Budget budget) {
        ContentValues contentValues =new ContentValues();
        contentValues.put(BudgetContract.BudgetColumns.BUDGET_AMOUNT,budget.getAmount());
        contentValues.put(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION,budget.getDescription());
        Uri uri = BudgetContract.Budget.buildBudgetUri(String.valueOf(budget.getId()));


        int recordedUpdated = contentResolver.update(uri,contentValues,null,null);
        Toast.makeText(getActivity(), "updated "+recordedUpdated, Toast.LENGTH_SHORT).show();
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
        @Override
   public Loader<List<Budget>> onCreateLoader(int id, Bundle args) {
        contentResolver = getActivity().getContentResolver();
        return new BudgetLoader(getActivity(),BudgetContract.URI_TABLE,contentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Budget>> loader, List<Budget> data) {
        budgetList = data;
        budgetTaskAdapter.setData(budgetList);
        budgetTaskAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<Budget>> loader) {
        budgetTaskAdapter.setData(null);
    }

    @Override
    public void onDeleteClicked(Budget budget) {
        Uri uri = BudgetContract.Budget.buildBudgetUri(String.valueOf(budget.getId()));
        contentResolver.delete(uri,null,null);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditClicked(Budget budget) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        BudgetDialogFragment budgetDialogFragment = new BudgetDialogFragment();
        budgetDialogFragment.setTargetFragment(this, BudgetFragment.DIALOG_FRAGMENT);
        Bundle bundle = new Bundle();
        bundle.putString(BudgetContract.BudgetColumns.BUDGET_AMOUNT, String.valueOf(budget.getAmount()));
        bundle.putString(BudgetContract.BudgetColumns.BUDGET_DESCRIPTION,budget.getDescription());
        bundle.putInt(BudgetContract.BudgetColumns.BUDGET_ID,budget.getId());
        budgetDialogFragment.setArguments(bundle);
        budgetDialogFragment.show(fm, "fragment_add_task");


    }

        @Override
    public void markChanged() { getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
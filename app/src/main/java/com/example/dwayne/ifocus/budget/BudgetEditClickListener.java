package com.example.dwayne.ifocus.budget;

/**
 * Created by Dwayne on 4/17/2016.
 */
public interface BudgetEditClickListener {
    void onDeleteClicked(Budget budget);
    void onEditClicked(Budget budget);
    void markChanged();
}


package com.example.dwayne.ifocus.budget;

/**
 * Created by Dwayne on 4/17/2016.
 */
public class BudgetHistoryDao {

    private int _id;
    private String description;
    private float amount;

    public BudgetHistoryDao(String description, float amount, int _id) {
        this.description = description;
        this.amount = amount;
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
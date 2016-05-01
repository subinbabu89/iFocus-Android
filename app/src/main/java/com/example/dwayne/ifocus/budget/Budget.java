package com.example.dwayne.ifocus.budget;

/**
 * Created by Dwayne on 3/15/2016.
 */


public class Budget {

    private int id;
    private float amount;
    private String description;

    public Budget(float amount, String description)
    {
        this.amount = amount;
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


package com.example.dwayne.ifocus.health.pojo;

/**
 * Created by Subin on 4/8/2016.
 */
public class Health {

    private int id;
    private String type;
    private String description;
    private String date;
    private boolean completion;

    public Health(String type, String description, String date, boolean completion) {
        this.type = type;
        this.description = description;
        this.date = date;
        this.completion = completion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }
}

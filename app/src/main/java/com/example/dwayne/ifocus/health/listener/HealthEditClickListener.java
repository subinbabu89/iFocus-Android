package com.example.dwayne.ifocus.health.listener;

import com.example.dwayne.ifocus.health.pojo.Health;

/**
 * Created by Subin on 4/9/2016.
 */
public interface HealthEditClickListener {

    void onDeleteClicked(Health health);
    void onEditClicked(Health health);
    void markChanged();
}

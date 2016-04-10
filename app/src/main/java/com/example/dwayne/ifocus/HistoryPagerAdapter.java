package com.example.dwayne.ifocus;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dwayne.ifocus.health.history.HealthHistoryFragment;

/**
 * Created by Subin on 4/8/2016.
 */
public class HistoryPagerAdapter extends FragmentStatePagerAdapter {

    private TabLayout tabLayout;

    public HistoryPagerAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                BudgetHistoryFragment budgetHistoryFragment = new BudgetHistoryFragment();
                return budgetHistoryFragment;
            case 1:
                StudyHistoryFragment studyHistoryFragment = new StudyHistoryFragment();
                return studyHistoryFragment;
            case 2:
                HealthHistoryFragment healthHistoryFragment = new HealthHistoryFragment();
                return healthHistoryFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabLayout.getTabCount();
    }
}
